package usmb.info803.profile_search.log;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.entreprise.Entreprise;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogActionServiceTest {

    @Mock
    private LogActionRepository logActionRepository;

    @InjectMocks
    private LogActionService logActionService;

    @Test
    public void testLogAction() {
        // Arrange
        Member member = createMember();
        String message = "Test message";
        
        LogAction savedLogAction = new LogAction();
        savedLogAction.setId(1L);
        savedLogAction.setMember(member);
        savedLogAction.setMessage(message);
        savedLogAction.setDateTime(LocalDateTime.now());
        
        when(logActionRepository.save(any(LogAction.class))).thenReturn(savedLogAction);
        
        // Act
        LogAction result = logActionService.logAction(member, message);
        
        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(member, result.getMember());
        assertEquals(message, result.getMessage());
        assertNotNull(result.getDateTime());
        verify(logActionRepository, times(1)).save(any(LogAction.class));
    }
    
    @Test
    public void testGetLogsByMember() {
        // Arrange
        Member member = createMember();
        List<LogAction> logs = Arrays.asList(
            createLogAction(1L, member, "Message 1"),
            createLogAction(2L, member, "Message 2")
        );
        
        when(logActionRepository.findByMember(member)).thenReturn(logs);
        
        // Act
        List<LogAction> result = logActionService.getLogsByMember(member);
        
        // Assert
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(logActionRepository, times(1)).findByMember(member);
    }
    
    @Test
    public void testGetLogsByMemberId() {
        // Arrange
        Member member = createMember();
        Long memberId = 1L;
        List<LogAction> logs = Arrays.asList(
            createLogAction(1L, member, "Message 1"),
            createLogAction(2L, member, "Message 2")
        );
        
        when(logActionRepository.findByMemberId(memberId)).thenReturn(logs);
        
        // Act
        List<LogAction> result = logActionService.getLogsByMemberId(memberId);
        
        // Assert
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(logActionRepository, times(1)).findByMemberId(memberId);
    }
    
    @Test
    public void testGetAllLogs() {
        // Arrange
        Member member1 = createMember();
        Member member2 = createMember();
        member2.setId(2L);
        
        List<LogAction> logs = Arrays.asList(
            createLogAction(1L, member1, "Message 1"),
            createLogAction(2L, member2, "Message 2")
        );
        
        when(logActionRepository.findAll()).thenReturn(logs);
        
        // Act
        List<LogAction> result = logActionService.getAllLogs();
        
        // Assert
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(logActionRepository, times(1)).findAll();
    }
    
    private Member createMember() {
        Entreprise entreprise = new Entreprise();
        entreprise.setId(1L);
        entreprise.setName("TestEntreprise");
        
        Member member = new Member();
        member.setId(1L);
        member.setNom("Doe");
        member.setPrenom("John");
        member.setEmail("john.doe@example.com");
        member.setActif(true);
        member.setEntreprise(entreprise);
        
        return member;
    }
    
    private LogAction createLogAction(Long id, Member member, String message) {
        LogAction logAction = new LogAction();
        logAction.setId(id);
        logAction.setMember(member);
        logAction.setMessage(message);
        logAction.setDateTime(LocalDateTime.now());
        
        return logAction;
    }
} 