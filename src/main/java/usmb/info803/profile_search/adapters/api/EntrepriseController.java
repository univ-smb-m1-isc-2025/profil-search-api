package usmb.info803.profile_search.adapters.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import usmb.info803.profile_search.application.EntrepriseService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class EntrepriseController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EntrepriseService entrepriseService;

    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }
    

    @GetMapping(value = "/api/entreprises")
    public List<String> entreprises(){
        logger.info("Get all entreprises");
        return entrepriseService.entreprises()
            .stream()
            .map(e -> e.getName())
            .collect(toList());
    }
}
