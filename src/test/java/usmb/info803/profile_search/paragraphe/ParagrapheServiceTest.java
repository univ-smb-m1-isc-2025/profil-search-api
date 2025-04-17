package usmb.info803.profile_search.paragraphe;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import usmb.info803.profile_search.offre.Offre;
import usmb.info803.profile_search.offre.OffreService;

class ParagrapheServiceTest {

    @Mock
    private ParagrapheRepository paragrapheRepository;

    @Mock
    private OffreService offreService;

    @InjectMocks
    private ParagrapheService paragrapheService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllParagraphes() {
        Paragraphe paragraphe1 = new Paragraphe("Content 1", new Offre());
        Paragraphe paragraphe2 = new Paragraphe("Content 2", new Offre());

        when(paragrapheRepository.findAll()).thenReturn(List.of(paragraphe1, paragraphe2));

        List<Paragraphe> paragraphes = paragrapheService.getAllParagraphes();

        assertEquals(2, paragraphes.size());
        assertEquals("Content 1", paragraphes.get(0).getContenu());
        assertEquals("Content 2", paragraphes.get(1).getContenu());
    }

    @Test
    void testGetParagrapheById() {
        Paragraphe paragraphe = new Paragraphe("Content 1", new Offre());
        when(paragrapheRepository.findById(1L)).thenReturn(Optional.of(paragraphe));

        Paragraphe result = paragrapheService.getParagrapheById(1L);

        assertEquals("Content 1", result.getContenu());
    }

    @Test
    void testCreateParagraphe() {
        Paragraphe paragraphe = new Paragraphe("Content 1", new Offre());
        when(paragrapheRepository.save(paragraphe)).thenReturn(paragraphe);

        Paragraphe result = paragrapheService.createParagraphe(paragraphe);

        assertEquals("Content 1", result.getContenu());
    }

    @Test
    void testDeleteParagraphe() {
        when(paragrapheRepository.existsById(1L)).thenReturn(true);

        boolean result = paragrapheService.deleteParagraphe(1L);

        assertEquals(true, result);
        verify(paragrapheRepository, times(1)).deleteById(1L);
    }
}
