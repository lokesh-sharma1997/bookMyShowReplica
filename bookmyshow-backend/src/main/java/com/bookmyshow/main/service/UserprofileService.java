package com.bookmyshow.main.service;

import java.util.List;

import com.bookmyshow.main.model.Userprofile;

public interface UserprofileService {
   List<Userprofile> getAllUserDetails();
   Userprofile InsertUser(Userprofile userprofile);
}
