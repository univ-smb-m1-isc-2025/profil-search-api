package usmb.info803.profile_search.candidature;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import usmb.info803.profile_search.DbEntity;
import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.offre.Offre;
import usmb.info803.profile_search.question_reponse.QuestionReponse;

@Entity
public class Candidature implements DbEntity {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;

    @JsonProperty("emailCandidat")
    private String emailCandidat;

    @JsonProperty("name")
    private String name;

    @JsonProperty("offre")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offre_id", nullable = false)
    private Offre offre;

    @JsonProperty("assignee")
    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", nullable = true)
    private Member assignee;

    @JsonProperty("closed")
    private boolean closed;

    @JsonProperty("positif")
    private boolean positif;

    @OneToMany(mappedBy = "candidature", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<QuestionReponse> questionReponses = new ArrayList<>();

    public Candidature() {
    }

    public Candidature(String emailCandidat, String name, Offre offre) {
        this.emailCandidat = emailCandidat;
        this.name = name;
        this.offre = offre;
        this.assignee = null;
        this.closed = false;
        this.positif = false;
    }

    @Override
    public boolean isValid() {
        return emailCandidat != null
                && !emailCandidat.isEmpty()
                && offre != null
                && offre.isValid()
                && name != null
                && !name.isEmpty();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailCandidat() {
        return emailCandidat;
    }

    public void setEmailCandidat(String emailCandidat) {
        this.emailCandidat = emailCandidat;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public Member getAssignee() {
        return assignee;
    }

    public void setAssignee(Member assignee) {
        this.assignee = assignee;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isPositif() {
        return positif;
    }

    public void setPositif(boolean positif) {
        this.positif = positif;
    }

    public List<QuestionReponse> getQuestionReponses() {
        return questionReponses;
    }

    public void setQuestionReponses(List<QuestionReponse> questionReponses) {
        this.questionReponses = questionReponses;
    }

    public void addQuestionReponse(QuestionReponse questionReponse) {
        questionReponses.add(questionReponse);
        questionReponse.setCandidature(this);
    }

    public void removeQuestionReponse(QuestionReponse questionReponse) {
        questionReponses.remove(questionReponse);
        questionReponse.setCandidature(null);
    }
}
