package usmb.info803.profile_search.entreprise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/entreprises")
public class EntrepriseController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EntrepriseService entrepriseService;

    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Entreprise> entreprises(){
        logger.info("Get all entreprises");
        return entrepriseService.entreprises()
            .stream()
            .toList();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Entreprise entreprise(@PathVariable("id") long id){
        logger.info(String.format("Get entreprise by id : %d", id));
        return entrepriseService.entreprise(id);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") long id){
        logger.info(String.format("Delete entreprise by id : %d", id));
        if(!entrepriseService.delete(id)){
            logger.error(String.format("Entreprise not found : %d", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Entreprise %d not found", id));
        }
        return ResponseEntity.ok(String.format("Entreprise %d deleted", id));
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody CreateEntrepriseBody req){
        String name = req.getName().replaceAll("[\n\r]", "_");
        logger.info(String.format("Create entreprise with name : %s", name));
        if(req.getName() == null || name.isEmpty()) {
            logger.error("Name is empty");
            return ResponseEntity.badRequest().body("Name is empty");
        }
        if(!entrepriseService.create(name)) {
            logger.error(String.format("Entreprise already exists : %s", name));
            return ResponseEntity.badRequest().body(String.format("Entreprise %s already exists", name));
        }
        return ResponseEntity.ok(String.format("Entreprise %s created", name));
    }
}
