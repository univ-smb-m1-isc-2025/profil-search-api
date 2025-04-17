package usmb.info803.profile_search.offres_question;

import com.fasterxml.jackson.annotation.JsonProperty;

import usmb.info803.profile_search.question.Question;

public class OffreQuestionDTO {

    private Long id;

    @JsonProperty("question_text")
    private String questionText;

    public OffreQuestionDTO() {
    }

    public OffreQuestionDTO(Long id, String questionText) {
        this.id = id;
        this.questionText = questionText;
    }

    // Factory method pour créer le DTO à partir d'une Question
    public static OffreQuestionDTO fromQuestion(Question question) {
        return new OffreQuestionDTO(
                question.getId(),
                question.getQuestion()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
