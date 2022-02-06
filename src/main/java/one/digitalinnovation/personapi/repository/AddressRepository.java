package one.digitalinnovation.personapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import one.digitalinnovation.personapi.entity.Address;

public interface AddressRepository extends JpaRepository<Address, String>{

}
