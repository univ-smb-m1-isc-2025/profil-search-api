package usmb.info803.profile_search.entreprise;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EntrepriseService {
    
    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseService(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    public List<Entreprise> entreprises() {
        return entrepriseRepository.findAll();
    }

    public Entreprise entreprise(long id) {
        return entrepriseRepository.findById(id).orElse(null);
    }

    public boolean delete(Long id) {
        if (entrepriseRepository.existsById(id)) {
            entrepriseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean create(String name) {
        Entreprise entreprise = entrepriseRepository.findByName(name);
        if(entreprise != null) {
            return false;
        }
        entrepriseRepository.save(new Entreprise(name));
        return true;
    }
}
