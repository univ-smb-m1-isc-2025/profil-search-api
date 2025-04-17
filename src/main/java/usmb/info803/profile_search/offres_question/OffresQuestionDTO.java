package usmb.info803.profile_search.offres_question;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OffresQuestionDTO {

    private Long id;

    @JsonProperty("offre_id")
    private Long offreId;

    @JsonProperty("question_id")
    private Long questionId;

    public OffresQuestionDTO() {
    }

    public OffresQuestionDTO(Long id, Long offreId, Long questionId) {
        this.id = id;
        this.offreId = offreId;
        this.questionId = questionId;
    }

    // Factory method pour créer un DTO à partir de l'entité
    public static OffresQuestionDTO fromEntity(OffresQuestion offresQuestion) {
        return new OffresQuestionDTO(
                offresQuestion.getId(),
                offresQuestion.getOffre() != null ? offresQuestion.getOffre().getId() : null,
                offresQuestion.getQuestion() != null ? offresQuestion.getQuestion().getId() : null
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOffreId() {
        return offreId;
    }

    public void setOffreId(Long offreId) {
        this.offreId = offreId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
