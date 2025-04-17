package usmb.info803.profile_search.bullet_point;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import usmb.info803.profile_search.DbEntity;
import usmb.info803.profile_search.offre.Offre;

@Entity
public class BulletPoint implements DbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("bullet_point")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offre_id", nullable = false)
    @JsonBackReference
    private Offre offre;

    public BulletPoint() {
    }

    public BulletPoint(String content, Offre offre) {
        this.content = content;
        this.offre = offre;
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

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    @Override
    public boolean isValid() {
        return content != null && !content.isEmpty()
                && offre != null
                && offre.isValid();
    }
}
