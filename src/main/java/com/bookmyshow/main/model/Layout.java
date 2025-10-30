package com.bookmyshow.main.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "layout")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Layout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String layoutName;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @OneToMany(mappedBy = "layout", cascade = CascadeType.ALL) 
    private List<LayoutRow> layoutRows;

    @OneToMany(mappedBy = "layout", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<ShowLayout> showLayouts;
	


    private int cols;

   
}

