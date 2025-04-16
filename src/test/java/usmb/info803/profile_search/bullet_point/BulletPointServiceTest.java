package usmb.info803.profile_search.bullet_point;

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

class BulletPointServiceTest {

    @Mock
    private BulletPointRepository bulletPointRepository;

    @InjectMocks
    private BulletPointService bulletPointService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBulletPoints() {
        Offre offre = new Offre();
        BulletPoint bp1 = new BulletPoint("Point 1", offre);
        BulletPoint bp2 = new BulletPoint("Point 2", offre);

        when(bulletPointRepository.findAll()).thenReturn(Arrays.asList(bp1, bp2));

        List<BulletPoint> bulletPoints = bulletPointService.getAllBulletPoints();

        assertEquals(2, bulletPoints.size());
        assertEquals("Point 1", bulletPoints.get(0).getBulletPoint());
        assertEquals("Point 2", bulletPoints.get(1).getBulletPoint());
    }

    @Test
    void testGetBulletPointById() {
        Offre offre = new Offre();
        BulletPoint bulletPoint = new BulletPoint("Point 1", offre);

        when(bulletPointRepository.findById(1L)).thenReturn(Optional.of(bulletPoint));

        BulletPoint result = bulletPointService.getBulletPointById(1L);

        assertNotNull(result);
        assertEquals("Point 1", result.getBulletPoint());
    }

    @Test
    void testCreateBulletPoint() {
        Offre offre = new Offre();
        BulletPoint bulletPoint = new BulletPoint("Point 1", offre);

        when(bulletPointRepository.save(bulletPoint)).thenReturn(bulletPoint);

        BulletPoint result = bulletPointService.createBulletPoint(bulletPoint);

        assertNotNull(result);
        assertEquals("Point 1", result.getBulletPoint());
        verify(bulletPointRepository, times(1)).save(bulletPoint);
    }

    @Test
    void testDeleteBulletPoint() {
        when(bulletPointRepository.existsById(1L)).thenReturn(true);

        boolean result = bulletPointService.deleteBulletPoint(1L);

        assertTrue(result);
        verify(bulletPointRepository, times(1)).deleteById(1L);
    }
}
