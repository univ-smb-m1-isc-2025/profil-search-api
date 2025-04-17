package usmb.info803.profile_search.member;

import usmb.info803.profile_search.DbEntity;
import usmb.info803.profile_search.Utils;
import usmb.info803.profile_search.entreprise.Entreprise;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Member implements DbEntity {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("prenom")
    private String prenom;

    @JsonProperty("email")
    @Column(unique = true, nullable = false)
    private String email;

    @JsonProperty("actif")
    private boolean actif;

    @JsonProperty("entreprise")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entreprise_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Entreprise entreprise;

    @JsonProperty("token")
    @Column(unique = true, nullable = false)
    private String token;

    public Member() {
    }

    public Member(String nom, String prenom, String email, Entreprise entreprise) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.actif = true;
        this.entreprise = entreprise;
        this.token = Utils.randomString(100);
    }

    @Override
    public boolean isValid() {
        return nom != null
            && !nom.isEmpty()
            && prenom != null
            && !prenom.isEmpty()
            && email != null 
            && !email.isEmpty()
            && entreprise != null
            && entreprise.isValid();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActif() {
        return actif;
    }
    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }
    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
