package usmb.info803.profile_search.bullet_point;

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

@WebMvcTest(BulletPointController.class)
class BulletPointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BulletPointService bulletPointService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllBulletPoints() throws Exception {
        Offre offre = new Offre();
        BulletPoint bp1 = new BulletPoint("Point 1", offre);
        BulletPoint bp2 = new BulletPoint("Point 2", offre);

        when(bulletPointService.getAllBulletPoints()).thenReturn(Arrays.asList(bp1, bp2));

        mockMvc.perform(get("/api/bullet-points/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].bullet_point").value("Point 1")) // Correction ici
                .andExpect(jsonPath("$[1].bullet_point").value("Point 2")); // Correction ici
    }

    @Test
    void testCreateBulletPoint() throws Exception {
        Offre offre = new Offre();
        BulletPoint bulletPoint = new BulletPoint("Point 1", offre);

        when(bulletPointService.createBulletPoint(any(BulletPoint.class))).thenReturn(bulletPoint);

        mockMvc.perform(post("/api/bullet-points/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bulletPoint)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bullet_point").value("Point 1")); // Correction ici
    }
}
