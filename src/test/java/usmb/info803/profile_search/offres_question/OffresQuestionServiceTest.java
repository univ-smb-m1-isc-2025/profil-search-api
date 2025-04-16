package usmb.info803.profile_search.offres_question;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import usmb.info803.profile_search.offre.Offre;
import usmb.info803.profile_search.question.Question;

class OffresQuestionServiceTest {

    @Mock
    private OffresQuestionRepository offresQuestionRepository;

    @InjectMocks
    private OffresQuestionService offresQuestionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOffresQuestions() {
        Offre offre = new Offre();
        Question question = new Question();
        OffresQuestion oq1 = new OffresQuestion(offre, question);
        OffresQuestion oq2 = new OffresQuestion(offre, question);

        when(offresQuestionRepository.findAll()).thenReturn(Arrays.asList(oq1, oq2));

        List<OffresQuestion> offresQuestions = offresQuestionService.getAllOffresQuestions();

        assertEquals(2, offresQuestions.size());
    }

    @Test
    void testGetOffresQuestionById() {
        Offre offre = new Offre();
        Question question = new Question();
        OffresQuestion offresQuestion = new OffresQuestion(offre, question);

        when(offresQuestionRepository.findById(1L)).thenReturn(Optional.of(offresQuestion));

        OffresQuestion result = offresQuestionService.getOffresQuestionById(1L);

        assertNotNull(result);
    }

    @Test
    void testCreateOffresQuestion() {
        Offre offre = new Offre();
        Question question = new Question();
        OffresQuestion offresQuestion = new OffresQuestion(offre, question);

        when(offresQuestionRepository.save(offresQuestion)).thenReturn(offresQuestion);

        OffresQuestion result = offresQuestionService.createOffresQuestion(offresQuestion);

        assertNotNull(result);
        verify(offresQuestionRepository, times(1)).save(offresQuestion);
    }

    @Test
    void testDeleteOffresQuestion() {
        when(offresQuestionRepository.existsById(1L)).thenReturn(true);

        boolean result = offresQuestionService.deleteOffresQuestion(1L);

        assertTrue(result);
        verify(offresQuestionRepository, times(1)).deleteById(1L);
    }
}
