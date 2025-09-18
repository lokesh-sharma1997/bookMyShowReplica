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

    @OneToMany(mappedBy = "layout", cascade = CascadeType.ALL)  // Ensure layout rows are cascaded
    private List<LayoutRow> layoutRows;

    private int cols;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

}

