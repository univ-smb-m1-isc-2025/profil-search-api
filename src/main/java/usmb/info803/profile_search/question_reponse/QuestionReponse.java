package usmb.info803.profile_search.question_reponse;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import usmb.info803.profile_search.DbEntity;
import usmb.info803.profile_search.candidature.Candidature;
import usmb.info803.profile_search.question.Question;

@Entity
public class QuestionReponse implements DbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidature_id", nullable = false)
    @JsonBackReference
    private Candidature candidature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonBackReference
    private Question question;

    @JsonProperty("reponse")
    private String reponse;

    public QuestionReponse() {
    }

    public QuestionReponse(Candidature candidature, Question question, String reponse) {
        this.candidature = candidature;
        this.question = question;
        this.reponse = reponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidature getCandidature() {
        return candidature;
    }

    public void setCandidature(Candidature candidature) {
        this.candidature = candidature;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    @Override
    public boolean isValid() {
        return candidature != null && candidature.isValid()
                && question != null && question.isValid()
                && reponse != null && !reponse.isEmpty();
    }
}
