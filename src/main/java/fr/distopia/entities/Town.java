package fr.distopia.entities;

import java.sql.Array;

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
public class Town {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTown;
    private String NameT;
    private Array MovieTheaterList;
    
    @ManyToOne
    private MovieTheater movieTheater;
}