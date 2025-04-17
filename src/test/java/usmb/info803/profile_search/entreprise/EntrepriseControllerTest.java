package usmb.info803.profile_search.entreprise;

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

import com.fasterxml.jackson.databind.ObjectMapper;

public class EntrepriseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EntrepriseService entrepriseService;

    @InjectMocks
    private EntrepriseController entrepriseController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        entrepriseService = mock(EntrepriseService.class);
        entrepriseController = new EntrepriseController(entrepriseService);
        mockMvc = MockMvcBuilders.standaloneSetup(entrepriseController).build();
    }

    @Test
    public void testGetAllEntreprises() throws Exception {
        Entreprise entreprise1 = new Entreprise("Test1");
        Entreprise entreprise2 = new Entreprise("Test2");
        entreprise1.setId(1L);
        entreprise2.setId(2L);
        when(entrepriseService.entreprises()).thenReturn(List.of(entreprise1, entreprise2));

        mockMvc.perform(get("/api/entreprises/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Test2"));
    }

    @Test
    public void testGetEntrepriseById() throws Exception {
        Entreprise entreprise = new Entreprise("Test1");
        entreprise.setId(1L);
        when(entrepriseService.entreprise(1L)).thenReturn(entreprise);

        mockMvc.perform(get("/api/entreprises/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test1"));
    }

    @Test
    public void testDeleteEntreprise() throws Exception {
        when(entrepriseService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/entreprises/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Entreprise 1 deleted"));
    }

    @Test
    public void testDeleteEntrepriseNotFound() throws Exception {
        when(entrepriseService.delete(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/entreprises/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Entreprise 1 not found"));
    }

    @Test
    public void testCreateEntreprise() throws Exception {
        CreateEntrepriseBody request = new CreateEntrepriseBody("Test1");
        when(entrepriseService.create("Test1")).thenReturn(true);

        mockMvc.perform(post("/api/entreprises/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Entreprise Test1 created"));
    }

    @Test
    public void testCreateEntrepriseAlreadyExists() throws Exception {
        CreateEntrepriseBody request = new CreateEntrepriseBody("Test1");
        when(entrepriseService.create("Test1")).thenReturn(false);

        mockMvc.perform(post("/api/entreprises/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Entreprise Test1 already exists"));
    }

    @Test
    public void testCreateEntrepriseEmptyName() throws Exception {
        CreateEntrepriseBody request = new CreateEntrepriseBody("");

        mockMvc.perform(post("/api/entreprises/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Name is empty"));
    }
}
