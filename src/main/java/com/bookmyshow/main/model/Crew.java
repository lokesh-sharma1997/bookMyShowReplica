package com.bookmyshow.main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "crew")
@Data
@Embeddable
public class Crew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long crewId;
    
    @Column(nullable = false, unique = true)
    private String memberName;
    
    @Column(columnDefinition = "TEXT")
    private String crewImg;
}
