package usmb.info803.profile_search.entreprise;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Entreprise {
    
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Entreprise() {
    }

    public Entreprise(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
