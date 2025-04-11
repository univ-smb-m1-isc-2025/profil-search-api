package usmb.info803.profile_search.offre;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import usmb.info803.profile_search.member.Member;

class OffreServiceTest {

    @Mock
    private OffreRepository offreRepository;

    @InjectMocks
    private OffreService offreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOffres() {
        Member user = new Member();
        Offre offre1 = new Offre("Offre 1", user, true);
        Offre offre2 = new Offre("Offre 2", user, false);

        when(offreRepository.findAll()).thenReturn(Arrays.asList(offre1, offre2));

        List<Offre> offres = offreService.getAllOffres();

        assertEquals(2, offres.size());
        assertEquals("Offre 1", offres.get(0).getTitre());
        assertEquals("Offre 2", offres.get(1).getTitre());
    }

    @Test
    void testGetOffreById() {
        Member user = new Member();
        Offre offre = new Offre("Offre 1", user, true);
        when(offreRepository.findById(1L)).thenReturn(Optional.of(offre));

        Offre result = offreService.getOffreById(1L);

        assertNotNull(result);
        assertEquals("Offre 1", result.getTitre());
    }

    @Test
    void testCreateOffre() {
        Member user = new Member();
        Offre offre = new Offre("Offre 1", user, true);
        when(offreRepository.save(offre)).thenReturn(offre);

        Offre result = offreService.createOffre(offre);

        assertNotNull(result);
        assertEquals("Offre 1", result.getTitre());
    }

    @Test
    void testDeleteOffre() {
        when(offreRepository.existsById(1L)).thenReturn(true);

        boolean result = offreService.deleteOffre(1L);

        assertTrue(result);
        verify(offreRepository, times(1)).deleteById(1L);
    }
}
