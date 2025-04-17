package usmb.info803.profile_search.offres_question;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateOffresQuestionBody {

    @JsonProperty("offre_id")
    private Long offreId;

    @JsonProperty("question_id")
    private Long questionId;

    public CreateOffresQuestionBody() {
    }

    public CreateOffresQuestionBody(Long offreId, Long questionId) {
        this.offreId = offreId;
        this.questionId = questionId;
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

    public boolean isValid() {
        return offreId != null && questionId != null;
    }
}
