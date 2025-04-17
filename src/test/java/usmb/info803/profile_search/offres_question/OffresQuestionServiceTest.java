package usmb.info803.profile_search.offres_question;

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

import usmb.info803.profile_search.offre.Offre;
import usmb.info803.profile_search.offre.OffreService;
import usmb.info803.profile_search.question.Question;
import usmb.info803.profile_search.question.QuestionService;

class OffresQuestionServiceTest {

    @Mock
    private OffresQuestionRepository offresQuestionRepository;

    @Mock
    private OffreService offreService;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private OffresQuestionService offresQuestionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOffresQuestions() {
        // Préparation des données de test
        Offre offre = new Offre();
        offre.setId(1L);

        Question question = new Question();
        question.setId(1L);

        OffresQuestion offresQuestion1 = new OffresQuestion(offre, question);
        offresQuestion1.setId(1L);

        OffresQuestion offresQuestion2 = new OffresQuestion(offre, question);
        offresQuestion2.setId(2L);

        when(offresQuestionRepository.findAll()).thenReturn(Arrays.asList(offresQuestion1, offresQuestion2));

        // Exécution du test
        List<OffresQuestionDTO> offresQuestions = offresQuestionService.getAllOffresQuestions();

        // Vérifications
        assertEquals(2, offresQuestions.size());
        assertEquals(1L, offresQuestions.get(0).getOffreId());
        assertEquals(1L, offresQuestions.get(0).getQuestionId());
        assertEquals(1L, offresQuestions.get(1).getOffreId());
        assertEquals(1L, offresQuestions.get(1).getQuestionId());
    }

    @Test
    void testGetOffresQuestionById() {
        // Préparation des données de test
        Offre offre = new Offre();
        offre.setId(1L);

        Question question = new Question();
        question.setId(1L);

        OffresQuestion offresQuestion = new OffresQuestion(offre, question);
        offresQuestion.setId(1L);

        when(offresQuestionRepository.findById(1L)).thenReturn(Optional.of(offresQuestion));

        // Exécution du test
        OffresQuestionDTO result = offresQuestionService.getOffresQuestionById(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getOffreId());
        assertEquals(1L, result.getQuestionId());
    }

    @Test
    void testCreateOffresQuestion() {
        // Préparation des données de test
        Offre offre = new Offre();
        offre.setId(1L);

        Question question = new Question();
        question.setId(1L);

        CreateOffresQuestionBody createBody = new CreateOffresQuestionBody(1L, 1L);

        OffresQuestion offresQuestion = new OffresQuestion(offre, question);
        offresQuestion.setId(1L);

        when(offreService.getOffreById(1L)).thenReturn(offre);
        when(questionService.question(1L)).thenReturn(question);
        when(offresQuestionRepository.save(any(OffresQuestion.class))).thenReturn(offresQuestion);

        // Exécution du test
        OffresQuestionDTO result = offresQuestionService.createOffresQuestion(createBody);

        // Vérifications
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getOffreId());
        assertEquals(1L, result.getQuestionId());
        verify(offresQuestionRepository, times(1)).save(any(OffresQuestion.class));
    }

    @Test
    void testDeleteOffresQuestion() {
        // Préparation des données de test
        when(offresQuestionRepository.existsById(1L)).thenReturn(true);

        // Exécution du test
        boolean result = offresQuestionService.deleteOffresQuestion(1L);

        // Vérifications
        assertTrue(result);
        verify(offresQuestionRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteOffresQuestionNotFound() {
        // Préparation des données de test
        when(offresQuestionRepository.existsById(1L)).thenReturn(false);

        // Exécution du test
        boolean result = offresQuestionService.deleteOffresQuestion(1L);

        // Vérifications
        assertFalse(result);
        verify(offresQuestionRepository, times(0)).deleteById(1L);
    }
}
