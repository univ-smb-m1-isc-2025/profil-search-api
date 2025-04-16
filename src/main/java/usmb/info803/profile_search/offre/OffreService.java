package usmb.info803.profile_search.offre;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.member.MemberService;

@Service
public class OffreService {

    private final OffreRepository offreRepository;
    private final MemberService memberService;

    public OffreService(OffreRepository offreRepository, MemberService memberService) {
        this.offreRepository = offreRepository;
        this.memberService = memberService;
    }

    public List<Offre> getAllOffres() {
        return offreRepository.findAll();
    }

    public Offre getOffreById(Long id) {
        return offreRepository.findById(id).orElse(null);
    }

    public Offre createOffre(Offre offre) {
        return offreRepository.save(offre);
    }

    @Transactional
    public Offre createOffreFromDTO(OffreCreationDTO dto) {
        Member member = memberService.memberById(dto.getMemberId());
        if (member == null) {
            return null; // Membre non trouvé
        }

        Offre offre = new Offre(dto.getTitre(), member, dto.isEst_publiee());
        return offreRepository.save(offre);
    }

    public boolean deleteOffre(Long id) {
        if (offreRepository.existsById(id)) {
            offreRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
