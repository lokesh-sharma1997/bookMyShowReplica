package com.bookmyshow.main.model;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
    
   

    public City(Long id, @NotBlank(message = "City name is required") String name,
			@NotNull(message = "Popular flag is required") Boolean popular,
			@Size(max = 255, message = "Image URL must be less than 255 characters") String imageUrl
			) {
		super();
		this.id = id;
		this.name = name;
		this.popular = popular;
		this.imageUrl = imageUrl;
		
	}

	

 public City()
 {
	 
 }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Boolean getPopular() { return popular; }
    public void setPopular(Boolean popular) { this.popular = popular; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }



	
    
}

