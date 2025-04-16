package usmb.info803.profile_search.offre;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import usmb.info803.profile_search.DbEntity;
import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.paragraphe.Paragraphe;

@Entity
public class Offre implements DbEntity {

    @Override
    public boolean isValid() {
        return titre != null && !titre.isEmpty()
                && userSource != null
                && userSource.isValid()
                && paragraphes != null
                && !paragraphes.isEmpty();
    }

    @Id
    @GeneratedValue
    private Long Id;

    @JsonProperty("titre")
    private String titre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_source_id", nullable = false)
    @JsonProperty("user_source")
    private Member userSource;

    @JsonProperty("est_publiee")
    private boolean estPubliee;

    @OneToMany(mappedBy = "offre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paragraphe> paragraphes = new ArrayList<>();

    public Offre() {
    }

    public Offre(String titre, Member user_source, boolean est_publiée) {
        this.titre = titre;
        this.userSource = user_source;
        this.estPubliee = est_publiée;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long _Id) {
        this.Id = _Id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Member getuser_source() {
        return userSource;
    }

    public void setuser_source(Member user_source) {
        this.userSource = user_source;
    }

    public boolean isEst_publiée() {
        return estPubliee;
    }

    public void setEst_publiée(boolean est_publiée) {
        this.estPubliee = est_publiée;
    }

    public List<Paragraphe> getParagraphes() {
        return paragraphes;
    }

    public void setParagraphes(List<Paragraphe> paragraphes) {
        this.paragraphes = paragraphes;
    }
}
