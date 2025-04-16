package usmb.info803.profile_search.bullet_point;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BulletPointService {

    private final BulletPointRepository bulletPointRepository;

    public BulletPointService(BulletPointRepository bulletPointRepository) {
        this.bulletPointRepository = bulletPointRepository;
    }

    public List<BulletPoint> getAllBulletPoints() {
        return bulletPointRepository.findAll();
    }

    public BulletPoint getBulletPointById(Long id) {
        return bulletPointRepository.findById(id).orElse(null);
    }

    public BulletPoint createBulletPoint(BulletPoint bulletPoint) {
        return bulletPointRepository.save(bulletPoint);
    }

    public boolean deleteBulletPoint(Long id) {
        if (bulletPointRepository.existsById(id)) {
            bulletPointRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
