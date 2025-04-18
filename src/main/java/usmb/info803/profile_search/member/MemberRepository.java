package usmb.info803.profile_search.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
    List<Member> findByNomAndPrenom(String nom, String prenom);
    List<Member> findByActif(boolean actif);
    List<Member> findByEntrepriseId(Long entrepriseId);
}
