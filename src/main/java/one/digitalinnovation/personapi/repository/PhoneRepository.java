package one.digitalinnovation.personapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import one.digitalinnovation.personapi.entity.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
	
}
