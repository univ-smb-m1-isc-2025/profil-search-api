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

    public boolean save(Member member) {
        if (member == null || !member.isValid()) {
            return false;
        }
        Member existingMember = memberRepository.findById(member.getId()).orElse(null);
        if (existingMember != null) {
            existingMember.setNom(member.getNom());
            existingMember.setPrenom(member.getPrenom());
            existingMember.setEmail(member.getEmail());
            existingMember.setActif(member.isActif());
            memberRepository.save(existingMember);
            return true;
        }else{
            memberRepository.save(member);
            return true;
        }
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

    public String create(String nom, String prenom, String email, Long entrepriseId) {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            return String.format("Email %s alredy exists", email);
        }
        Entreprise entreprise = entrepriseRepository.findById(entrepriseId).orElse(null);
        if (entreprise == null) {
            return String.format("Entreprise with id %d not found", entrepriseId);
        }
        member = new Member(nom, prenom, email, entreprise);
        memberRepository.save(member);
        return "";
    }
    
}
