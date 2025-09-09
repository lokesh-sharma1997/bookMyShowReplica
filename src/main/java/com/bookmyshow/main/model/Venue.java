package com.bookmyshow.main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "venue")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Venue name is required")
    private String venueName;


    private int venueCapacity;

    private String venueFor;  

    private String venueType;
    
    @ManyToOne
    private Address address; 

    @ManyToMany
    private List<Amenity> amenities; 

    @ManyToMany
    private List<SupportedCategory> supportedCategories; 

    @OneToMany(mappedBy = "venue")
    private List<Screen> screens; // Screens (only for "movies")

    

    private Boolean deleted = false;
}
