package usmb.info803.profile_search.candidature;

import java.util.List;

import org.springframework.stereotype.Service;

import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.offre.Offre;

@Service
public class CandidatureService {

    private final CandidatureRepository candidatureRepository;

    public CandidatureService(CandidatureRepository candidatureRepository) {
        this.candidatureRepository = candidatureRepository;
    }

    public List<Candidature> getAllCandidatures() {
        return candidatureRepository.findAll();
    }

    public Candidature getCandidatureById(Long id) {
        return candidatureRepository.findById(id).orElse(null);
    }

    public List<Candidature> getCandidatureByEmailCandidat(String emailCandidat) {
        return candidatureRepository.findByEmailCandidat(emailCandidat);
    }

    public List<Candidature> getCandidatureByName(String name) {
        return candidatureRepository.findByName(name);
    }

    public List<Candidature> getCandidatureByOffreId(Long offreId) {
        return candidatureRepository.findByOffreId(offreId);
    }

    public List<Candidature> getCandidatureByAssigneeId(Long assigneeId) {
        return candidatureRepository.findByAssigneeId(assigneeId);
    }

    public List<Candidature> getCandidatureByClosed(boolean closed) {
        return candidatureRepository.findByClosed(closed);
    }

    public List<Candidature> getCandidatureByPositif(boolean positif) {
        return candidatureRepository.findByPositif(positif);
    }

    public Candidature assign(Long candidatureId, Member assignee) {
        Candidature candidature = getCandidatureById(candidatureId);
        if (candidature != null) {
            candidature.setAssignee(assignee);
            return candidatureRepository.save(candidature);
        }
        return null;
    }
    
    public Candidature createCandidature(String emailCandidat, String name, Offre offre) {
        Candidature candidature = new Candidature(emailCandidat, name, offre);
        return candidatureRepository.save(candidature);
    }

    public void deleteCandidature(Long id) {
        candidatureRepository.deleteById(id);
    }

    public Candidature updatePositif(Long candidatureId, boolean positif) {
        Candidature candidature = getCandidatureById(candidatureId);
        if (candidature != null) {
            candidature.setPositif(positif);
            return candidatureRepository.save(candidature);
        }
        return null;
    }

    public Candidature updateClosed(Long candidatureId, boolean closed) {
        Candidature candidature = getCandidatureById(candidatureId);
        if (candidature != null) {
            candidature.setClosed(closed);
            return candidatureRepository.save(candidature);
        }
        return null;
    }
}
