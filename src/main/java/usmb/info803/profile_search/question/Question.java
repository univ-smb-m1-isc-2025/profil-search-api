package usmb.info803.profile_search.question;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import usmb.info803.profile_search.DbEntity;
import usmb.info803.profile_search.offres_question.OffresQuestion;

@Entity
public class Question implements DbEntity {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String question;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OffresQuestion> offres = new ArrayList<>();

    public Question() {
    }

    public Question(String question) {
        this.question = question;
    }

    @Override
    public boolean isValid() {
        return question != null
                && !question.isEmpty()
                && question.length() <= 255
                && id != null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<OffresQuestion> getOffres() {
        return offres;
    }

    public void setOffres(List<OffresQuestion> offres) {
        this.offres = offres;
    }

    public void addOffre(OffresQuestion offresQuestion) {
        offres.add(offresQuestion);
        offresQuestion.setQuestion(this);
    }

    public void removeOffre(OffresQuestion offresQuestion) {
        offres.remove(offresQuestion);
        offresQuestion.setQuestion(null);
    }
}
