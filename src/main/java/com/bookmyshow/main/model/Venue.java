package com.bookmyshow.main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

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

    @Embedded
    private Address address;

    private int venueCapacity;

    private String venueFor;  

    private String venueType;

    @ElementCollection
    @CollectionTable(name = "venue_supported_categories", joinColumns = @JoinColumn(name = "venue_id"))
    @Column(name = "category")
    private Set<String> supportedCategories;

    @ElementCollection
    @CollectionTable(name = "venue_additional_fields", joinColumns = @JoinColumn(name = "venue_id"))
    @MapKeyColumn(name = "field_key")
    @Column(name = "field_value")
    private Map<String, String> additionalFields;

    private Boolean deleted = false;
}
