package fr.distopia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="orders")		
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Session {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int Duration;
    private double totalAmount;
    
    @ManyToOne
    private User user;
    
    @OneToMany(mappedBy = "order")
    private Collection<SessionList> sessionLists;
}
