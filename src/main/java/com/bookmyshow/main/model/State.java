package com.bookmyshow.main.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "state")
@NoArgsConstructor
@AllArgsConstructor
public class State {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long state_id;
	private String statekey;
	private String name;
	  @OneToMany(mappedBy = "state")
	    private List<City> city;
}
