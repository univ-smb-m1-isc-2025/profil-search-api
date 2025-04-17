package usmb.info803.profile_search.invitation;

import java.util.Date;
import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import usmb.info803.profile_search.Utils;
import usmb.info803.profile_search.entreprise.Entreprise;

@Service
public class InvitationService {

    private final InvitationRepository invitationRepository;

    public InvitationService(InvitationRepository invitationRepository){
        this.invitationRepository = invitationRepository;
    }

    @Transactional
    public void clean(){
        invitationRepository.deleteBytimeoutDateLessThan(Date.from(Instant.now()));
    }

    public Entreprise verify(String token){
        clean();
        Invitation invitation = invitationRepository.findByToken(token).orElse(null);
        if (invitation != null) {
            Entreprise entreprise = invitation.getEntreprise();
            if (entreprise != null) {
                return entreprise;
            }
        }
        return null;
    }

    @Transactional
    public Invitation add(Entreprise entreprise){
        clean();
        Invitation invitation = new Invitation(Utils.randomString(100), entreprise);
        invitationRepository.save(invitation);
        return invitation;
    }

    @Transactional
    public void delete(String token){
        clean();
        invitationRepository.deleteByToken(token);
    }
}
