package usmb.info803.profile_search.offres_question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffresQuestionRepository extends JpaRepository<OffresQuestion, Long> {
}
