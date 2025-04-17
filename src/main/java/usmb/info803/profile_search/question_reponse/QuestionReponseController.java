package usmb.info803.profile_search.question_reponse;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question-reponses")
public class QuestionReponseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final QuestionReponseService questionReponseService;

    public QuestionReponseController(QuestionReponseService questionReponseService) {
        this.questionReponseService = questionReponseService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QuestionReponseDTO> getAllQuestionReponses() {
        logger.info("Get all question-reponses");
        return questionReponseService.getAllQuestionReponses();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionReponseDTO> getQuestionReponseById(@PathVariable("id") long id) {
        logger.info(String.format("Get question-reponse by id: %d", id));
        QuestionReponseDTO questionReponse = questionReponseService.getQuestionReponseById(id);
        if (questionReponse == null) {
            logger.error(String.format("Question-reponse not found: %d", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(questionReponse);
    }

    @GetMapping(value = "/candidature/{candidatureId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionReponseDTO>> getQuestionReponsesByCandidatureId(@PathVariable("candidatureId") long candidatureId) {
        logger.info(String.format("Get question-reponses by candidature id: %d", candidatureId));
        List<QuestionReponseDTO> questionReponses = questionReponseService.getQuestionReponsesByCandidatureId(candidatureId);
        if (questionReponses.isEmpty()) {
            logger.info(String.format("No question-reponses found for candidature: %d", candidatureId));
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(questionReponses);
    }

    @GetMapping(value = "/question/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionReponseDTO>> getQuestionReponsesByQuestionId(@PathVariable("questionId") long questionId) {
        logger.info(String.format("Get question-reponses by question id: %d", questionId));
        List<QuestionReponseDTO> questionReponses = questionReponseService.getQuestionReponsesByQuestionId(questionId);
        if (questionReponses.isEmpty()) {
            logger.info(String.format("No question-reponses found for question: %d", questionId));
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(questionReponses);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionReponseDTO> create(@RequestBody CreateQuestionReponseBody createBody) {
        logger.info("Create question-reponse");
        QuestionReponseDTO createdQuestionReponse = questionReponseService.createQuestionReponse(createBody);
        if (createdQuestionReponse == null) {
            logger.error("Error while creating question-reponse");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(createdQuestionReponse);
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionReponseDTO> updateReponse(@PathVariable("id") long id, @RequestBody String nouvelleReponse) {
        logger.info(String.format("Update reponse for question-reponse id: %d", id));
        QuestionReponseDTO updatedQuestionReponse = questionReponseService.updateReponse(id, nouvelleReponse);
        if (updatedQuestionReponse == null) {
            logger.error(String.format("Question-reponse not found or invalid response: %d", id));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(updatedQuestionReponse);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        logger.info(String.format("Delete question-reponse by id: %d", id));
        if (!questionReponseService.deleteQuestionReponse(id)) {
            logger.error(String.format("Question-reponse not found: %d", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Question-reponse %d not found", id));
        }
        return ResponseEntity.ok(String.format("Question-reponse %d deleted", id));
    }
}
