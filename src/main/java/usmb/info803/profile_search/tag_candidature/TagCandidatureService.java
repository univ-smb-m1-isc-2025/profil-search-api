package usmb.info803.profile_search.tag_candidature;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TagCandidatureService {
    
    private final TagCandidatureRepository tagCandidatureRepository;

    public TagCandidatureService(TagCandidatureRepository tagCandidatureRepository) {
        this.tagCandidatureRepository = tagCandidatureRepository;
    }

    public List<TagCandidature> findByTagId(Long tagId) {
        return tagCandidatureRepository.findByTagId(tagId);
    }

    public List<TagCandidature> findByCandidatureId(Long candidatureId) {
        return tagCandidatureRepository.findByCandidatureId(candidatureId);
    }

    public TagCandidature findByTagIdAndCandidatureId(Long tagId, Long candidatureId) {
        return tagCandidatureRepository.findByTagIdAndCandidatureId(tagId, candidatureId);
    }

    public TagCandidature save(TagCandidature tagCandidature) {
        if(findByTagIdAndCandidatureId(tagCandidature.getTag().getId(), tagCandidature.getCandidature().getId()) != null) {
            return null; // TagCandidature already exists
        }
        return tagCandidatureRepository.save(tagCandidature);
    }

    public void deleteByCandidatureId(Long candidatureId) {
        tagCandidatureRepository.deleteByCandidatureId(candidatureId);
    }

    public void deleteByTagIdAndCandidatureId(Long tagId, Long candidatureId) {
        tagCandidatureRepository.deleteByTagIdAndCandidatureId(tagId, candidatureId);
    }

    public void delete(TagCandidature tagCandidature) {
        tagCandidatureRepository.delete(tagCandidature);
    }
}
