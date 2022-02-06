package one.digitalinnovation.personapi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.request.PhoneDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Address;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.entity.Phone;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.AddressRepository;
import one.digitalinnovation.personapi.repository.PersonRepository;
import one.digitalinnovation.personapi.repository.PhoneRepository;
import one.digitalinnovation.personapi.service.exceptions.DataBaseException;

@Service
public class PersonService {

	@Autowired
    private PersonRepository personRepository;
	
	@Autowired
	private PhoneRepository phoneRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private viaCepService cepService;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public MessageResponseDTO createPerson(PersonDTO personDTO) {
    	Address address = addressRepository.findById(personDTO.getCep()).orElseGet(() -> {
    		Address newAddress = cepService.viaCep(personDTO.getCep());
    		addressRepository.save(newAddress);
    		return newAddress;
    	});
    	
        Person personToSave = personMapper.toModel(personDTO);
        personToSave.setAddress(address);
        Person savedPerson = personRepository.save(personToSave);
        
        return createMessageResponse(savedPerson.getId(), "Created person with ID: ");
    }
    

    @Transactional(readOnly = true)
	public Page<PersonDTO> findAllPaged(Pageable pageable) {
		Page<Person> pagelist = personRepository.findAll(pageable);
		///System.out.println("CEP: "+pagelist.getContent());
		return pagelist.map(x -> new PersonDTO(x));
	}
    
    @Transactional(readOnly = true)
	public PersonDTO findByCpf(String cpf) throws PersonNotFoundException {
		Person obj = personRepository.findByCpf(cpf);
		 if(obj == null) {
			 throw new PersonNotFoundException(cpf);
		 }
		 return personMapper.toDTO(obj);
	}
    
    @Transactional(readOnly = true)
    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll(Sort.by("firstName"));
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfExists(id);
		System.out.println("CEP: "+person.getAddress().getCep());
        return new PersonDTO(person);
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        
        try {
			personRepository.deleteById(id);
		} 
		catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
    }
    
    @Transactional
	public PersonDTO update(Long id, PersonDTO dto) throws PersonNotFoundException {
		try {
			Person entity = personRepository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = personRepository.save(entity);
			return new PersonDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new PersonNotFoundException(id);	
		}
		
	}

    @Transactional
    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);

        Person personToUpdate = personMapper.toModel(personDTO);

        Person updatedPerson = personRepository.save(personToUpdate);
        return createMessageResponse(updatedPerson.getId(), "Updated person with ID: ");
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO.builder()
                .message(message + id)
                .build();
    }
    
private void copyDtoToEntity(PersonDTO dto, Person entity) {
		
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setCpf(dto.getCpf());
		LocalDate localDate = LocalDate.parse(dto.getBirthDate());
		entity.setBirthDate(localDate);
		
		entity.getPhones().clear();
		for(PhoneDTO phoneDto : dto.getPhones()) {
			Phone phone = phoneRepository.getOne(phoneDto.getId());
			entity.getPhones().add(phone);
		}
	}
}
