package usmb.info803.profile_search.logDelCandidature;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface logDelCandidatureRepository extends JpaRepository<LogDelCandidature, Long> {
    
    List<LogDelCandidature> findByEmailCandidat(String emailCandidat);
} 