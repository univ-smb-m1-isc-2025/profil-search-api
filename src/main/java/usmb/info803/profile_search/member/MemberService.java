package usmb.info803.profile_search.member;

import org.springframework.stereotype.Service;

import usmb.info803.profile_search.entreprise.Entreprise;
import usmb.info803.profile_search.entreprise.EntrepriseRepository;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final EntrepriseRepository entrepriseRepository;

    public MemberService(MemberRepository memberRepository, EntrepriseRepository entrepriseRepository) {
        this.memberRepository = memberRepository;
        this.entrepriseRepository = entrepriseRepository;
    }

    public List<Member> members() {
        return memberRepository.findAll();
    }

    public Member memberById(long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member memberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public List<Member> memberByNomAndPrenom(String nom, String prenom) {
        return memberRepository.findByNomAndPrenom(nom, prenom);
    }

    public List<Member> membersByActif(boolean actif) {
        return memberRepository.findByActif(actif);
    }

    public List<Member> membersByEntrepriseId(Long entrepriseId) {
        return memberRepository.findByEntrepriseId(entrepriseId);
    }

    public boolean delete(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean create(String nom, String prenom, String email, Long entrepriseId) {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            return false;
        }
        Entreprise entreprise = entrepriseRepository.findById(entrepriseId).orElse(null);
        if (entreprise == null) {
            return false;
        }
        member = new Member(nom, prenom, email, entreprise);
        memberRepository.save(member);
        return true;
    }
    
}
