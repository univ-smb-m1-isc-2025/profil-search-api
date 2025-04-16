package usmb.info803.profile_search.bullet_point;

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
    private Long _Id;

    @JsonProperty("bullet_point")
    private String bulletPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offre_id", nullable = false)
    private Offre offre;

    public BulletPoint() {
    }

    public BulletPoint(String bulletPoint, Offre offre) {
        this.bulletPoint = bulletPoint;
        this.offre = offre;
    }

    public Long get_Id() {
        return _Id;
    }

    public void set_Id(Long _Id) {
        this._Id = _Id;
    }

    public String getBulletPoint() {
        return bulletPoint;
    }

    public void setBulletPoint(String bulletPoint) {
        this.bulletPoint = bulletPoint;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    @Override
    public boolean isValid() {
        return bulletPoint != null && !bulletPoint.isEmpty()
                && offre != null
                && offre.isValid();
    }
}
