package com.bookmyshow.main.model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="showtimedate")
public class ShowTimeDate {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate showDate;
    @OneToMany(mappedBy = "showTimeDate") // This will automatically map to showTimeDate in ShowTime
    private List<ShowTime> showTimes;
    
    @ManyToOne
    @JoinColumn(name = "show_id", referencedColumnName = "id")
    @JsonBackReference 
    private Show show; 
}
