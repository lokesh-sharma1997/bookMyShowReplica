package com.bookmyshow.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{

}
