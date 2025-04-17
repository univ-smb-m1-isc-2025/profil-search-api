package usmb.info803.profile_search.question_reponse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import usmb.info803.profile_search.candidature.Candidature;
import usmb.info803.profile_search.candidature.CandidatureService;
import usmb.info803.profile_search.question.Question;
import usmb.info803.profile_search.question.QuestionService;

class QuestionReponseServiceTest {

    @Mock
    private QuestionReponseRepository questionReponseRepository;

    @Mock
    private CandidatureService candidatureService;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionReponseService questionReponseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllQuestionReponses() {
        // Préparation des données de test
        Candidature candidature = new Candidature();
        candidature.setId(1L);

        Question question = new Question();
        question.setId(1L);
        question.setQuestion("Question test?");

        QuestionReponse questionReponse1 = new QuestionReponse(candidature, question, "Réponse 1");
        questionReponse1.setId(1L);

        QuestionReponse questionReponse2 = new QuestionReponse(candidature, question, "Réponse 2");
        questionReponse2.setId(2L);

        when(questionReponseRepository.findAll()).thenReturn(Arrays.asList(questionReponse1, questionReponse2));

        // Exécution du test
        List<QuestionReponseDTO> questionReponses = questionReponseService.getAllQuestionReponses();

        // Vérifications
        assertEquals(2, questionReponses.size());
        assertEquals(1L, questionReponses.get(0).getCandidatureId());
        assertEquals(1L, questionReponses.get(0).getQuestionId());
        assertEquals("Question test?", questionReponses.get(0).getQuestionText());
        assertEquals("Réponse 1", questionReponses.get(0).getReponse());
        assertEquals("Réponse 2", questionReponses.get(1).getReponse());
    }

    @Test
    void testGetQuestionReponseById() {
        // Préparation des données de test
        Candidature candidature = new Candidature();
        candidature.setId(1L);

        Question question = new Question();
        question.setId(1L);
        question.setQuestion("Question test?");

        QuestionReponse questionReponse = new QuestionReponse(candidature, question, "Réponse test");
        questionReponse.setId(1L);

        when(questionReponseRepository.findById(1L)).thenReturn(Optional.of(questionReponse));

        // Exécution du test
        QuestionReponseDTO result = questionReponseService.getQuestionReponseById(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getCandidatureId());
        assertEquals(1L, result.getQuestionId());
        assertEquals("Question test?", result.getQuestionText());
        assertEquals("Réponse test", result.getReponse());
    }

    @Test
    void testGetQuestionReponsesByCandidatureId() {
        // Préparation des données de test
        Candidature candidature = new Candidature();
        candidature.setId(1L);

        Question question1 = new Question();
        question1.setId(1L);
        question1.setQuestion("Question 1?");

        Question question2 = new Question();
        question2.setId(2L);
        question2.setQuestion("Question 2?");

        QuestionReponse qr1 = new QuestionReponse(candidature, question1, "Réponse 1");
        qr1.setId(1L);

        QuestionReponse qr2 = new QuestionReponse(candidature, question2, "Réponse 2");
        qr2.setId(2L);

        when(questionReponseRepository.findByCandidatureId(1L)).thenReturn(Arrays.asList(qr1, qr2));

        // Exécution du test
        List<QuestionReponseDTO> results = questionReponseService.getQuestionReponsesByCandidatureId(1L);

        // Vérifications
        assertEquals(2, results.size());
        assertEquals("Question 1?", results.get(0).getQuestionText());
        assertEquals("Réponse 1", results.get(0).getReponse());
        assertEquals("Question 2?", results.get(1).getQuestionText());
        assertEquals("Réponse 2", results.get(1).getReponse());
    }

    @Test
    void testCreateQuestionReponse() {
        // Préparation des données de test
        Candidature candidature = new Candidature();
        candidature.setId(1L);

        Question question = new Question();
        question.setId(1L);
        question.setQuestion("Question test?");

        CreateQuestionReponseBody createBody = new CreateQuestionReponseBody(1L, 1L, "Réponse test");

        QuestionReponse questionReponse = new QuestionReponse(candidature, question, "Réponse test");
        questionReponse.setId(1L);

        when(candidatureService.getCandidatureById(1L)).thenReturn(candidature);
        when(questionService.question(1L)).thenReturn(question);
        when(questionReponseRepository.save(any(QuestionReponse.class))).thenReturn(questionReponse);

        // Exécution du test
        QuestionReponseDTO result = questionReponseService.createQuestionReponse(createBody);

        // Vérifications
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getCandidatureId());
        assertEquals(1L, result.getQuestionId());
        assertEquals("Question test?", result.getQuestionText());
        assertEquals("Réponse test", result.getReponse());
        verify(questionReponseRepository, times(1)).save(any(QuestionReponse.class));
    }

    @Test
    void testUpdateReponse() {
        // Préparation des données de test
        Candidature candidature = new Candidature();
        candidature.setId(1L);

        Question question = new Question();
        question.setId(1L);
        question.setQuestion("Question test?");

        QuestionReponse questionReponse = new QuestionReponse(candidature, question, "Ancienne réponse");
        questionReponse.setId(1L);

        QuestionReponse questionReponseUpdated = new QuestionReponse(candidature, question, "Nouvelle réponse");
        questionReponseUpdated.setId(1L);

        when(questionReponseRepository.findById(1L)).thenReturn(Optional.of(questionReponse));
        when(questionReponseRepository.save(any(QuestionReponse.class))).thenReturn(questionReponseUpdated);

        // Exécution du test
        QuestionReponseDTO result = questionReponseService.updateReponse(1L, "Nouvelle réponse");

        // Vérifications
        assertNotNull(result);
        assertEquals("Nouvelle réponse", result.getReponse());
        verify(questionReponseRepository, times(1)).save(questionReponse);
    }

    @Test
    void testDeleteQuestionReponse() {
        // Préparation des données de test
        when(questionReponseRepository.existsById(1L)).thenReturn(true);

        // Exécution du test
        boolean result = questionReponseService.deleteQuestionReponse(1L);

        // Vérifications
        assertTrue(result);
        verify(questionReponseRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteQuestionReponseNotFound() {
        // Préparation des données de test
        when(questionReponseRepository.existsById(1L)).thenReturn(false);

        // Exécution du test
        boolean result = questionReponseService.deleteQuestionReponse(1L);

        // Vérifications
        assertFalse(result);
        verify(questionReponseRepository, times(0)).deleteById(1L);
    }
}
