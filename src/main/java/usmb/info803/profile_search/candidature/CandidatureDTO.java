package usmb.info803.profile_search.candidature;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.question_reponse.QuestionReponseDTO;
import usmb.info803.profile_search.tag.TagDTO;
import usmb.info803.profile_search.tag_candidature.TagCandidature;
import usmb.info803.profile_search.tag_candidature.TagCandidatureService;

public class CandidatureDTO {

    private Long id;
    private String emailCandidat;
    private String name;
    private Long offreId;
    private Long assigneeId;
    private boolean closed;
    private boolean positif;
    private List<TagDTO> tagList;
    private List<QuestionReponseDTO> questionReponses;
    private String deleteToken;

    public CandidatureDTO() {
    }

    public CandidatureDTO(Candidature candidature, TagCandidatureService tagCandidatureService) {
        this(candidature);
        List<TagCandidature> tagcandList = tagCandidatureService.findByCandidatureId(id);
        List<TagDTO> tagList = tagcandList.stream()
                .map(tagCandidature -> tagCandidature.getTag())
                .map(TagDTO::new)
                .toList();
        this.tagList = tagList;

        // Ajouter les réponses aux questions
        if (candidature.getQuestionReponses() != null) {
            this.questionReponses = candidature.getQuestionReponses().stream()
                    .map(QuestionReponseDTO::fromEntity)
                    .collect(Collectors.toList());
        } else {
            this.questionReponses = new ArrayList<>();
        }
    }

    public CandidatureDTO(Candidature candidature) {
        this.id = candidature.getId();
        this.emailCandidat = candidature.getEmailCandidat();
        this.name = candidature.getName();
        this.offreId = candidature.getOffre().getId();
        Member assignee = candidature.getAssignee();
        if (assignee != null) {
            this.assigneeId = assignee.getId();
        } else {
            this.assigneeId = null;
        }
        this.closed = candidature.isClosed();
        this.positif = candidature.isPositif();
        this.deleteToken = candidature.getDeleteToken();
        this.tagList = new ArrayList<>();
        this.questionReponses = new ArrayList<>();
    }

    public CandidatureDTO(Long id, String emailCandidat, String name, Long offreId, Long assigneeId, boolean closed, boolean positif, List<TagDTO> tagList) {
        this.id = id;
        this.emailCandidat = emailCandidat;
        this.name = name;
        this.offreId = offreId;
        this.assigneeId = assigneeId;
        this.closed = closed;
        this.positif = positif;
        this.tagList = tagList;
        this.questionReponses = new ArrayList<>();
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

    public List<TagDTO> getTagList() {
        return tagList;
    }

    public List<QuestionReponseDTO> getQuestionReponses() {
        return questionReponses;
    }

    public String getDeleteToken() {
        return deleteToken;
    }

}
