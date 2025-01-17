package fr.distopia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Size(min=5,max=30)
    private String username;
    
    @NotNull
	@Size(min=5,max=30)
    private String password;

    @NotNull
	@Size(min=5,max=30)
    private String adress;

    @NotNull
	@Size(min=5,max=30)
    private String email;
    
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<SessionList> sessionList;
}
