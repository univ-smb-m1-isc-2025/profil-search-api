package usmb.info803.profile_search.bullet_point;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BulletPointDTO {

    private Long id;

    @JsonProperty("bullet_point")
    private String content;

    @JsonProperty("offre_id")
    private Long offreId;

    public BulletPointDTO() {
    }

    public BulletPointDTO(Long id, String content, Long offreId) {
        this.id = id;
        this.content = content;
        this.offreId = offreId;
    }

    // Factory method pour créer un DTO à partir de l'entité
    public static BulletPointDTO fromEntity(BulletPoint bulletPoint) {
        return new BulletPointDTO(
                bulletPoint.getId(),
                bulletPoint.getContent(),
                bulletPoint.getOffre() != null ? bulletPoint.getOffre().getId() : null
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
