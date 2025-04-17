package usmb.info803.profile_search.candidature;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, Long> {

    List<Candidature> findByEmailCandidat(String emailCandidat);
    List<Candidature> findByName(String name);
    List<Candidature> findByOffreId(Long offreId);
    List<Candidature> findByAssigneeId(Long assigneeId);
    List<Candidature> findByClosed(boolean closed);
    List<Candidature> findByPositif(boolean positif);
    Candidature findByDeleteToken(String deleteToken);
    void deleteByDeleteToken(String deleteToken);
}
