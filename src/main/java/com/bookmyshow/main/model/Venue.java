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
    
//    @ManyToOne
//    private Address address; 

    @ManyToOne(cascade = CascadeType.PERSIST) 
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Amenity> amenities; 

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "venue_supported_category",
        joinColumns = @JoinColumn(name = "venue_id"),
        inverseJoinColumns = @JoinColumn(name = "supported_category_id")
    )
    private List<SupportedCategory> supportedCategories; 

    @OneToMany(mappedBy = "venue",cascade = CascadeType.PERSIST)
    private List<Screen> screens; // Screens (only for "movies")
    @ManyToMany(mappedBy = "venues")
    private List<Event> movies;
    

    private Boolean deleted = false;

}
