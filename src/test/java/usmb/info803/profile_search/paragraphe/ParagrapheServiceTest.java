package usmb.info803.profile_search.paragraphe;

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

import usmb.info803.profile_search.offre.Offre;

class ParagrapheServiceTest {

    @Mock
    private ParagrapheRepository paragrapheRepository;

    @InjectMocks
    private ParagrapheService paragrapheService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllParagraphes() {
        Offre offre = new Offre();
        Paragraphe p1 = new Paragraphe("Contenu 1", offre);
        Paragraphe p2 = new Paragraphe("Contenu 2", offre);

        when(paragrapheRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Paragraphe> paragraphes = paragrapheService.getAllParagraphes();

        assertEquals(2, paragraphes.size());
        assertEquals("Contenu 1", paragraphes.get(0).getContenu());
        assertEquals("Contenu 2", paragraphes.get(1).getContenu());
    }

    @Test
    void testGetParagrapheById() {
        Offre offre = new Offre();
        Paragraphe paragraphe = new Paragraphe("Contenu 1", offre);

        when(paragrapheRepository.findById(1L)).thenReturn(Optional.of(paragraphe));

        Paragraphe result = paragrapheService.getParagrapheById(1L);

        assertNotNull(result);
        assertEquals("Contenu 1", result.getContenu());
    }

    @Test
    void testCreateParagraphe() {
        Offre offre = new Offre();
        Paragraphe paragraphe = new Paragraphe("Contenu 1", offre);

        when(paragrapheRepository.save(paragraphe)).thenReturn(paragraphe);

        Paragraphe result = paragrapheService.createParagraphe(paragraphe);

        assertNotNull(result);
        assertEquals("Contenu 1", result.getContenu());
        verify(paragrapheRepository, times(1)).save(paragraphe);

    }

    @Test
    void testDeleteParagraphe() {
        when(paragrapheRepository.existsById(1L)).thenReturn(true);

        boolean result = paragrapheService.deleteParagraphe(1L);

        assertTrue(result);
        verify(paragrapheRepository, times(1)).deleteById(1L);
    }
}
