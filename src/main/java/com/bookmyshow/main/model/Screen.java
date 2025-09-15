package com.bookmyshow.main.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "screen")
public class Screen {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String screenName; 
    
     @ManyToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "venue_id", referencedColumnName = "id")
     private Venue venue; 

     @OneToMany(mappedBy = "screen")
     private List<Layout> layouts;
}
