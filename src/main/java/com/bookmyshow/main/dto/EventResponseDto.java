package com.bookmyshow.main.dto;



import java.util.List;
import lombok.Data;

@Data
public class EventResponseDto {
    private long eventId;       // custom / imdb id
    private String title;         // Event name
    private String likes;         // likes as string (from DB)
    private String poster;        // image url
    private List<String> genre;   // list of genres
    private String imdbVotes;     // optional / dummy
    private String imdbRating;    // rating as string
    private Boolean releasedFlag; // currentlyPlaying flag
}
