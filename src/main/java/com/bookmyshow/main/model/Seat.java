package com.bookmyshow.main.model;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="seat")
public class Seat {

    @Id
    @GeneratedValue
    private Long id;

    private String seatNumber; 

    private boolean reserved;  

    
    private String userId;     

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;         

    @ManyToOne
    @JoinColumn(name = "category_id")
    private SupportedCategory supportedcategory;  
    
    @ManyToOne
    @JoinColumn(name = "screen_id") // Ensure this matches your DB schema
    private Screen screen;

}
