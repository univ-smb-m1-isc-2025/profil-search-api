package usmb.info803.profile_search.question_reponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateQuestionReponseBody {

    @JsonProperty("candidature_id")
    private Long candidatureId;

    @JsonProperty("question_id")
    private Long questionId;

    @JsonProperty("reponse")
    private String reponse;

    public CreateQuestionReponseBody() {
    }

    public CreateQuestionReponseBody(Long candidatureId, Long questionId, String reponse) {
        this.candidatureId = candidatureId;
        this.questionId = questionId;
        this.reponse = reponse;
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

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public boolean isValid() {
        return candidatureId != null && questionId != null && reponse != null && !reponse.isEmpty();
    }
}
