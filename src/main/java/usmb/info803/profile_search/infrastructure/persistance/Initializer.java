package usmb.info803.profile_search.infrastructure.persistance;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class Initializer {
    
    private final EntrepriseRepository entrepriseRepository;

    public Initializer(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    @PostConstruct
    public void init() {
        entrepriseRepository.deleteAllInBatch();

        if(entrepriseRepository.findAll().isEmpty()){
            entrepriseRepository.save(new Entreprise("USMB"));
            entrepriseRepository.save(new Entreprise("Google"));
            entrepriseRepository.save(new Entreprise("Apple"));
        }
    }
}
