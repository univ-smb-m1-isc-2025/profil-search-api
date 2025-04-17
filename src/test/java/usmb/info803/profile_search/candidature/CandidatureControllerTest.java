package usmb.info803.profile_search.candidature;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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

import usmb.info803.profile_search.logDelCandidature.LogDelCandidatureService;
import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.member.MemberService;
import usmb.info803.profile_search.offre.Offre;
import usmb.info803.profile_search.offre.OffreService;
import usmb.info803.profile_search.tag_candidature.TagCandidatureService;

public class CandidatureControllerTest {

    private MockMvc mockMvc;
    
    @Mock
    private CandidatureService candidatureService;

    @Mock
    private OffreService offreService;

    @Mock
    private MemberService memberService;

    @Mock
    private TagCandidatureService tagCandidatureService;

    @Mock
    private LogDelCandidatureService logDelCandidatureService;

    @InjectMocks
    private CandidatureController candidatureController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        candidatureService = mock(CandidatureService.class);
        offreService = mock(OffreService.class);
        memberService = mock(MemberService.class);
        tagCandidatureService = mock(TagCandidatureService.class);
        logDelCandidatureService = mock(LogDelCandidatureService.class);
        candidatureController = new CandidatureController(candidatureService, offreService, memberService, tagCandidatureService, logDelCandidatureService);
        mockMvc = MockMvcBuilders.standaloneSetup(candidatureController).build();
    }

    @Test
    public void testGetAllCandidatures() throws Exception {
        List<CandidatureDTO> mockCandidatures = List.of(
            new CandidatureDTO(1L, "test@example.com", "Test Name", 1L, null, false, false, null),
            new CandidatureDTO(2L, "another@example.com", "Another Name", 2L, null, true, true, null)
        );

        when(candidatureService.getAllCandidatures())
            .thenReturn(mockCandidatures.stream().map(dto -> new Candidature(dto.getEmailCandidat(), dto.getName(), new Offre())).toList());

        mockMvc.perform(get("/api/candidatures/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(mockCandidatures.size()))
                .andExpect(jsonPath("$[0].emailCandidat").value("test@example.com"))
                .andExpect(jsonPath("$[1].emailCandidat").value("another@example.com"));
    }

    @Test
    public void testGetCandidatureById() throws Exception {
        Candidature mockCandidature = new Candidature("test@example.com", "Test Name", new Offre());
        mockCandidature.setId(1L);
        when(candidatureService.getCandidatureById(1L)).thenReturn(mockCandidature);

        mockMvc.perform(get("/api/candidatures/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testCreateCandidature() throws Exception {
        CandidatureDTO candidatureDTO = new CandidatureDTO(1L, "test@example.com", "Test Name", 1L, null, false, false, null);
        Offre mockOffre = new Offre();
        when(offreService.getOffreById(1L)).thenReturn(mockOffre);
        when(candidatureService.createCandidature("test@example.com", "Test Name", mockOffre))
                .thenReturn(new Candidature("test@example.com", "Test Name", mockOffre)); // Return a Candidature object directly

        mockMvc.perform(post("/api/candidatures/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(candidatureDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailCandidat").value("test@example.com"));
    }

    @Test
    public void testDeleteCandidature() throws Exception {
        Candidature mockCandidature = new Candidature("test@example.com", "Test Name", new Offre());
        mockCandidature.setId(1L);
        mockCandidature.setDeleteToken("validToken");
        
        DeleteCandidatureBody deleteBody = new DeleteCandidatureBody("test@example.com", "oui");

        when(candidatureService.deleteByDeleteToken("validToken") ).thenReturn(mockCandidature);

        mockMvc.perform(delete("/api/candidatures/delete/validToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("Candidature deleted successfully"));
    }

    @Test
    public void testAssignCandidature() throws Exception {
        Candidature mockCandidature = new Candidature("test@example.com", "Test Name", new Offre());
        mockCandidature.setId(1L);
        Member mockMember = new Member();
        when(candidatureService.getCandidatureById(1L)).thenReturn(mockCandidature);
        when(memberService.memberById(2L)).thenReturn(mockMember);
        when(candidatureService.assign(1L, mockMember)).thenReturn(mockCandidature);

        mockMvc.perform(post("/api/candidatures/assign/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
