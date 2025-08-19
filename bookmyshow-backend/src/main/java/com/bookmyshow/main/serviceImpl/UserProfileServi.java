package com.bookmyshow.main.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.model.Userprofile;
import com.bookmyshow.main.repository.Userprofilerepo;
import com.bookmyshow.main.service.UserprofileService;
@Service
public class UserProfileServi implements UserprofileService {

@Autowired
public Userprofilerepo userprofilerepo;

 public List<Userprofile> getAllUserDetails()
 {
	return  userprofilerepo.findAll();
 }
 
 public    Userprofile InsertUser( Userprofile userprofile)
 {
	 return userprofilerepo.save(userprofile);
	 
 }

}
