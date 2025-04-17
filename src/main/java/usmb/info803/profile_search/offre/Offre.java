package usmb.info803.profile_search.offre;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import usmb.info803.profile_search.bullet_point.BulletPoint;
import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.offres_question.OffresQuestion;
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

    @JsonProperty("user_source")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_source_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Member userSource;

    @JsonProperty("est_publiee")
    private boolean estPubliee;

    @OneToMany(mappedBy = "offre", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<Paragraphe> paragraphes = new ArrayList<>();

    @OneToMany(mappedBy = "offre", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<BulletPoint> bulletPoints = new ArrayList<>();

    @OneToMany(mappedBy = "offre", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<OffresQuestion> questions = new ArrayList<>();

    public Offre() {
    }

    public Offre(String titre, Member user_source, boolean est_publiee) {
        this.titre = titre;
        this.userSource = user_source;
        this.estPubliee = est_publiee;
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

    public Member getuserSource() {
        return userSource;
    }

    public void setuserSource(Member user_source) {
        this.userSource = user_source;
    }

    public boolean isEstPubliee() {
        return estPubliee;
    }

    public void setEstPubliee(boolean est_publiee) {
        this.estPubliee = est_publiee;
    }

    public List<Paragraphe> getParagraphes() {
        return paragraphes;
    }

    public void setParagraphes(List<Paragraphe> paragraphes) {
        this.paragraphes = paragraphes;
    }

    public List<BulletPoint> getBulletPoints() {
        return bulletPoints;
    }

    public void setBulletPoints(List<BulletPoint> bulletPoints) {
        this.bulletPoints = bulletPoints;
    }

    public List<OffresQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<OffresQuestion> questions) {
        this.questions = questions;
    }

    public void addQuestion(OffresQuestion offresQuestion) {
        questions.add(offresQuestion);
        offresQuestion.setOffre(this);
    }

    public void removeQuestion(OffresQuestion offresQuestion) {
        questions.remove(offresQuestion);
        offresQuestion.setOffre(null);
    }

    public void addBulletPoint(BulletPoint bulletPoint) {
        bulletPoints.add(bulletPoint);
        bulletPoint.setOffre(this);
    }

    public void removeBulletPoint(BulletPoint bulletPoint) {
        bulletPoints.remove(bulletPoint);
        bulletPoint.setOffre(null);
    }
}
