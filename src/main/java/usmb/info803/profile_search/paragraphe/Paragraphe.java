package usmb.info803.profile_search.paragraphe;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import usmb.info803.profile_search.DbEntity;
import usmb.info803.profile_search.offre.Offre;

@Entity
public class Paragraphe implements DbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _Id;

    @JsonProperty("contenu")
    private String contenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offre_id", nullable = false)
    private Offre $offre;

    public Paragraphe() {
    }

    public Paragraphe(String contenu, Offre $offre) {
        this.contenu = contenu;
        this.$offre = $offre;
    }

    public Long get_Id() {
        return _Id;
    }

    public void set_Id(Long _Id) {
        this._Id = _Id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Offre get$offre() {
        return $offre;
    }

    public void set$offre(Offre $offre) {
        this.$offre = $offre;
    }

    @Override
    public boolean isValid() {
        return contenu != null && !contenu.isEmpty()
                && $offre != null
                && $offre.isValid();
    }
}
