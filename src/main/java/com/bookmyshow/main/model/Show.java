package com.bookmyshow.main.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "show")
public class Show {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@ManyToOne
@JoinColumn(name = "venue_id", referencedColumnName = "id")
private Venue venue;

@ManyToOne
@JoinColumn(name = "screen_id", referencedColumnName = "id")
private Screen screen;

@ManyToOne
@JoinColumn(name = "layout_id", referencedColumnName = "id")
private Layout layout;


private int showPrice;

@OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
@JsonManagedReference
private List<ShowTimeDate> showstimedate; 


@ManyToOne
@JoinColumn(name = "event_id", referencedColumnName = "event_id")
//@JsonBackReference
private Event event;


}