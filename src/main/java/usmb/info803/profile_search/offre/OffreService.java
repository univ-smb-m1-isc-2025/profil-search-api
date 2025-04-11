package usmb.info803.profile_search.offre;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class OffreService {

    private final OffreRepository offreRepository;

    public OffreService(OffreRepository offreRepository) {
        this.offreRepository = offreRepository;
    }

    public List<Offre> getAllOffres() {
        return offreRepository.findAll();
    }

    public Offre getOffreById(Long id) {
        return offreRepository.findById(id).orElse(null);
    }

    public Offre createOffre(Offre offre) {
        return offreRepository.save(offre);
    }

    public boolean deleteOffre(Long id) {
        if (offreRepository.existsById(id)) {
            offreRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
