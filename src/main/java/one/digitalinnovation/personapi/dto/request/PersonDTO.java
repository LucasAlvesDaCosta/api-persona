package one.digitalinnovation.personapi.dto.request;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Builder;
import lombok.Data;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.entity.Phone;

@Data
@Builder
public class PersonDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String lastName;

    @NotEmpty
    @CPF
    private String cpf;

    @NotNull(message = "insert your birthdate")
    private String birthDate;
    
    private String cep;

    @Valid
    @NotEmpty
    private List<PhoneDTO> phones;
    
    public PersonDTO() {
    	
    }
    
    public PersonDTO(Person entity) {
		id = entity.getId();
		firstName = entity.getFirstName();
		lastName = entity.getLastName();
		cpf = entity.getCpf();		
		birthDate = entity.getBirthDate().toString();
		cep = entity.getAddress().getCep();
		phones = entity.getPhones().stream().map(x-> new PhoneDTO(x)).collect(Collectors.toList());
	}
    
    
	public PersonDTO(Person entity, Set<Phone> phones) {
		this(entity);
		phones.forEach(cat -> this.phones.add(new PhoneDTO(cat)));
	}

	public PersonDTO(Long id, String firstName, String lastName, String cpf,
		String birthDate,  String address, List<PhoneDTO> phones) {
		
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.phones = phones;
		cep = address;
	}
	
}
