
package com.bookmyshow.main.model;

import java.util.List;

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
@Table(name = "layout_row")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LayoutRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rowName;

    @ManyToOne
    @JoinColumn(name = "layout_id")
    private Layout layout;
    
    @OneToMany(mappedBy = "layoutRow", cascade = CascadeType.ALL)
    private List<Seat> seats;


}
