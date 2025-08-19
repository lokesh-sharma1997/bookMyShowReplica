package com.bookmyshow.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.model.Userprofile;
import com.bookmyshow.main.serviceImpl.UserProfileServi;

@RestController
@RequestMapping("/userprofile")
public class UserprofileContro {
	@Autowired 
	private UserProfileServi userProfileServi;
	
	@GetMapping("/getuserdetails")
	public List<Userprofile> GetAll()
	{
		return userProfileServi.getAllUserDetails();
	}
	
	@PostMapping("/InsertUser")
	public String UploadProfile(@RequestBody Userprofile userprofile)
	{
		userProfileServi.InsertUser(userprofile);
		return "Upload Profile";
	}

}
