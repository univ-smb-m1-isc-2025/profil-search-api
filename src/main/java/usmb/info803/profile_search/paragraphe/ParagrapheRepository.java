package usmb.info803.profile_search.paragraphe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParagrapheRepository extends JpaRepository<Paragraphe, Long> {
}
