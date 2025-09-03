//package com.bookmyshow.main.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.List;
//
//import ch.qos.logback.core.Layout;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "screen")
//public class Screen {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String screenNo; 
//
////   // @ManyToOne
////    @JoinColumn(name = "venue_id")
////    private Venue venue;
//
//  // @OneToMany(cascade = CascadeType.ALL)
//    
//    @JoinColumn(name = "screen_id") 
//    private List<Layout> layout;
//}
