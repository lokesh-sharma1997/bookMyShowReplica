package com.bookmyshow.main.dto;
import java.time.LocalDate;
import java.util.List;

public class MovieDto {
    private Long id;
    private String name;
    private List<String> language;

    private List<String> genre;

    private List<String> format;

    private String description;
    private String duration;
    private LocalDate releaseDate;
    private String imageurl;
    private Double rating;
    private Double likes;
    private Boolean currentlyPlaying;

   
   
    
   
	@Override
	public String toString() {
		return "MovieDto [id=" + id + ", name=" + name + ", language=" + language + ", genre=" + genre + ", format="
				+ format + ", description=" + description + ", duration=" + duration + ", releaseDate=" + releaseDate
				+ ", imageurl=" + imageurl + ", rating=" + rating + ", likes=" + likes + ", currentlyPlaying="
				+ currentlyPlaying + "]";
	}




	public MovieDto(Long id, String name, List<String> language, List<String> genre, List<String> format,
			String description, String duration, LocalDate releaseDate, String imageurl, Double rating, Double likes,
			Boolean currentlyPlaying) {
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
	}




	public MovieDto()
    {
    	
    }
    
    
	

	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

  

    public List<String> getLanguage() {
		return language;
	}




	public void setLanguage(List<String> language) {
		this.language = language;
	}




	public List<String> getGenre() {
		return genre;
	}




	public void setGenre(List<String> genre) {
		this.genre = genre;
	}




	public List<String> getFormat() {
		return format;
	}




	public void setFormat(List<String> format) {
		this.format = format;
	}




	public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public String getImageurl() { return imageurl; }
    public void setImageurl(String imageurl) { this.imageurl = imageurl; }

    public Double getLikes() {
		return likes;
	}

	public void setLikes(Double likes) {
		this.likes = likes;
	}

	public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Boolean getCurrentlyPlaying() { return currentlyPlaying; }
    public void setCurrentlyPlaying(Boolean currentlyPlaying) { this.currentlyPlaying = currentlyPlaying; }
}
