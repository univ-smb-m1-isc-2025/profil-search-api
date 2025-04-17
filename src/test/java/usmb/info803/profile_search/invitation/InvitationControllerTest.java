package usmb.info803.profile_search.invitation;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import usmb.info803.profile_search.entreprise.Entreprise;
import usmb.info803.profile_search.entreprise.EntrepriseService;

public class InvitationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InvitationService invitationService;

    @Mock
    private EntrepriseService entrepriseService;

    @InjectMocks
    private InvitationController invitationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        invitationService = mock(InvitationService.class);
        entrepriseService = mock(EntrepriseService.class);
        invitationController = new InvitationController(invitationService, entrepriseService);
        mockMvc = MockMvcBuilders.standaloneSetup(invitationController).build();
    }

    @Test
    public void testVerifyInvitation_ValidToken() throws Exception {
        String token = "validToken";
        Entreprise entreprise = new Entreprise();
        when(invitationService.verify(token)).thenReturn(entreprise);

        mockMvc.perform(get("/api/invites/verify/{token}", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(invitationService, times(1)).verify(token);
    }

    @Test
    public void testVerifyInvitation_InvalidToken() throws Exception {
        String token = "invalidToken";
        when(invitationService.verify(token)).thenReturn(null);

        mockMvc.perform(get("/api/invites/verify/{token}", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));

        verify(invitationService, times(1)).verify(token);
    }

    @Test
    public void testCreateInvitation_ValidEntrepriseId() throws Exception {
        Long entrepriseId = 1L;
        Entreprise entreprise = new Entreprise();
        Invitation invitation = new Invitation();
        invitation.setToken("generatedToken");

        when(entrepriseService.entreprise(entrepriseId)).thenReturn(entreprise);
        when(invitationService.add(entreprise)).thenReturn(invitation);

        mockMvc.perform(get("/api/invites/create/{entrepriseId}", entrepriseId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("generatedToken"));

        verify(entrepriseService, times(1)).entreprise(entrepriseId);
        verify(invitationService, times(1)).add(entreprise);
    }

    @Test
    public void testCreateInvitation_InvalidEntrepriseId() throws Exception {
        Long entrepriseId = 1L;

        when(entrepriseService.entreprise(entrepriseId)).thenReturn(null);

        mockMvc.perform(get("/api/invites/create/{entrepriseId}", entrepriseId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Entreprise not found"));

        verify(entrepriseService, times(1)).entreprise(entrepriseId);
        verify(invitationService, never()).add(any());
    }

    @Test
    public void testCreateInvitation_FailedToCreateInvitation() throws Exception {
        Long entrepriseId = 1L;
        Entreprise entreprise = new Entreprise();

        when(entrepriseService.entreprise(entrepriseId)).thenReturn(entreprise);
        when(invitationService.add(entreprise)).thenReturn(null);

        mockMvc.perform(get("/api/invites/create/{entrepriseId}", entrepriseId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to create invitation"));

        verify(entrepriseService, times(1)).entreprise(entrepriseId);
        verify(invitationService, times(1)).add(entreprise);
    }

}
