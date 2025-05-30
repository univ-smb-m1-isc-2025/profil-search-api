package usmb.info803.profile_search.offre;

import java.util.List;
import java.util.stream.Collectors;

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
@RequestMapping("/api/offres")
public class OffreController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OffreService offreService;

    public OffreController(OffreService offreService) {
        this.offreService = offreService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OffreDTO> all() {
        logger.info("Get all offres");
        return offreService.getAllOffres().stream()
                .map(OffreDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OffreDTO> offre(@PathVariable("id") long id) {
        logger.info(String.format("Get offre by id : %d", id));
        Offre offre = offreService.getOffreById(id);
        if (offre == null) {
            logger.error(String.format("Offre not found : %d", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(OffreDTO.fromEntity(offre));
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OffreDTO> create(@RequestBody OffreCreationDTO offreDTO) {
        logger.info(String.format("Create offre with titre : %s et membre id : %d",
                offreDTO.getTitre(), offreDTO.getMemberId()));

        Offre createdOffre = offreService.createOffreFromDTO(offreDTO);
        if (createdOffre == null) {
            logger.error("Error while creating offre: member not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(OffreDTO.fromEntity(createdOffre));
    }

    @PostMapping(value = "/create/full", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OffreDTO> createFull(@RequestBody Offre offre) {
        logger.info(String.format("Create offre with titre : %s", offre.getTitre()));
        Offre createdOffre = offreService.createOffre(offre);
        if (createdOffre == null) {
            logger.error("Error while creating offre");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(OffreDTO.fromEntity(createdOffre));
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        logger.info(String.format("Delete offre by id : %d", id));
        if (!offreService.deleteOffre(id)) {
            logger.error(String.format("Offre not found : %d", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Offre %d not found", id));
        }
        return ResponseEntity.ok(String.format("Offre %d deleted", id));
    }
}
