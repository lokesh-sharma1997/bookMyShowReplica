package com.bookmyshow.main.model;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="layout")
public class Layout {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String layoutName;
	
	@ManyToOne
	private Screen screen;
	
	@OneToMany(mappedBy = "layout")
    private List<LayoutRow> layoutRows;
	
	private int cols;
	
	@OneToMany(mappedBy = "layout", cascade = CascadeType.PERSIST)
    private List<Show> shows;
	
}
