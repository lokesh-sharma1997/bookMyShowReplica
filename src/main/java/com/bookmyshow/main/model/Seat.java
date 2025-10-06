package com.bookmyshow.main.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seat")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

	@Id
	@GeneratedValue
	private Long id;

	private String seatNumber;

	private boolean reserved;

	@ManyToOne
	@JoinColumn(name = "show_id")
	private Show show;

	@ManyToOne
	@JoinColumn(name = "screen_id")
	private Screen screen;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserMaster user;

	@ManyToOne
	@JoinColumn(name = "show_category_id")
	private ShowCategory showCategory;

	@ManyToMany(mappedBy = "seats")
	private List<Booking> bookings;

	@ManyToOne
	@JoinColumn(name = "layout_row_id")
	private LayoutRow layoutRow;

}
