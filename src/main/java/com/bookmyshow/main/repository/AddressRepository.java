package com.bookmyshow.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{

	public Optional<Address> findByStreetAndCity_NameAndPin(String street, String cityName, String pin);

}
