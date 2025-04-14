package usmb.info803.profile_search.invitation;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import usmb.info803.profile_search.entreprise.Entreprise;

@ExtendWith(MockitoExtension.class)
class InvitationServiceTest {

    @Mock
    private InvitationRepository invitationRepository;

    @InjectMocks 
    private InvitationService invitationService;

    @Test
    void testClean() {
        // Arrange
        doNothing().when(invitationRepository).deleteBytimeoutDateLessThan(any(Date.class));

        // Act
        invitationService.clean();

        // Assert
        verify(invitationRepository, times(1)).deleteBytimeoutDateLessThan(any(Date.class));
    }

    @Test
    void testVerifyWithValidToken() {
        // Arrange
        String token = "validToken";
        Entreprise entreprise = new Entreprise();
        Invitation invitation = new Invitation(token, entreprise);
        when(invitationRepository.findByToken(token)).thenReturn(Optional.of(invitation));

        // Act
        Entreprise result = invitationService.verify(token);

        // Assert
        assertThat(result).isEqualTo(entreprise);
        verify(invitationRepository, times(1)).findByToken(token);
    }

    @Test
    void testVerifyWithInvalidToken() {
        // Arrange
        String token = "invalidToken";
        when(invitationRepository.findByToken(token)).thenReturn(Optional.empty());

        // Act
        Entreprise result = invitationService.verify(token);

        // Assert
        assertThat(result).isNull();
        verify(invitationRepository, times(1)).findByToken(token);
    }

    @Test
    void testAdd() {
        // Arrange
        Entreprise entreprise = new Entreprise();
        when(invitationRepository.save(any(Invitation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Invitation result = invitationService.add(entreprise);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getEntreprise()).isEqualTo(entreprise);
        assertThat(result.getToken()).isNotEmpty();
        verify(invitationRepository, times(1)).save(any(Invitation.class));
    }

    @Test
    void testDelete() {
        // Arrange
        String token = "tokenToDelete";
        doNothing().when(invitationRepository).deleteByToken(token);

        // Act
        invitationService.delete(token);

        // Assert
        verify(invitationRepository, times(1)).deleteByToken(token);
    }
}