package usmb.info803.profile_search.paragraphe;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

import usmb.info803.profile_search.offre.Offre;

@WebMvcTest(ParagrapheController.class)
class ParagrapheControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParagrapheService paragrapheService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllParagraphes() throws Exception {
        Offre offre = new Offre();
        Paragraphe p1 = new Paragraphe("Contenu 1", offre);
        Paragraphe p2 = new Paragraphe("Contenu 2", offre);

        when(paragrapheService.getAllParagraphes()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/paragraphes/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].contenu").value("Contenu 1"))
                .andExpect(jsonPath("$[1].contenu").value("Contenu 2"));
    }

    @Test
    void testGetParagrapheById() throws Exception {
        Offre offre = new Offre();
        Paragraphe paragraphe = new Paragraphe("Contenu 1", offre);

        when(paragrapheService.getParagrapheById(1L)).thenReturn(paragraphe);

        mockMvc.perform(get("/api/paragraphes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contenu").value("Contenu 1"));
    }

    @Test
    void testCreateParagraphe() throws Exception {
        Offre offre = new Offre();
        Paragraphe paragraphe = new Paragraphe("Contenu 1", offre);
        when(paragrapheService.createParagraphe(any(Paragraphe.class))).thenReturn(paragraphe);
        mockMvc.perform(post("/api/paragraphes/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paragraphe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contenu").value("Contenu 1"));
    }

    @Test
    void testGetParagrapheById_NotFound() throws Exception {
        when(paragrapheService.getParagrapheById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/paragraphes/1"))
                .andExpect(status().isNotFound());
    }
}
