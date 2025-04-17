package usmb.info803.profile_search.offres_question;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import usmb.info803.profile_search.offre.Offre;
import usmb.info803.profile_search.offre.OffreService;
import usmb.info803.profile_search.question.Question;
import usmb.info803.profile_search.question.QuestionService;

@Service
public class OffresQuestionService {

    private final OffresQuestionRepository offresQuestionRepository;
    private final OffreService offreService;
    private final QuestionService questionService;

    public OffresQuestionService(
            OffresQuestionRepository offresQuestionRepository,
            OffreService offreService,
            QuestionService questionService) {
        this.offresQuestionRepository = offresQuestionRepository;
        this.offreService = offreService;
        this.questionService = questionService;
    }

    public List<OffresQuestionDTO> getAllOffresQuestions() {
        return offresQuestionRepository.findAll()
                .stream()
                .map(OffresQuestionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public OffresQuestionDTO getOffresQuestionById(Long id) {
        return offresQuestionRepository.findById(id)
                .map(OffresQuestionDTO::fromEntity)
                .orElse(null);
    }

    @Transactional
    public OffresQuestionDTO createOffresQuestion(CreateOffresQuestionBody createBody) {
        if (!createBody.isValid()) {
            return null;
        }

        Offre offre = offreService.getOffreById(createBody.getOffreId());
        if (offre == null) {
            return null;
        }

        Question question = questionService.question(createBody.getQuestionId());
        if (question == null) {
            return null;
        }

        OffresQuestion offresQuestion = new OffresQuestion(offre, question);
        OffresQuestion savedOffresQuestion = offresQuestionRepository.save(offresQuestion);

        return OffresQuestionDTO.fromEntity(savedOffresQuestion);
    }

    @Transactional
    public boolean deleteOffresQuestion(Long id) {
        if (offresQuestionRepository.existsById(id)) {
            offresQuestionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
