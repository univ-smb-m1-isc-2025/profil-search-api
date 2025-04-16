package usmb.info803.profile_search.candidature;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.offre.Offre;

@ExtendWith(MockitoExtension.class)
public class CandidatureServiceTest {

    @Mock
    private CandidatureRepository candidatureRepository;

    @InjectMocks
    private CandidatureService candidatureService;

    @Test
    public void testGetAllCandidatures() {
        List<Candidature> mockCandidatures = Arrays.asList(new Candidature(), new Candidature());
        when(candidatureRepository.findAll()).thenReturn(mockCandidatures);

        List<Candidature> result = candidatureService.getAllCandidatures();

        assertEquals(2, result.size());
        verify(candidatureRepository, times(1)).findAll();
    }

    @Test
    public void testGetCandidatureById() {
        Candidature mockCandidature = new Candidature();
        when(candidatureRepository.findById(1L)).thenReturn(Optional.of(mockCandidature));

        Candidature result = candidatureService.getCandidatureById(1L);

        assertNotNull(result);
        verify(candidatureRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateCandidature() {
        Offre mockOffre = new Offre();
        Candidature mockCandidature = new Candidature("test@example.com", "Test Name", mockOffre);
        when(candidatureRepository.save(any(Candidature.class))).thenReturn(mockCandidature);

        Candidature result = candidatureService.createCandidature("test@example.com", "Test Name", mockOffre);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmailCandidat());
        assertEquals("Test Name", result.getName());
        verify(candidatureRepository, times(1)).save(any(Candidature.class));
    }

    @Test
    public void testDeleteCandidature() {
        doNothing().when(candidatureRepository).deleteById(1L);

        candidatureService.deleteCandidature(1L);

        verify(candidatureRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testAssign() {
        Member mockMember = new Member();
        Candidature mockCandidature = new Candidature();
        when(candidatureRepository.findById(1L)).thenReturn(Optional.of(mockCandidature));
        when(candidatureRepository.save(any(Candidature.class))).thenReturn(mockCandidature);

        Candidature result = candidatureService.assign(1L, mockMember);

        assertNotNull(result);
        assertEquals(mockMember, result.getAssignee());
        verify(candidatureRepository, times(1)).findById(1L);
        verify(candidatureRepository, times(1)).save(mockCandidature);
    }
}
