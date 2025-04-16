package usmb.info803.profile_search.offres_question;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import usmb.info803.profile_search.offre.Offre;
import usmb.info803.profile_search.question.Question;

@WebMvcTest(OffresQuestionController.class)
class OffresQuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OffresQuestionService offresQuestionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllOffresQuestions() throws Exception {
        Offre offre = new Offre();
        Question question = new Question();
        OffresQuestion oq1 = new OffresQuestion(offre, question);
        OffresQuestion oq2 = new OffresQuestion(offre, question);

        when(offresQuestionService.getAllOffresQuestions()).thenReturn(Arrays.asList(oq1, oq2));

        mockMvc.perform(get("/api/offres-questions/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testCreateOffresQuestion() throws Exception {
        Offre offre = new Offre();
        Question question = new Question();
        OffresQuestion offresQuestion = new OffresQuestion(offre, question);

        when(offresQuestionService.createOffresQuestion(any(OffresQuestion.class))).thenReturn(offresQuestion);

        mockMvc.perform(post("/api/offres-questions/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(offresQuestion)))
                .andExpect(status().isOk());
    }
}
