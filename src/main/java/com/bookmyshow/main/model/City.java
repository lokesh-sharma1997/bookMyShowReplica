package com.bookmyshow.main.model;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="city")
public class City {

	@Id
    private Long id; 

    @NotBlank(message = "City name is required")
    private String name;

    @NotNull(message = "Popular flag is required")
    private Boolean popular;

    @Size(max = 255, message = "Image URL must be less than 255 characters")
    private String imageUrl;
    
   


    
}

