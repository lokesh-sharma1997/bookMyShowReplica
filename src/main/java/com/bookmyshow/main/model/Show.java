package com.bookmyshow.main.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "eventid")
//    private Long eventid;
//
//    @Column(name = "venueid")
//    private Long venueid;

    private String city;
    private String eventType;
    
    @Column(name="screen_name")
    private String screenName;
    
    @Column(name="date",nullable = false)
    
    private LocalDate date;
    private LocalTime startTime;
    private int duration;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Languages> languages = new ArrayList<>();

    private String status;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Showprice> showPrices = new ArrayList<>();

    @Column(name="format")
    private String format;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Layout> layouts = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST) 
    @JoinColumn(name = "event_id",nullable = false)
    private Event event;

    @ManyToMany(mappedBy = "shows")
    private Set<UserMaster> users = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "venueid",nullable = false)
    private Venue venue;
    
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowCategory> showCategories = new ArrayList<>();

    
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();


}
