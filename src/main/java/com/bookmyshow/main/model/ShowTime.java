package com.bookmyshow.main.model;



import java.time.LocalTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="showtime")
public class ShowTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime showTime;

    private Boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "show_time_date_id", referencedColumnName = "id")
    private ShowTimeDate showTimeDate; // Link back to ShowTimeDate

}