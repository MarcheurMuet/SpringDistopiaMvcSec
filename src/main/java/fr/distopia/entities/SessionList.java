package fr.distopia.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity 
@Data  @NoArgsConstructor @AllArgsConstructor @ToString
public class SessionList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private double price;
    
    @ManyToOne
    private Movie movie;
    
    @ManyToOne
    private Session session;

    @OneToMany(mappedBy = "session")
    private Collection<SessionList> sessionLists;

    public SessionList (int id, int quantity, double price){
        this.setId(id);
        this.setQuantity(quantity);
        this.setPrice(price);
    }

    public void setSessionList(SessionList sessionlist) {
        this.sessionLists.add(sessionlist);
    }
}