package usmb.info803.profile_search.paragraphe;

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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import usmb.info803.profile_search.offre.Offre;

class ParagrapheControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParagrapheService paragrapheService;

    @InjectMocks
    private ParagrapheController paragrapheController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        paragrapheService = mock(ParagrapheService.class);
        objectMapper = new ObjectMapper();
        paragrapheController = new ParagrapheController(paragrapheService);
        mockMvc = MockMvcBuilders.standaloneSetup(paragrapheController).build();
    }

    @Test
    void testGetAllParagraphes() throws Exception {
        Paragraphe paragraphe1 = new Paragraphe("Content 1", new Offre());
        Paragraphe paragraphe2 = new Paragraphe("Content 2", new Offre());

        when(paragrapheService.getAllParagraphes()).thenReturn(Arrays.asList(paragraphe1, paragraphe2));

        mockMvc.perform(get("/api/paragraphes/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].contenu").value("Content 1"))
                .andExpect(jsonPath("$[1].contenu").value("Content 2"));

        verify(paragrapheService, times(1)).getAllParagraphes();
    }

    @Test
    void testGetParagrapheById() throws Exception {
        Paragraphe paragraphe = new Paragraphe("Content 1", new Offre());
        paragraphe.setId(1L);
        when(paragrapheService.getParagrapheById(1L)).thenReturn(paragraphe);

        mockMvc.perform(get("/api/paragraphes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.contenu").value("Content 1"));

        verify(paragrapheService, times(1)).getParagrapheById(1L);
    }

    @Test
    void testGetParagrapheByIdNotFound() throws Exception {
        when(paragrapheService.getParagrapheById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/paragraphes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(paragrapheService, times(1)).getParagrapheById(1L);
    }

    @Test
    void testCreateParagraphe() throws Exception {
        Offre offre = new Offre();
        offre.setId(1L);
        ParagrapheCreationDTO dto = new ParagrapheCreationDTO("New Content", 1L);
        Paragraphe createdParagraphe = new Paragraphe("New Content", offre);
        createdParagraphe.setId(1L);
        when(paragrapheService.createParagrapheFromDTO(any(ParagrapheCreationDTO.class))).thenReturn(createdParagraphe);

        mockMvc.perform(post("/api/paragraphes/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.contenu").value("New Content"));

        verify(paragrapheService, times(1)).createParagrapheFromDTO(any(ParagrapheCreationDTO.class));
    }

    @Test
    void testCreateParagrapheBadRequest() throws Exception {
        Offre offre = new Offre();
        offre.setId(1L);
        ParagrapheCreationDTO dto = new ParagrapheCreationDTO("New Content", 1L);
        when(paragrapheService.createParagrapheFromDTO(any(ParagrapheCreationDTO.class))).thenReturn(null);

        mockMvc.perform(post("/api/paragraphes/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verify(paragrapheService, times(1)).createParagrapheFromDTO(any(ParagrapheCreationDTO.class));
    }

    @Test
    void testDeleteParagraphe() throws Exception {
        when(paragrapheService.deleteParagraphe(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/paragraphes/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Paragraphe 1 deleted"));

        verify(paragrapheService, times(1)).deleteParagraphe(1L);
    }

    @Test
    void testDeleteParagrapheNotFound() throws Exception {
        when(paragrapheService.deleteParagraphe(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/paragraphes/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Paragraphe 1 not found"));

        verify(paragrapheService, times(1)).deleteParagraphe(1L);
    }

}
