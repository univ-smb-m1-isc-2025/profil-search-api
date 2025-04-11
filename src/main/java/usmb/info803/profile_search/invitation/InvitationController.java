package usmb.info803.profile_search.invitation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usmb.info803.profile_search.entreprise.Entreprise;
import usmb.info803.profile_search.entreprise.EntrepriseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/api/invites")
public class InvitationController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final InvitationService invitationService;
    private final EntrepriseService entrepriseService;

    public InvitationController(InvitationService invitationService, EntrepriseService entrepriseService) {
        this.invitationService = invitationService;
        this.entrepriseService = entrepriseService;
    }

    @GetMapping(value = "/create/{entrepriseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createInvitation(@PathVariable("entrepriseId") Long entrepriseId) {
        logger.info(String.format("Create invitation for entreprise id : %d", entrepriseId));
        Entreprise entreprise = entrepriseService.entreprise(entrepriseId);
        if (entreprise == null) {
            return ResponseEntity.badRequest().body("Entreprise not found");
        }
        Invitation invitation = invitationService.add(entreprise);
        if (invitation == null) {
            return ResponseEntity.status(500).body("Failed to create invitation");
        }
        return ResponseEntity.ok(invitation.getToken());
    }
    
}
