package usmb.info803.profile_search.candidature;

public class DeleteCandidatureBody {

    private String emailCandidat;
    private String raison;

    public DeleteCandidatureBody() {
    }

    public DeleteCandidatureBody(String emailCandidat, String raison) {
        this.emailCandidat = emailCandidat;
        this.raison = raison;
    }

    public String getEmailCandidat() {
        return emailCandidat;
    }
    public String getRaison() {
        return raison;
    }
    
}
