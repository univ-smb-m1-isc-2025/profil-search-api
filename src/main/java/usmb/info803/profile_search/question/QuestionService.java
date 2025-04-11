package usmb.info803.profile_search.question;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> all() {
        return questionRepository.findAll();
    }

    public Question question(long id) {
        return questionRepository.findById(id).orElse(null);
    }

    public Question question(String question) {
        return questionRepository.findByQuestion(question).orElse(null);
    }

    public boolean create(String question) {
        Question q = question(question);
        if (q != null) {
            return false;
        }
        questionRepository.save(new Question(question));
        return true;
    }
}
