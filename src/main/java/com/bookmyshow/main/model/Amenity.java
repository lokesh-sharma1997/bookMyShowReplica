package com.bookmyshow.main.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "amenity")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amenity_name")  // Correctly map to the "amenity name" column in the database
     private String amenityName; 

    @ManyToMany(mappedBy = "amenities")
    private List<Venue> venues; 
}
