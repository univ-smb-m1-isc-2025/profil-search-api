package usmb.info803.profile_search.entreprise;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void delete(long id) {
        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);
        entreprise.ifPresent(entrepriseRepository::delete);
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
