package usmb.info803.profile_search.offre;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import com.fasterxml.jackson.databind.ObjectMapper;

import usmb.info803.profile_search.member.Member;

@WebMvcTest(OffreController.class)
class OffreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OffreService offreService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllOffres() throws Exception {
        Member user = new Member();
        Offre offre1 = new Offre("Offre 1", user, true);
        Offre offre2 = new Offre("Offre 2", user, false);

        when(offreService.getAllOffres()).thenReturn(Arrays.asList(offre1, offre2));

        mockMvc.perform(get("/api/offres/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].titre").value("Offre 1"))
                .andExpect(jsonPath("$[1].titre").value("Offre 2"));
    }

    @Test
    void testCreateOffre() throws Exception {
        Member user = new Member();
        Offre offre = new Offre("Offre 1", user, true);

        when(offreService.createOffre(any(Offre.class))).thenReturn(offre);

        mockMvc.perform(post("/api/offres/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(offre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titre").value("Offre 1"));
    }
}
