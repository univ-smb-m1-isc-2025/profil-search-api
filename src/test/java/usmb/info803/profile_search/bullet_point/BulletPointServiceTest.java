package usmb.info803.profile_search.bullet_point;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import usmb.info803.profile_search.offre.Offre;
import usmb.info803.profile_search.offre.OffreService;

class BulletPointServiceTest {

    @Mock
    private BulletPointRepository bulletPointRepository;

    @Mock
    private OffreService offreService;

    @InjectMocks
    private BulletPointService bulletPointService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBulletPoints() {
        // Préparation des données de test
        Offre offre = new Offre();
        offre.setId(1L);

        BulletPoint bulletPoint1 = new BulletPoint("Content 1", offre);
        bulletPoint1.setId(1L);

        BulletPoint bulletPoint2 = new BulletPoint("Content 2", offre);
        bulletPoint2.setId(2L);

        when(bulletPointRepository.findAll()).thenReturn(Arrays.asList(bulletPoint1, bulletPoint2));

        // Exécution du test
        List<BulletPointDTO> bulletPoints = bulletPointService.getAllBulletPoints();

        // Vérifications
        assertEquals(2, bulletPoints.size());
        assertEquals("Content 1", bulletPoints.get(0).getContent());
        assertEquals("Content 2", bulletPoints.get(1).getContent());
        assertEquals(1L, bulletPoints.get(0).getOffreId());
        assertEquals(1L, bulletPoints.get(1).getOffreId());
    }

    @Test
    void testGetBulletPointById() {
        // Préparation des données de test
        Offre offre = new Offre();
        offre.setId(1L);

        BulletPoint bulletPoint = new BulletPoint("Content 1", offre);
        bulletPoint.setId(1L);

        when(bulletPointRepository.findById(1L)).thenReturn(Optional.of(bulletPoint));

        // Exécution du test
        BulletPointDTO result = bulletPointService.getBulletPointById(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Content 1", result.getContent());
        assertEquals(1L, result.getOffreId());
    }

    @Test
    void testCreateBulletPoint() {
        // Préparation des données de test
        Offre offre = new Offre();
        offre.setId(1L);

        CreateBulletPointBody createBody = new CreateBulletPointBody("Content 1", 1L);

        // Mock l'offre pour qu'elle mette correctement l'id du bulletPoint à 1 lors de l'ajout
        when(offreService.getOffreById(1L)).thenReturn(offre);
        when(offreService.createOffre(any(Offre.class))).thenAnswer(invocation -> {
            Offre o = invocation.getArgument(0);
            // Simuler le comportement JPA qui assigne un ID lorsqu'un élément est ajouté
            if (!o.getBulletPoints().isEmpty()) {
                BulletPoint bp = o.getBulletPoints().get(0);
                bp.setId(1L);
            }
            return o;
        });

        // Exécution du test
        BulletPointDTO result = bulletPointService.createBulletPoint(createBody);

        // Vérifications
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Content 1", result.getContent());
        assertEquals(1L, result.getOffreId());
        verify(offreService, times(1)).createOffre(any(Offre.class));
    }

    @Test
    void testDeleteBulletPoint() {
        // Préparation des données de test
        Offre offre = new Offre();
        offre.setId(1L);

        BulletPoint bulletPoint = new BulletPoint("Content 1", offre);
        bulletPoint.setId(1L);

        offre.addBulletPoint(bulletPoint);

        when(bulletPointRepository.findById(1L)).thenReturn(Optional.of(bulletPoint));

        // Exécution du test
        boolean result = bulletPointService.deleteBulletPoint(1L);

        // Vérifications
        assertTrue(result);
        assertEquals(0, offre.getBulletPoints().size());
    }

    @Test
    void testDeleteBulletPointNotFound() {
        // Préparation des données de test
        when(bulletPointRepository.findById(1L)).thenReturn(Optional.empty());

        // Exécution du test
        boolean result = bulletPointService.deleteBulletPoint(1L);

        // Vérifications
        assertFalse(result);
    }
}
