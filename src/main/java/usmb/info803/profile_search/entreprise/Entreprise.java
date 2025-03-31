package usmb.info803.profile_search.entreprise;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import usmb.info803.profile_search.DbEntity;

@Entity
public class Entreprise implements DbEntity {
    
    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    public Entreprise() {
    }

    public Entreprise(String name) {
        this.name = name;
    }

    @Override
    public boolean isValid() {
        return name != null
            && name.length() > 0
            && name.length() <= 255
            && id != null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
