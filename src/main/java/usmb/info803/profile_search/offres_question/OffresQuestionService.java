package usmb.info803.profile_search.offres_question;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class OffresQuestionService {

    private final OffresQuestionRepository offresQuestionRepository;

    public OffresQuestionService(OffresQuestionRepository offresQuestionRepository) {
        this.offresQuestionRepository = offresQuestionRepository;
    }

    public List<OffresQuestion> getAllOffresQuestions() {
        return offresQuestionRepository.findAll();
    }

    public OffresQuestion getOffresQuestionById(Long id) {
        return offresQuestionRepository.findById(id).orElse(null);
    }

    public OffresQuestion createOffresQuestion(OffresQuestion offresQuestion) {
        return offresQuestionRepository.save(offresQuestion);
    }

    public boolean deleteOffresQuestion(Long id) {
        if (offresQuestionRepository.existsById(id)) {
            offresQuestionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
