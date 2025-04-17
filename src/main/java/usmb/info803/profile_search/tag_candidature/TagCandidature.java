package usmb.info803.profile_search.tag_candidature;

import usmb.info803.profile_search.tag.Tag;
import usmb.info803.profile_search.candidature.Candidature;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import usmb.info803.profile_search.DbEntity;

@Entity
@jakarta.persistence.Table(
    name = "tag_candidature",
    uniqueConstraints = @jakarta.persistence.UniqueConstraint(columnNames = {"tag_id", "candidature_id"})
)
public class TagCandidature implements DbEntity {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("tag")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @JsonProperty("candidature")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidature_id", nullable = false)
    private Candidature candidature;

    public TagCandidature() {
    }

    public TagCandidature(Tag tag, Candidature candidature) {
        this.tag = tag;
        this.candidature = candidature;
    }

    public boolean isValid() {
        return tag != null 
            && candidature != null
            && tag.isValid()
            && candidature.isValid();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Tag getTag() {
        return tag;
    }
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Candidature getCandidature() {
        return candidature;
    }
    public void setCandidature(Candidature candidature) {
        this.candidature = candidature;
    }
    
}
