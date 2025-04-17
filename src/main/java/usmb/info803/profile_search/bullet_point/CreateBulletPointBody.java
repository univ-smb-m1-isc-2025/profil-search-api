package usmb.info803.profile_search.bullet_point;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateBulletPointBody {

    @JsonProperty("bullet_point")
    private String content;

    @JsonProperty("offre_id")
    private Long offreId;

    public CreateBulletPointBody() {
    }

    public CreateBulletPointBody(String content, Long offreId) {
        this.content = content;
        this.offreId = offreId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getOffreId() {
        return offreId;
    }

    public void setOffreId(Long offreId) {
        this.offreId = offreId;
    }

    public boolean isValid() {
        return content != null && !content.isEmpty() && offreId != null;
    }
}
