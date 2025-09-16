package com.bookmyshow.main.dto;

import lombok.Data;

@Data
public class EditProfileRequest {
    private String profileImg;
    private String name;
    private String username;      
    private String email;          
    private String phoneNumber;   
    private String dob;
    private String identity;
    private String married;
    private String anniversaryDate;
    private String pincode;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
}
