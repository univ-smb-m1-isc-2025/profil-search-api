package usmb.info803.profile_search.reponse_question;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import usmb.info803.profile_search.DbEntity;
import usmb.info803.profile_search.question.Question;

@Entity
public class ReponseQuestion implements DbEntity {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("question")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @JsonProperty("reponse")
    private String reponse;

    public ReponseQuestion() {
    }

    public ReponseQuestion(Question question, String reponse) {
        this.question = question;
        this.reponse = reponse;
    }

    public boolean isValid() {
        return question != null 
            && reponse != null
            && question.isValid()
            && !reponse.isBlank();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    
}
