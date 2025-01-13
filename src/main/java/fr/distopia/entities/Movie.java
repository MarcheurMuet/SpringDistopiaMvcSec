package fr.distopia.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity 
@Data  @NoArgsConstructor @AllArgsConstructor @ToString
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovie;
    private String Title;
    private int Duration;
    private int SessionPrice;
    
    @ManyToOne
    private Movie movie;
}