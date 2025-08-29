package com.bookmyshow.main.model;
import com.bookmyshow.main.model.Seat;


import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "screen")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    @ManyToOne
//    @JoinColumn(name = "venue_id")
    private Long venueId;

   private List<Seat> layout;
}
