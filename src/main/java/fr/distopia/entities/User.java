package fr.distopia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Array;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Size(min=5,max=30)
    private String UserName;
    
    @NotNull
	@Size(min=5,max=30)
    private String Password;
    
    @NotNull
	@Size(min=5,max=30)
    private Array SessionList;
}
