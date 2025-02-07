package usmb.info803.profile_search.entreprise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public Entreprise entreprise(@PathVariable("id") String id_s){
        logger.info("Get entreprise by id : " + id_s);
        long id = -1;
        try {
            id = Long.parseLong(id_s);
        } catch (NumberFormatException e) {
            logger.error("Invalid id : " + id_s);
        }
        if(id != -1){
            return entrepriseService.entreprise(id);
        }
        return null;
    }

    @GetMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") String id_s){
        logger.info("Delete entreprise by id : " + id_s);
        long id = -1;
        try {
            id = Long.parseLong(id_s);
            entrepriseService.delete(id);
        } catch (NumberFormatException e) {
            logger.error("Invalid id : " + id_s);
        }
        if(id != -1){
            entrepriseService.delete(id);
        }
    }

    @GetMapping(value = "/create/{name}")
    public void create(@PathVariable("name") String name){
        logger.info("Create entreprise with name : " + name);
        if(!entrepriseService.create(name)) {
            logger.error("Entreprise already exists : " + name);
        }
    }
}
