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
public class MovieTheater {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovieTheater;
    private String Name;
    private String Adresse;
    private String MovieList;
    
    @ManyToOne
    private Movie movie;
}
