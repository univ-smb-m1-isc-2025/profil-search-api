package usmb.info803.profile_search.invitation;

import usmb.info803.profile_search.DbEntity;
import usmb.info803.profile_search.entreprise.Entreprise;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Invitation implements DbEntity {
    
    @Id
    @GeneratedValue
    @JsonProperty("id")
    private long id;

    @JsonProperty("token")
    @Column(unique = true, nullable = false)
    private String token;

    @JsonProperty("entreprise")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entreprise_id", nullable = false)
    private Entreprise entreprise;

    @JsonProperty("creation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date creationDate = new Date(System.currentTimeMillis());

    @JsonProperty("timeout_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date timeoutDate;

    @PrePersist
    public void prePersist() {
        creationDate = new Date();
        // Set the timeout date to 7 days from now
        timeoutDate = new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000);
    }

    public Invitation() {
    }
    public Invitation(String token, Entreprise entreprise) {
        this.token = token;
        this.entreprise = entreprise;
    }

    @Override
    public boolean isValid() {
        return token != null
            && !token.isEmpty()
            && entreprise != null
            && entreprise.isValid();
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }
    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

}
