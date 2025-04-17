package usmb.info803.profile_search.tag_candidature;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagCandidatureRepository extends JpaRepository<TagCandidature, Long> {

    List<TagCandidature> findByTagId(Long tagId);
    List<TagCandidature> findByCandidatureId(Long candidatureId);
    TagCandidature findByTagIdAndCandidatureId(Long tagId, Long candidatureId);
    void deleteByCandidatureId(Long candidatureId);
    void deleteByTagIdAndCandidatureId(Long tagId, Long candidatureId);
    
}
