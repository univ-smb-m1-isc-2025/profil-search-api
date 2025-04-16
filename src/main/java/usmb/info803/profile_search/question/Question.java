package usmb.info803.profile_search.question;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import usmb.info803.profile_search.DbEntity;

@Entity
public class Question implements DbEntity {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String question;

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
}
