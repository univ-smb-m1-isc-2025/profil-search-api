package usmb.info803.profile_search.offre;

public class OffreCreationDTO {

    private String titre;
    private Long memberId;
    private boolean est_publiee;

    public OffreCreationDTO() {
    }

    public OffreCreationDTO(String titre, Long memberId, boolean est_publiee) {
        this.titre = titre;
        this.memberId = memberId;
        this.est_publiee = est_publiee;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public boolean isEst_publiee() {
        return est_publiee;
    }

    public void setEst_publiee(boolean est_publiee) {
        this.est_publiee = est_publiee;
    }
}
