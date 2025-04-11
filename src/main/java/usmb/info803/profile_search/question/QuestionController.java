package usmb.info803.profile_search.question;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Question> questions() {
        logger.info("Get all questions");
        return questionService.all()
            .stream()
            .toList();
    }
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Question question(@PathVariable("id") long id) {
        logger.info(String.format("Get question by id : %d", id));
        return questionService.question(id);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody String question) {
        logger.info(String.format("Create question with name : %s", question));
        if(question == null || question.isEmpty()) {
            logger.error("Question is empty");
            return ResponseEntity.badRequest().body("Question is empty");
        }
        if(!questionService.create(question)) {
            logger.error(String.format("Question already exists : %s", question));
            return ResponseEntity.badRequest().body(String.format("Question already exists : %s", question));
        }
        return ResponseEntity.ok(String.format("Question %s created", question));
    }
    
}
