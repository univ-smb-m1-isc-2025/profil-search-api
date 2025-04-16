package usmb.info803.profile_search.offre;

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

import usmb.info803.profile_search.member.Member;

class OffreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OffreService offreService;

    @InjectMocks
    private OffreController offreController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        offreService = mock(OffreService.class);
        objectMapper = new ObjectMapper();
        offreController = new OffreController(offreService);
        mockMvc = MockMvcBuilders.standaloneSetup(offreController).build();
    }

    @Test
    void testGetAllOffres() throws Exception {
        Offre offre1 = new Offre("Offre 1", new Member(), false);
        Offre offre2 = new Offre("Offre 2", new Member(), false);

        when(offreService.getAllOffres()).thenReturn(Arrays.asList(offre1, offre2));

        mockMvc.perform(get("/api/offres/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titre").value("Offre 1"))
                .andExpect(jsonPath("$[1].titre").value("Offre 2"));

        verify(offreService, times(1)).getAllOffres();
    }

    @Test
    void testGetOffreById() throws Exception {
        Offre offre = new Offre("Offre 1", new Member(), false);

        when(offreService.getOffreById(1L)).thenReturn(offre);

        mockMvc.perform(get("/api/offres/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titre").value("Offre 1"));

        verify(offreService, times(1)).getOffreById(1L);
    }

    @Test
    void testGetOffreById_NotFound() throws Exception {
        when(offreService.getOffreById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/offres/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(offreService, times(1)).getOffreById(1L);
    }

    @Test
    void testCreateOffre() throws Exception {
        OffreCreationDTO offreDTO = new OffreCreationDTO("Offre 1", 1L, false);
        Offre createdOffre = new Offre("Offre 1", new Member(), false);

        when(offreService.createOffreFromDTO(any(OffreCreationDTO.class))).thenReturn(createdOffre);

        mockMvc.perform(post("/api/offres/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(offreDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titre").value("Offre 1"));

        verify(offreService, times(1)).createOffreFromDTO(any(OffreCreationDTO.class));
    }

    @Test
    void testCreateOffre_BadRequest() throws Exception {
        OffreCreationDTO offreDTO = new OffreCreationDTO("Offre 1", 1L, false);

        when(offreService.createOffreFromDTO(any(OffreCreationDTO.class))).thenReturn(null);

        mockMvc.perform(post("/api/offres/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(offreDTO)))
                .andExpect(status().isBadRequest());

        verify(offreService, times(1)).createOffreFromDTO(any(OffreCreationDTO.class));
    }

    @Test
    void testDeleteOffre() throws Exception {
        when(offreService.deleteOffre(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/offres/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Offre 1 deleted"));

        verify(offreService, times(1)).deleteOffre(1L);
    }

    @Test
    void testDeleteOffre_NotFound() throws Exception {
        when(offreService.deleteOffre(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/offres/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Offre 1 not found"));

        verify(offreService, times(1)).deleteOffre(1L);
    }

}
