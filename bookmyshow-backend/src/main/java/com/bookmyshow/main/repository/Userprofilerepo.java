package com.bookmyshow.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmyshow.main.model.Userprofile;

@Repository
public interface Userprofilerepo extends JpaRepository<Userprofile, Long> {

}
