package usmb.info803.profile_search.bullet_point;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulletPointRepository extends JpaRepository<BulletPoint, Long> {

    List<BulletPoint> findByOffreId(Long offreId);
}
