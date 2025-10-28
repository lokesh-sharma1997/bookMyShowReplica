
package com.bookmyshow.main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "seat_number", nullable = false)
	private String seatNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "layout_row_id", nullable = false)
	private LayoutRow layoutRow;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "screen_id", nullable = false)
	private Screen screen;

}
