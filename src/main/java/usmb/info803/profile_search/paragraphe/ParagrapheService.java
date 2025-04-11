package usmb.info803.profile_search.paragraphe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParagrapheService {

    @Autowired
    private final ParagrapheRepository paragrapheRepository;

    public ParagrapheService(ParagrapheRepository paragrapheRepository) {
        this.paragrapheRepository = paragrapheRepository;
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
}
