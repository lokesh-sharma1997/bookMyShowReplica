package com.bookmyshow.main.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="city")
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId; 

    @NotBlank(message = "City name is required")
    private String name;

    @NotNull(message = "Popular flag is required")
    private Boolean popular;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;
    
//    @ManyToMany(mappedBy = "city")
//	private List<Event> events;
    @ManyToMany(mappedBy = "city")
    private List<Event> events;
    @ManyToOne
    @JoinColumn(name = "state_id")   
    private State state;
    
}

