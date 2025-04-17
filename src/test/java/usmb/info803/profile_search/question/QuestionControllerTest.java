package usmb.info803.profile_search.question;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class QuestionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        questionService = mock(QuestionService.class);
        questionController = new QuestionController(questionService);
        mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
    }

    @Test
    public void testGetAllQuestions() throws Exception {
        Question question1 = new Question("Question 1");
        Question question2 = new Question("Question 2");
        question1.setId(1L);
        question2.setId(2L);
        List<Question> questions = List.of(question1, question2);

        when(questionService.all()).thenReturn(questions);

        mockMvc.perform(get("/api/questions/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Question 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Question 2"));

        verify(questionService, times(1)).all();
    }

    @Test
    public void testGetQuestionById() throws Exception {
        Question question = new Question("Question 1");
        question.setId(1L);

        when(questionService.question(1L)).thenReturn(question);

        mockMvc.perform(get("/api/questions/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Question 1"));

        verify(questionService, times(1)).question(1L);
    }

    @Test
    public void testCreateQuestionSuccess() throws Exception {
        String questionName = "New Question";
        when(questionService.create(questionName)).thenReturn(true);

        mockMvc.perform(post("/api/questions/create")
                .param("question", questionName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Question %s created", questionName)));

        verify(questionService, times(1)).create(questionName);
    }

    @Test
    public void testCreateQuestionEmpty() throws Exception {
        mockMvc.perform(post("/api/questions/create")
                .param("question", "")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Question is empty"));

        verify(questionService, never()).create(anyString());
    }

    @Test
    public void testCreateQuestionAlreadyExists() throws Exception {
        String questionName = "Existing Question";
        when(questionService.create(questionName)).thenReturn(false);

        mockMvc.perform(post("/api/questions/create")
                .param("question", questionName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format("Question already exists : %s", questionName)));

        verify(questionService, times(1)).create(questionName);
    }

}
