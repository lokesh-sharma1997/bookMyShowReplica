package com.bookmyshow.main.model;

import java.time.LocalDate;
import java.util.List;

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

	private int ageLimit;

	@OneToMany
    @JoinTable(
        name = "event_language_map",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "language_id")
    )
	private List<Languages> languages;

	@OneToMany(mappedBy = "generes")
	@Column(name = "genres")
	private List<Genres> genres;

	@OneToMany(mappedBy = "format")
	@Column(name = "format")
	private List<Format> format;

	@OneToMany(mappedBy = "tag")
	@Column(name = "tag")
	private List<Tag> tag;

	@OneToMany(mappedBy = "releaseMonth")
	@Column(name = "releaseMonth")
	private List<ReleaseMonth> releaseMonth;

	private LocalDate releasingOn;

	@OneToMany(mappedBy = "date")
	@Column(name = "date")
	private LocalDate date;

	@OneToMany(mappedBy = "categories")
	@Column(name = "categories")
	private List<Categories> categories;

	@OneToMany(mappedBy = "moreFilters")
	@Column(name = "moreFilters")
	private List<MoreFilters> moreFilters;

	@OneToMany(mappedBy = "price")
	@Column(name = "price")
	private List<Price> price;

	@OneToMany(mappedBy = "cast")
	@Column(name = "cast")
	private List<Cast> cast;

	@OneToMany(mappedBy = "crew")
	@Column(name = "crew")
	private List<Crew> crew;

}
