package usmb.info803.profile_search.question_reponse;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import usmb.info803.profile_search.candidature.Candidature;
import usmb.info803.profile_search.candidature.CandidatureService;
import usmb.info803.profile_search.question.Question;
import usmb.info803.profile_search.question.QuestionService;

@Service
public class QuestionReponseService {

    private final QuestionReponseRepository questionReponseRepository;
    private final CandidatureService candidatureService;
    private final QuestionService questionService;

    public QuestionReponseService(
            QuestionReponseRepository questionReponseRepository,
            CandidatureService candidatureService,
            QuestionService questionService) {
        this.questionReponseRepository = questionReponseRepository;
        this.candidatureService = candidatureService;
        this.questionService = questionService;
    }

    public List<QuestionReponseDTO> getAllQuestionReponses() {
        return questionReponseRepository.findAll()
                .stream()
                .map(QuestionReponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public QuestionReponseDTO getQuestionReponseById(Long id) {
        return questionReponseRepository.findById(id)
                .map(QuestionReponseDTO::fromEntity)
                .orElse(null);
    }

    public List<QuestionReponseDTO> getQuestionReponsesByCandidatureId(Long candidatureId) {
        return questionReponseRepository.findByCandidatureId(candidatureId)
                .stream()
                .map(QuestionReponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<QuestionReponseDTO> getQuestionReponsesByQuestionId(Long questionId) {
        return questionReponseRepository.findByQuestionId(questionId)
                .stream()
                .map(QuestionReponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public QuestionReponseDTO createQuestionReponse(CreateQuestionReponseBody createBody) {
        if (!createBody.isValid()) {
            return null;
        }

        Candidature candidature = candidatureService.getCandidatureById(createBody.getCandidatureId());
        if (candidature == null) {
            return null;
        }

        Question question = questionService.question(createBody.getQuestionId());
        if (question == null) {
            return null;
        }

        QuestionReponse questionReponse = new QuestionReponse(candidature, question, createBody.getReponse());
        QuestionReponse savedQuestionReponse = questionReponseRepository.save(questionReponse);

        return QuestionReponseDTO.fromEntity(savedQuestionReponse);
    }

    @Transactional
    public boolean deleteQuestionReponse(Long id) {
        if (questionReponseRepository.existsById(id)) {
            questionReponseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public QuestionReponseDTO updateReponse(Long id, String nouvelleReponse) {
        if (nouvelleReponse == null || nouvelleReponse.isEmpty()) {
            return null;
        }

        return questionReponseRepository.findById(id)
                .map(qr -> {
                    qr.setReponse(nouvelleReponse);
                    return QuestionReponseDTO.fromEntity(questionReponseRepository.save(qr));
                })
                .orElse(null);
    }
}
