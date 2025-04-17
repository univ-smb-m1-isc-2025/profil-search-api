package usmb.info803.profile_search.bullet_point;

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

class BulletPointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private BulletPointService bulletPointService;

    @InjectMocks
    private BulletPointController bulletPointController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        bulletPointService = mock(BulletPointService.class);
        objectMapper = new ObjectMapper();
        bulletPointController = new BulletPointController(bulletPointService);
        mockMvc = MockMvcBuilders.standaloneSetup(bulletPointController).build();
    }

    @Test
    void testGetAllBulletPoints() throws Exception {
        BulletPointDTO bulletPoint1 = new BulletPointDTO(1L, "Content 1", 1L);
        BulletPointDTO bulletPoint2 = new BulletPointDTO(2L, "Content 2", 1L);

        when(bulletPointService.getAllBulletPoints()).thenReturn(Arrays.asList(bulletPoint1, bulletPoint2));

        mockMvc.perform(get("/api/bullet-points/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bullet_point").value("Content 1"))
                .andExpect(jsonPath("$[1].bullet_point").value("Content 2"));

        verify(bulletPointService, times(1)).getAllBulletPoints();
    }

    @Test
    void testGetBulletPointById() throws Exception {
        BulletPointDTO bulletPoint = new BulletPointDTO(1L, "Content 1", 1L);

        when(bulletPointService.getBulletPointById(1L)).thenReturn(bulletPoint);

        mockMvc.perform(get("/api/bullet-points/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.bullet_point").value("Content 1"));

        verify(bulletPointService, times(1)).getBulletPointById(1L);
    }

    @Test
    void testGetBulletPointByIdNotFound() throws Exception {
        when(bulletPointService.getBulletPointById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/bullet-points/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bulletPointService, times(1)).getBulletPointById(1L);
    }

    @Test
    void testCreateBulletPoint() throws Exception {
        CreateBulletPointBody createBody = new CreateBulletPointBody("New Content", 1L);
        BulletPointDTO createdBulletPoint = new BulletPointDTO(1L, "New Content", 1L);

        when(bulletPointService.createBulletPoint(any(CreateBulletPointBody.class))).thenReturn(createdBulletPoint);

        mockMvc.perform(post("/api/bullet-points/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.bullet_point").value("New Content"));

        verify(bulletPointService, times(1)).createBulletPoint(any(CreateBulletPointBody.class));
    }

    @Test
    void testCreateBulletPointBadRequest() throws Exception {
        CreateBulletPointBody createBody = new CreateBulletPointBody("New Content", 1L);

        when(bulletPointService.createBulletPoint(any(CreateBulletPointBody.class))).thenReturn(null);

        mockMvc.perform(post("/api/bullet-points/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBody)))
                .andExpect(status().isBadRequest());

        verify(bulletPointService, times(1)).createBulletPoint(any(CreateBulletPointBody.class));
    }

    @Test
    void testDeleteBulletPoint() throws Exception {
        when(bulletPointService.deleteBulletPoint(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/bullet-points/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Bullet point 1 deleted"));

        verify(bulletPointService, times(1)).deleteBulletPoint(1L);
    }

    @Test
    void testDeleteBulletPointNotFound() throws Exception {
        when(bulletPointService.deleteBulletPoint(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/bullet-points/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Bullet point 1 not found"));

        verify(bulletPointService, times(1)).deleteBulletPoint(1L);
    }
}
