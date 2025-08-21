package com.bookmyshow.main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Movie name is required")
    private String name;

//    @ElementCollection
    private List<String> language;

   
    private List<String> genre;

//    @ElementCollection
    private List<String> format;
    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Duration is required")
    private String duration;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;
//
//    @Lob
//    @Column(columnDefinition = "TEXT")
    private String imageurl; 

    private Double rating;
    
    private Double likes;

    private Boolean currentlyPlaying;

    private Boolean deleted = false;

    
    
  
    
    public void setLanguage(List<String> language) {
		this.language = language;
	}

	public void setGenre(List<String> genre) {
		this.genre = genre;
	}

	public void setFormat(List<String> format) {
		this.format = format;
	}

	public Movie()
    {
    	
    }
    
    
	

	public Movie(Long id, @NotBlank(message = "Movie name is required") String name, List<String> language,
			List<String> genre, List<String> format, @NotBlank(message = "Description is required") String description,
			@NotBlank(message = "Duration is required") String duration,
			@NotNull(message = "Release date is required") LocalDate releaseDate, String imageurl, Double rating,
			Double likes, Boolean currentlyPlaying, Boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.language = language;
		this.genre = genre;
		this.format = format;
		this.description = description;
		this.duration = duration;
		this.releaseDate = releaseDate;
		this.imageurl = imageurl;
		this.rating = rating;
		this.likes = likes;
		this.currentlyPlaying = currentlyPlaying;
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", name=" + name + ", language=" + language + ", genre=" + genre + ", format="
				+ format + ", description=" + description + ", duration=" + duration + ", releaseDate=" + releaseDate
				+ ", imageurl=" + imageurl + ", rating=" + rating + ", likes=" + likes + ", currentlyPlaying="
				+ currentlyPlaying + ", deleted=" + deleted + "]";
	}

	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

  

    public List<String> getLanguage() {
		return language;
	}

	public List<String> getGenre() {
		return genre;
	}

	public List<String> getFormat() {
		return format;
	}

	public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public String getImageurl() { return imageurl; }
    public void setImageurl(String imageurl) { this.imageurl = imageurl; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Boolean getCurrentlyPlaying() { return currentlyPlaying; }
    public void setCurrentlyPlaying(Boolean currentlyPlaying) { this.currentlyPlaying = currentlyPlaying; }

    public Boolean getDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }

	public Double getLikes() {
		return likes;
	}

	public void setLikes(Double likes) {
		this.likes = likes;
	}

	
    
    
}
