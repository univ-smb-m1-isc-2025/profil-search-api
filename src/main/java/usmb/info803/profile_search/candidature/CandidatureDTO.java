package usmb.info803.profile_search.candidature;

import usmb.info803.profile_search.member.Member;

public class CandidatureDTO {

    private Long id;
    private String emailCandidat;
    private String name;
    private Long offreId;
    private Long assigneeId;
    private boolean closed;
    private boolean positif;

    public CandidatureDTO() {
    }

    public CandidatureDTO(Candidature candidature) {
        this.id = candidature.getId();
        this.emailCandidat = candidature.getEmailCandidat();
        this.name = candidature.getName();
        this.offreId = candidature.getOffre().getId();
        Member assignee = candidature.getAssignee();
        if(assignee != null) {
            this.assigneeId = assignee.getId();
        } else {
            this.assigneeId = null;
        }
        this.closed = candidature.isClosed();
        this.positif = candidature.isPositif();
    }

    public CandidatureDTO(Long id, String emailCandidat, String name, Long offreId, Long assigneeId, boolean closed, boolean positif) {
        this.id = id;
        this.emailCandidat = emailCandidat;
        this.name = name;
        this.offreId = offreId;
        this.assigneeId = assigneeId;
        this.closed = closed;
        this.positif = positif;
    }

    public Long getId() {
        return id;
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
    public Long getAssigneeId() {
        return assigneeId;
    }
    public boolean isClosed() {
        return closed;
    }
    public boolean isPositif() {
        return positif;
    }
    
}
