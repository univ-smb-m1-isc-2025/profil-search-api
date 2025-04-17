package usmb.info803.profile_search.paragraphe;

import java.util.List;

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

@RestController
@RequestMapping("/api/paragraphes")
public class ParagrapheController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ParagrapheService paragrapheService;

    public ParagrapheController(ParagrapheService paragrapheService) {
        this.paragrapheService = paragrapheService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Paragraphe> getAllParagraphes() {
        logger.info("Get all paragraphes");
        return paragrapheService.getAllParagraphes();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Paragraphe> getParagrapheById(@PathVariable("id") long id) {
        logger.info(String.format("Get paragraphe by id : %d", id));
        Paragraphe paragraphe = paragrapheService.getParagrapheById(id);
        if (paragraphe == null) {
            logger.error(String.format("Paragraphe not found : %d", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(paragraphe);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Paragraphe> create(@RequestBody ParagrapheCreationDTO paragrapheDTO) {
        logger.info(String.format("Create paragraphe with contenu : %s", paragrapheDTO.getContenu()));
        Paragraphe createdParagraphe = paragrapheService.createParagrapheFromDTO(paragrapheDTO);
        if (createdParagraphe == null) {
            logger.error("Error while creating paragraphe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(createdParagraphe);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        logger.info(String.format("Delete paragraphe by id : %d", id));
        if (!paragrapheService.deleteParagraphe(id)) {
            logger.error(String.format("Paragraphe not found : %d", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Paragraphe %d not found", id));
        }
        return ResponseEntity.ok(String.format("Paragraphe %d deleted", id));
    }
}
