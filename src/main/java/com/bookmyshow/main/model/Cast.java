package com.bookmyshow.main.model;



import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Embeddable
public class Cast {

    private String actorName;

   
    @Column(columnDefinition = "TEXT")
    private String img;
}
