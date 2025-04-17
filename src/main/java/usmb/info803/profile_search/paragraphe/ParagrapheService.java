package usmb.info803.profile_search.paragraphe;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import usmb.info803.profile_search.offre.Offre;
import usmb.info803.profile_search.offre.OffreService;

@Service
public class ParagrapheService {

    private final ParagrapheRepository paragrapheRepository;
    private final OffreService offreService;

    public ParagrapheService(ParagrapheRepository paragrapheRepository, OffreService offreService) {
        this.paragrapheRepository = paragrapheRepository;
        this.offreService = offreService;
    }

    public List<Paragraphe> getAllParagraphes() {
        return paragrapheRepository.findAll();
    }

    public Paragraphe getParagrapheById(Long id) {
        return paragrapheRepository.findById(id).orElse(null);
    }

    public Paragraphe createParagraphe(Paragraphe paragraphe) {
        return paragrapheRepository.save(paragraphe);
    }

    public boolean deleteParagraphe(Long id) {
        if (paragrapheRepository.existsById(id)) {
            paragrapheRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Paragraphe createParagrapheFromDTO(ParagrapheCreationDTO dto) {
        Offre offre = offreService.getOffreById(dto.getOffreId());
        if (offre == null) {
            return null; // Offre non trouvée
        }

        Paragraphe paragraphe = new Paragraphe(dto.getContenu(), offre);
        return paragrapheRepository.save(paragraphe);
    }
}
