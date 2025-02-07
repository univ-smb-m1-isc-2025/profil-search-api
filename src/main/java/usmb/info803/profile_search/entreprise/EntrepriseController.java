package usmb.info803.profile_search.entreprise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/entreprises")
public class EntrepriseController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EntrepriseService entrepriseService;

    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }
    

    @GetMapping(value = "/all")
    public List<Entreprise> entreprises(){
        logger.info("Get all entreprises");
        return entrepriseService.entreprises()
            .stream()
            .collect(toList());
    }

    @GetMapping(value = "/{id}")
    public Entreprise entreprise(long id){
        logger.info("Get entreprise by id : " + id);
        return entrepriseService.entreprise(id);
    }

    @GetMapping(value = "/delete/{id}")
    public void delete(long id){
        logger.info("Delete entreprise by id : " + id);
        entrepriseService.delete(id);
    }

    @GetMapping(value = "/create/{name}")
    public void create(String name){
        logger.info("Create entreprise with name : " + name);
        entrepriseService.create(name);
    }
}
