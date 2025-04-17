package usmb.info803.profile_search.question_reponse;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

class QuestionReponseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private QuestionReponseService questionReponseService;

    @InjectMocks
    private QuestionReponseController questionReponseController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        questionReponseService = mock(QuestionReponseService.class);
        objectMapper = new ObjectMapper();
        questionReponseController = new QuestionReponseController(questionReponseService);
        mockMvc = MockMvcBuilders.standaloneSetup(questionReponseController).build();
    }

    @Test
    void testGetAllQuestionReponses() throws Exception {
        QuestionReponseDTO questionReponse1 = new QuestionReponseDTO(1L, 1L, 1L, "Question 1?", "Réponse 1");
        QuestionReponseDTO questionReponse2 = new QuestionReponseDTO(2L, 1L, 2L, "Question 2?", "Réponse 2");

        when(questionReponseService.getAllQuestionReponses()).thenReturn(Arrays.asList(questionReponse1, questionReponse2));

        mockMvc.perform(get("/api/question-reponses/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].candidature_id").value(1L))
                .andExpect(jsonPath("$[0].question_id").value(1L))
                .andExpect(jsonPath("$[0].question_text").value("Question 1?"))
                .andExpect(jsonPath("$[0].reponse").value("Réponse 1"))
                .andExpect(jsonPath("$[1].reponse").value("Réponse 2"));

        verify(questionReponseService, times(1)).getAllQuestionReponses();
    }

    @Test
    void testGetQuestionReponseById() throws Exception {
        QuestionReponseDTO questionReponse = new QuestionReponseDTO(1L, 1L, 1L, "Question test?", "Réponse test");

        when(questionReponseService.getQuestionReponseById(1L)).thenReturn(questionReponse);

        mockMvc.perform(get("/api/question-reponses/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.candidature_id").value(1L))
                .andExpect(jsonPath("$.question_id").value(1L))
                .andExpect(jsonPath("$.question_text").value("Question test?"))
                .andExpect(jsonPath("$.reponse").value("Réponse test"));

        verify(questionReponseService, times(1)).getQuestionReponseById(1L);
    }

    @Test
    void testGetQuestionReponseByIdNotFound() throws Exception {
        when(questionReponseService.getQuestionReponseById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/question-reponses/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(questionReponseService, times(1)).getQuestionReponseById(1L);
    }

    @Test
    void testGetQuestionReponsesByCandidatureId() throws Exception {
        QuestionReponseDTO qr1 = new QuestionReponseDTO(1L, 1L, 1L, "Question 1?", "Réponse 1");
        QuestionReponseDTO qr2 = new QuestionReponseDTO(2L, 1L, 2L, "Question 2?", "Réponse 2");

        when(questionReponseService.getQuestionReponsesByCandidatureId(1L)).thenReturn(Arrays.asList(qr1, qr2));

        mockMvc.perform(get("/api/question-reponses/candidature/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].question_text").value("Question 1?"))
                .andExpect(jsonPath("$[1].question_text").value("Question 2?"));

        verify(questionReponseService, times(1)).getQuestionReponsesByCandidatureId(1L);
    }

    @Test
    void testGetQuestionReponsesByCandidatureIdNoContent() throws Exception {
        when(questionReponseService.getQuestionReponsesByCandidatureId(1L)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/question-reponses/candidature/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(questionReponseService, times(1)).getQuestionReponsesByCandidatureId(1L);
    }

    @Test
    void testCreateQuestionReponse() throws Exception {
        CreateQuestionReponseBody createBody = new CreateQuestionReponseBody(1L, 1L, "Réponse test");
        QuestionReponseDTO createdQuestionReponse = new QuestionReponseDTO(1L, 1L, 1L, "Question test?", "Réponse test");

        when(questionReponseService.createQuestionReponse(any(CreateQuestionReponseBody.class))).thenReturn(createdQuestionReponse);

        mockMvc.perform(post("/api/question-reponses/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.candidature_id").value(1L))
                .andExpect(jsonPath("$.question_id").value(1L))
                .andExpect(jsonPath("$.question_text").value("Question test?"))
                .andExpect(jsonPath("$.reponse").value("Réponse test"));

        verify(questionReponseService, times(1)).createQuestionReponse(any(CreateQuestionReponseBody.class));
    }

    @Test
    void testCreateQuestionReponseBadRequest() throws Exception {
        CreateQuestionReponseBody createBody = new CreateQuestionReponseBody(1L, 1L, "Réponse test");

        when(questionReponseService.createQuestionReponse(any(CreateQuestionReponseBody.class))).thenReturn(null);

        mockMvc.perform(post("/api/question-reponses/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBody)))
                .andExpect(status().isBadRequest());

        verify(questionReponseService, times(1)).createQuestionReponse(any(CreateQuestionReponseBody.class));
    }

    @Test
    void testUpdateReponse() throws Exception {
        QuestionReponseDTO updatedQuestionReponse = new QuestionReponseDTO(1L, 1L, 1L, "Question test?", "Nouvelle réponse");

        when(questionReponseService.updateReponse(anyLong(), anyString())).thenReturn(updatedQuestionReponse);

        mockMvc.perform(put("/api/question-reponses/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("Nouvelle réponse"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reponse").value("Nouvelle réponse"));

        verify(questionReponseService, times(1)).updateReponse(1L, "Nouvelle réponse");
    }

    @Test
    void testDeleteQuestionReponse() throws Exception {
        when(questionReponseService.deleteQuestionReponse(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/question-reponses/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Question-reponse 1 deleted"));

        verify(questionReponseService, times(1)).deleteQuestionReponse(1L);
    }

    @Test
    void testDeleteQuestionReponseNotFound() throws Exception {
        when(questionReponseService.deleteQuestionReponse(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/question-reponses/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Question-reponse 1 not found"));

        verify(questionReponseService, times(1)).deleteQuestionReponse(1L);
    }
}
