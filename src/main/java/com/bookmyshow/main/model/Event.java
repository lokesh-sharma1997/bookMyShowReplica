package com.bookmyshow.main.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private Long eventId;

	@Column(nullable = false)
	@NotBlank(message = "Event name is required")
	private String name;

	@NotBlank(message = "Description is required")
	private String description;

	@NotBlank(message = "Run Time is required")
	private String runTime;

	private LocalDate startDate;

	private LocalDate endDate;

	@NotBlank(message = "Event Type is required")
	private String eventType;

	@Column(columnDefinition = "TEXT")
	private String imageurl;

	private Double imdbRating;

	private Double likes;

	private Double votes;

	private Boolean currentlyPlaying;

	private Boolean deleted = false;

	private Integer ageLimit;

	@ManyToMany
    @JoinTable(
        name = "event_language_map",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "language_id", referencedColumnName = "languageId")
    )
	private List<Languages> languages;

	@ManyToMany
    @JoinTable(
        name = "event_genres_map",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "genres_id", referencedColumnName = "genreId")
    )
	private List<Genres> genres;

	@ManyToMany
    @JoinTable(
        name = "event_format_map",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "format_id", referencedColumnName = "formatId")
    )
	private List<Format> format;

	@ManyToMany
    @JoinTable(
        name = "event_tag_map",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tagId")
    )
	private List<Tag> tag;

	@ManyToMany
    @JoinTable(
        name = "event_releaseMonth_map",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "releaseMonth_id", referencedColumnName = "releaseMonthId")
    )
	private List<ReleaseMonth> releaseMonth;

	private LocalDate releasingOn;

	@ManyToMany
    @JoinTable(
        name = "event_date_filter_map",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "date_filter_id", referencedColumnName = "dateFilterId")
    )
	private List<DateFilter> dateFilter;

	@ManyToMany
    @JoinTable(
        name = "event_categories_map",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "categoryId")
    )
	private List<Categories> categories;

	@ManyToMany
    @JoinTable(
        name = "event_moreFilters_map",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "moreFilters_id", referencedColumnName = "filterId")
    )
	private List<MoreFilters> moreFilters;

	@ManyToMany
    @JoinTable(
        name = "event_price_map",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "price_id", referencedColumnName = "priceId")
    )
	private List<Price> price;

	@ManyToMany
    @JoinTable(
        name = "event_cast_map",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "cast_id", referencedColumnName = "castId")
    )
	private List<Cast> cast;
	
//	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Cast> cast;

	@ManyToMany
    @JoinTable(
        name = "event_crew_map",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "crew_id", referencedColumnName = "crewId")
    )
	private List<Crew> crew;

	
	
	
	  @ManyToMany
	    @JoinTable(
	        name = "event_city_map",
	        joinColumns = @JoinColumn(name = "event_id"),
	        inverseJoinColumns = @JoinColumn(name = "city_id")
	    )
	    private List<City> city = new ArrayList<>();
	
	  @ManyToMany
	    @JoinTable(
	      name = "event_venue_map", 
	      joinColumns = @JoinColumn(name = "event_id"), 
	      inverseJoinColumns = @JoinColumn(name = "venue_id"))
	    private List<Venue> venues;
	 
	  
	  @OneToMany(mappedBy = "event")
	  private List<Show> shows;

	
	
}
