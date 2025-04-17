package usmb.info803.profile_search.offres_question;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

class OffresQuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private OffresQuestionService offresQuestionService;

    @InjectMocks
    private OffresQuestionController offresQuestionController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        offresQuestionService = mock(OffresQuestionService.class);
        objectMapper = new ObjectMapper();
        offresQuestionController = new OffresQuestionController(offresQuestionService);
        mockMvc = MockMvcBuilders.standaloneSetup(offresQuestionController).build();
    }

    @Test
    void testGetAllOffresQuestions() throws Exception {
        OffresQuestionDTO offresQuestion1 = new OffresQuestionDTO(1L, 1L, 1L);
        OffresQuestionDTO offresQuestion2 = new OffresQuestionDTO(2L, 1L, 2L);

        when(offresQuestionService.getAllOffresQuestions()).thenReturn(Arrays.asList(offresQuestion1, offresQuestion2));

        mockMvc.perform(get("/api/offres-questions/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].offre_id").value(1L))
                .andExpect(jsonPath("$[0].question_id").value(1L))
                .andExpect(jsonPath("$[1].offre_id").value(1L))
                .andExpect(jsonPath("$[1].question_id").value(2L));

        verify(offresQuestionService, times(1)).getAllOffresQuestions();
    }

    @Test
    void testGetOffresQuestionById() throws Exception {
        OffresQuestionDTO offresQuestion = new OffresQuestionDTO(1L, 1L, 1L);

        when(offresQuestionService.getOffresQuestionById(1L)).thenReturn(offresQuestion);

        mockMvc.perform(get("/api/offres-questions/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.offre_id").value(1L))
                .andExpect(jsonPath("$.question_id").value(1L));

        verify(offresQuestionService, times(1)).getOffresQuestionById(1L);
    }

    @Test
    void testGetOffresQuestionByIdNotFound() throws Exception {
        when(offresQuestionService.getOffresQuestionById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/offres-questions/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(offresQuestionService, times(1)).getOffresQuestionById(1L);
    }

    @Test
    void testCreateOffresQuestion() throws Exception {
        CreateOffresQuestionBody createBody = new CreateOffresQuestionBody(1L, 1L);
        OffresQuestionDTO createdOffresQuestion = new OffresQuestionDTO(1L, 1L, 1L);

        when(offresQuestionService.createOffresQuestion(any(CreateOffresQuestionBody.class))).thenReturn(createdOffresQuestion);

        mockMvc.perform(post("/api/offres-questions/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.offre_id").value(1L))
                .andExpect(jsonPath("$.question_id").value(1L));

        verify(offresQuestionService, times(1)).createOffresQuestion(any(CreateOffresQuestionBody.class));
    }

    @Test
    void testCreateOffresQuestionBadRequest() throws Exception {
        CreateOffresQuestionBody createBody = new CreateOffresQuestionBody(1L, 1L);

        when(offresQuestionService.createOffresQuestion(any(CreateOffresQuestionBody.class))).thenReturn(null);

        mockMvc.perform(post("/api/offres-questions/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBody)))
                .andExpect(status().isBadRequest());

        verify(offresQuestionService, times(1)).createOffresQuestion(any(CreateOffresQuestionBody.class));
    }

    @Test
    void testDeleteOffresQuestion() throws Exception {
        when(offresQuestionService.deleteOffresQuestion(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/offres-questions/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Offres-question 1 deleted"));

        verify(offresQuestionService, times(1)).deleteOffresQuestion(1L);
    }

    @Test
    void testDeleteOffresQuestionNotFound() throws Exception {
        when(offresQuestionService.deleteOffresQuestion(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/offres-questions/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Offres-question 1 not found"));

        verify(offresQuestionService, times(1)).deleteOffresQuestion(1L);
    }
}
