package com.bookmyshow.main.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "theatre")
public class Theatre {
     
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @NotBlank(message = "Theatre name is required")
     private String name;
    
    //@NotBlank(message="location is required")
	private String loaction;
    
    @NotBlank(message = "city is required")
	private String city;
    
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Theatre() {
		super();
	}
	public Theatre(Long id, String name, String loaction, String city) {
		super();
		this.id = id;
		this.name = name;
		this.loaction = loaction;
		this.city = city;
	}
	@Override
	public String toString() {
		return "Theatre [id=" + id + ", name=" + name + ", loaction=" + loaction + ", city=" + city + "]";
	}
	public String getLoaction() {
		return loaction;
	}
	public String getCity() {
		return city;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLoaction(String loaction) {
		this.loaction = loaction;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
