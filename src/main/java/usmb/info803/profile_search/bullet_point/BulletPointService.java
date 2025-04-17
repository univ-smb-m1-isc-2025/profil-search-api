package usmb.info803.profile_search.bullet_point;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import usmb.info803.profile_search.offre.Offre;
import usmb.info803.profile_search.offre.OffreService;

@Service
public class BulletPointService {

    private final BulletPointRepository bulletPointRepository;
    private final OffreService offreService;

    public BulletPointService(BulletPointRepository bulletPointRepository, OffreService offreService) {
        this.bulletPointRepository = bulletPointRepository;
        this.offreService = offreService;
    }

    public List<BulletPointDTO> getAllBulletPoints() {
        return bulletPointRepository.findAll()
                .stream()
                .map(BulletPointDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public BulletPointDTO getBulletPointById(Long id) {
        return bulletPointRepository.findById(id)
                .map(BulletPointDTO::fromEntity)
                .orElse(null);
    }

    public List<BulletPointDTO> getBulletPointsByOffreId(Long offreId) {
        return bulletPointRepository.findByOffreId(offreId)
                .stream()
                .map(BulletPointDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public BulletPointDTO createBulletPoint(CreateBulletPointBody createBody) {
        if (!createBody.isValid()) {
            return null;
        }

        Offre offre = offreService.getOffreById(createBody.getOffreId());
        if (offre == null) {
            return null;
        }

        BulletPoint bulletPoint = new BulletPoint();
        bulletPoint.setContent(createBody.getContent());

        // Utiliser la relation bidirectionnelle
        offre.addBulletPoint(bulletPoint);

        // Sauvegarder l'offre mettra également à jour les bullet points associés
        offreService.createOffre(offre);

        return BulletPointDTO.fromEntity(bulletPoint);
    }

    @Transactional
    public boolean deleteBulletPoint(Long id) {
        BulletPoint bulletPoint = bulletPointRepository.findById(id).orElse(null);
        if (bulletPoint != null) {
            Offre offre = bulletPoint.getOffre();
            offre.removeBulletPoint(bulletPoint);
            return true;
        }
        return false;
    }
}
