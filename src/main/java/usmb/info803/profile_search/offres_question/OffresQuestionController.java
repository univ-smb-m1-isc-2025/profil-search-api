package usmb.info803.profile_search.offres_question;

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
@RequestMapping("/api/offres-questions")
public class OffresQuestionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OffresQuestionService offresQuestionService;

    public OffresQuestionController(OffresQuestionService offresQuestionService) {
        this.offresQuestionService = offresQuestionService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OffresQuestion> getAllOffresQuestions() {
        logger.info("Get all offres-questions");
        return offresQuestionService.getAllOffresQuestions();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OffresQuestion> getOffresQuestionById(@PathVariable("id") long id) {
        logger.info(String.format("Get offres-question by id: %d", id));
        OffresQuestion offresQuestion = offresQuestionService.getOffresQuestionById(id);
        if (offresQuestion == null) {
            logger.error(String.format("Offres-question not found: %d", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(offresQuestion);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OffresQuestion> create(@RequestBody OffresQuestion offresQuestion) {
        logger.info("Create offres-question");
        OffresQuestion createdOffresQuestion = offresQuestionService.createOffresQuestion(offresQuestion);
        if (createdOffresQuestion == null) {
            logger.error("Error while creating offres-question");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(createdOffresQuestion);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        logger.info(String.format("Delete offres-question by id: %d", id));
        if (!offresQuestionService.deleteOffresQuestion(id)) {
            logger.error(String.format("Offres-question not found: %d", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Offres-question %d not found", id));
        }
        return ResponseEntity.ok(String.format("Offres-question %d deleted", id));
    }
}
