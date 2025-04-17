package usmb.info803.profile_search.member;

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

import usmb.info803.profile_search.entreprise.Entreprise;
import usmb.info803.profile_search.invitation.InvitationService;

public class MemberControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MemberService memberService;

    @Mock
    private InvitationService invitationService;

    @InjectMocks
    private MemberController memberController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        memberService = mock(MemberService.class);
        invitationService = mock(InvitationService.class);
        memberController = new MemberController(memberService, invitationService);
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    public void testGetAllMembers() throws Exception {
        Entreprise entreprise = new Entreprise("Test Company");
        entreprise.setId(1L);

        Member member = new Member("John", "Doe", "john.doe@example.com", entreprise);
        member.setId(1L);
        when(memberService.members()).thenReturn(List.of(member));

        mockMvc.perform(get("/api/members/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nom").value("John"))
                .andExpect(jsonPath("$[0].prenom").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[0].actif").value(true));

        verify(memberService, times(1)).members();
    }

    @Test
    public void testGetMemberById() throws Exception {
        Entreprise entreprise = new Entreprise("Test Company");
        entreprise.setId(1L);

        Member member = new Member("John", "Doe", "john.doe@example.com", entreprise);
        member.setId(1L);
        when(memberService.memberById(1L)).thenReturn(member);

        mockMvc.perform(get("/api/members/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("John"))
                .andExpect(jsonPath("$.prenom").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.actif").value(true));

        verify(memberService, times(1)).memberById(1L);
    }

    @Test
    public void testGetMemberByEmail() throws Exception {
        Entreprise entreprise = new Entreprise("Test Company");
        entreprise.setId(1L);

        Member member = new Member("John", "Doe", "john.doe@example.com", entreprise);
        member.setId(1L);
        when(memberService.memberByEmail("john.doe@example.com")).thenReturn(member);

        mockMvc.perform(get("/api/members/email/john.doe@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("John"))
                .andExpect(jsonPath("$.prenom").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.actif").value(true));

        verify(memberService, times(1)).memberByEmail("john.doe@example.com");
    }

    @Test
    public void testDeactivateMember() throws Exception {
        Entreprise entreprise = new Entreprise("Test Company");
        entreprise.setId(1L);

        Member member = new Member("John", "Doe", "john.doe@example.com", entreprise);
        when(memberService.memberById(1L)).thenReturn(member);
        when(memberService.save(any(Member.class))).thenReturn(true);

        mockMvc.perform(post("/api/members/deactivate/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User 1 deactivated"));

        verify(memberService, times(1)).memberById(1L);
        verify(memberService, times(1)).save(any(Member.class));
    }

    @Test
    public void testDeleteMember() throws Exception {
        when(memberService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/members/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User 1 deleted"));

        verify(memberService, times(1)).delete(1L);
    }

    @Test
    public void testCreateMember() throws Exception {
        CreateMemberBody request = new CreateMemberBody("John", "Doe", "john.doe@example.com", "validToken");
        Entreprise entreprise = new Entreprise("Test Company");
        entreprise.setId(1L);
        when(invitationService.verify("validToken")).thenReturn(entreprise);
        when(memberService.create("John", "Doe", "john.doe@example.com", entreprise)).thenReturn("");

        mockMvc.perform(post("/api/members/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("User john.doe@example.com created"));

        verify(invitationService, times(1)).verify("validToken");
        verify(memberService, times(1)).create("John", "Doe", "john.doe@example.com", entreprise);
    }

}
