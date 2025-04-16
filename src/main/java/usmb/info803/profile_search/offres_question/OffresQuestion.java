package usmb.info803.profile_search.offres_question;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import usmb.info803.profile_search.DbEntity;
import usmb.info803.profile_search.offre.Offre;
import usmb.info803.profile_search.question.Question;

@Entity
public class OffresQuestion implements DbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offre_id", nullable = false)
    private Offre offre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public OffresQuestion() {
    }

    public OffresQuestion(Offre offre, Question question) {
        this.offre = offre;
        this.question = question;
    }

    public Long get_Id() {
        return _Id;
    }

    public void set_Id(Long _Id) {
        this._Id = _Id;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public boolean isValid() {
        return offre != null && offre.isValid()
                && question != null && question.isValid();
    }
}
