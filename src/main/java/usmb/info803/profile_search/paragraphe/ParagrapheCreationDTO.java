package usmb.info803.profile_search.paragraphe;

public class ParagrapheCreationDTO {

    private String contenu;
    private Long offreId;

    public ParagrapheCreationDTO() {
    }

    public ParagrapheCreationDTO(String contenu, Long offreId) {
        this.contenu = contenu;
        this.offreId = offreId;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Long getOffreId() {
        return offreId;
    }

    public void setOffreId(Long offreId) {
        this.offreId = offreId;
    }
}
