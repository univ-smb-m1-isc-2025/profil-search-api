package usmb.info803.profile_search.question_reponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionReponseDTO {

    private Long id;

    @JsonProperty("candidature_id")
    private Long candidatureId;

    @JsonProperty("question_id")
    private Long questionId;

    @JsonProperty("question_text")
    private String questionText;

    @JsonProperty("reponse")
    private String reponse;

    public QuestionReponseDTO() {
    }

    public QuestionReponseDTO(Long id, Long candidatureId, Long questionId, String questionText, String reponse) {
        this.id = id;
        this.candidatureId = candidatureId;
        this.questionId = questionId;
        this.questionText = questionText;
        this.reponse = reponse;
    }

    // Factory method pour créer un DTO à partir de l'entité
    public static QuestionReponseDTO fromEntity(QuestionReponse questionReponse) {
        return new QuestionReponseDTO(
                questionReponse.getId(),
                questionReponse.getCandidature() != null ? questionReponse.getCandidature().getId() : null,
                questionReponse.getQuestion() != null ? questionReponse.getQuestion().getId() : null,
                questionReponse.getQuestion() != null ? questionReponse.getQuestion().getQuestion() : null,
                questionReponse.getReponse()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCandidatureId() {
        return candidatureId;
    }

    public void setCandidatureId(Long candidatureId) {
        this.candidatureId = candidatureId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
}
