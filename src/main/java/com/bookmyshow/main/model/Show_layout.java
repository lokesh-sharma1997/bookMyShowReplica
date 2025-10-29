package com.bookmyshow.main.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "show_layout")
public class Show_layout {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int moviePrice;
	@ManyToOne
	@JoinColumn(name = "layout_id", referencedColumnName = "id")
	private Layout layout;
	@ManyToOne
	@JoinColumn(name = "show_id", referencedColumnName = "id")
	@JsonBackReference
	private Show show;

}