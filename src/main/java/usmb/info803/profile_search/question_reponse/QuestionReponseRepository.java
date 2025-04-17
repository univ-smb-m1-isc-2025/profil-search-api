package usmb.info803.profile_search.question_reponse;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionReponseRepository extends JpaRepository<QuestionReponse, Long> {

    List<QuestionReponse> findByCandidatureId(Long candidatureId);

    List<QuestionReponse> findByQuestionId(Long questionId);

    List<QuestionReponse> findByCandidatureIdAndQuestionId(Long candidatureId, Long questionId);
}
