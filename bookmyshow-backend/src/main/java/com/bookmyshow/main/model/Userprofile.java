package com.bookmyshow.main.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="Userprofile")
public class Userprofile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private long id;
	 private String firstname;
	 private String lastname;
	 public long getId() {
		return id;
	}
	 public void setId(long id) {
		 this.id = id;
	 }
	 public Userprofile(long id, String firstname, String lastname, long phone_number, String email, LocalDate dOB,
			String address_1, String address_2, int pin_code, String land_mark, String city, String state,
			boolean flag) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone_number = phone_number;
		this.email = email;
		DOB = dOB;
		Address_1 = address_1;
		Address_2 = address_2;
		this.pin_code = pin_code;
		this.land_mark = land_mark;
		this.city = city;
		this.state = state;
		this.flag = flag;
	}
	 public Userprofile()
	 {
		 
	 }
	 @Override
	public String toString() {
		return "Userprofile [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", phone_number="
				+ phone_number + ", email=" + email + ", DOB=" + DOB + ", Address_1=" + Address_1 + ", Address_2="
				+ Address_2 + ", pin_code=" + pin_code + ", land_mark=" + land_mark + ", city=" + city + ", state="
				+ state + ", flag=" + flag + "]";
	}
	 public String getFirstname() {
		 return firstname;
	 }
	 public void setFirstname(String firstname) {
		 this.firstname = firstname;
	 }
	 public String getLastname() {
		 return lastname;
	 }
	 public void setLastname(String lastname) {
		 this.lastname = lastname;
	 }
	 public long getPhone_number() {
		 return phone_number;
	 }
	 public void setPhone_number(long phone_number) {
		 this.phone_number = phone_number;
	 }
	 public String getEmail() {
		 return email;
	 }
	 public void setEmail(String email) {
		 this.email = email;
	 }
	 public LocalDate getDOB() {
		 return DOB;
	 }
	 public void setDOB(LocalDate dOB) {
		 DOB = dOB;
	 }
	 public String getAddress_1() {
		 return Address_1;
	 }
	 public void setAddress_1(String address_1) {
		 Address_1 = address_1;
	 }
	 public String getAddress_2() {
		 return Address_2;
	 }
	 public void setAddress_2(String address_2) {
		 Address_2 = address_2;
	 }
	 public int getPin_code() {
		 return pin_code;
	 }
	 public void setPin_code(int pin_code) {
		 this.pin_code = pin_code;
	 }
	 public String getLand_mark() {
		 return land_mark;
	 }
	 public void setLand_mark(String land_mark) {
		 this.land_mark = land_mark;
	 }
	 public String getCity() {
		 return city;
	 }
	 public void setCity(String city) {
		 this.city = city;
	 }
	 public String getState() {
		 return state;
	 }
	 public void setState(String state) {
		 this.state = state;
	 }
	 public boolean isFlag() {
		 return flag;
	 }
	 public void setFlag(boolean flag) {
		 this.flag = flag;
	 }
	 private long phone_number;
	 private String email;
	 private LocalDate DOB;
	private String Address_1;
	private String Address_2;
	private int pin_code;
	private String land_mark;
	private String city;
	private String state;
	private boolean flag;
	
	 
	 
	 
	 
}
