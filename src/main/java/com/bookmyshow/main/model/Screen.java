package com.bookmyshow.main.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "screen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String screenName;

    @ManyToOne(cascade = CascadeType.ALL)  // Ensure cascades to venue if needed
    @JoinColumn(name = "venue_id", referencedColumnName = "id")
    private Venue venue;

    @OneToMany(mappedBy = "screen", fetch = FetchType.EAGER, cascade = CascadeType.ALL)  // Cascade all operations
    private List<Layout> layouts;


     @OneToMany(mappedBy = "screen", cascade = CascadeType.PERSIST)
     private List<Show> shows;

}
