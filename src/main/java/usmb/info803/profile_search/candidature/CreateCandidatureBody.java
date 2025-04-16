package usmb.info803.profile_search.candidature;

public class CreateCandidatureBody {
    
    private String emailCandidat;
    private String name;
    private Long offreId;

    public CreateCandidatureBody() {
    }

    public CreateCandidatureBody(String emailCandidat, String name, Long offreId) {
        this.emailCandidat = emailCandidat;
        this.name = name;
        this.offreId = offreId;
    }

    public String getEmailCandidat() {
        return emailCandidat;
    }
    public String getName() {
        return name;
    }
    public Long getOffreId() {
        return offreId;
    }

}
