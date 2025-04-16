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
                && user_source != null
                && user_source.isValid()
                && paragraphes != null
                && !paragraphes.isEmpty();
    }

    @Id
    @GeneratedValue
    private Long _Id;

    @JsonProperty("titre")
    private String titre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_source_id", nullable = false)
    @JsonProperty("user_source")
    private Member user_source;

    @JsonProperty("est_publiee")
    private boolean est_publiee;

    @OneToMany(mappedBy = "offre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paragraphe> paragraphes = new ArrayList<>();

    public Offre() {
    }

    public Offre(String titre, Member user_source, boolean est_publiee) {
        this.titre = titre;
        this.user_source = user_source;
        this.est_publiee = est_publiee;
    }

    public Long get_Id() {
        return _Id;
    }

    public void set_Id(Long _Id) {
        this._Id = _Id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Member getuser_source() {
        return user_source;
    }

    public void setuser_source(Member user_source) {
        this.user_source = user_source;
    }

    public boolean isEst_publiee() {
        return est_publiee;
    }

    public void setEst_publiee(boolean est_publiee) {
        this.est_publiee = est_publiee;
    }

    public List<Paragraphe> getParagraphes() {
        return paragraphes;
    }

    public void setParagraphes(List<Paragraphe> paragraphes) {
        this.paragraphes = paragraphes;
    }
}
