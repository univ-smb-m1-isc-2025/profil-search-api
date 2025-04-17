package usmb.info803.profile_search.bullet_point;

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
@RequestMapping("/api/bullet-points")
public class BulletPointController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BulletPointService bulletPointService;

    public BulletPointController(BulletPointService bulletPointService) {
        this.bulletPointService = bulletPointService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BulletPointDTO> getAllBulletPoints() {
        logger.info("Get all bullet points");
        return bulletPointService.getAllBulletPoints();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BulletPointDTO> getBulletPointById(@PathVariable("id") long id) {
        logger.info(String.format("Get bullet point by id: %d", id));
        BulletPointDTO bulletPoint = bulletPointService.getBulletPointById(id);
        if (bulletPoint == null) {
            logger.error(String.format("Bullet point not found: %d", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(bulletPoint);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BulletPointDTO> create(@RequestBody CreateBulletPointBody createBody) {
        logger.info("Create bullet point");
        BulletPointDTO createdBulletPoint = bulletPointService.createBulletPoint(createBody);
        if (createdBulletPoint == null) {
            logger.error("Error while creating bullet point");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(createdBulletPoint);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        logger.info(String.format("Delete bullet point by id: %d", id));
        if (!bulletPointService.deleteBulletPoint(id)) {
            logger.error(String.format("Bullet point not found: %d", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Bullet point %d not found", id));
        }
        return ResponseEntity.ok(String.format("Bullet point %d deleted", id));
    }

    @GetMapping(value = "/offre/{offreId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BulletPointDTO>> getBulletPointsByOffreId(@PathVariable("offreId") long offreId) {
        logger.info(String.format("Get bullet points for offre id: %d", offreId));
        List<BulletPointDTO> bulletPoints = bulletPointService.getBulletPointsByOffreId(offreId);
        if (bulletPoints == null || bulletPoints.isEmpty()) {
            logger.info(String.format("No bullet points found for offre: %d", offreId));
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bulletPoints);
    }
}
