package com.bookmyshow.main.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
