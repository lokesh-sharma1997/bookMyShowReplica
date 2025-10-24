package com.bookmyshow.main.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "show_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "show_id", referencedColumnName = "id")
	@JsonBackReference
	private Show show;

	@ManyToOne
//	@JoinColumn(name = "layout_id")
	private Layout layoutId;

	private Integer price;

}
