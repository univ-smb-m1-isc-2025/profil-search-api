package usmb.info803.profile_search.log;

import org.junit.jupiter.api.Test;

import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.entreprise.Entreprise;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class LogActionTest {

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.now();
        Member member = createMember();
        String message = "Test message";
        
        // Act
        LogAction logAction = new LogAction(dateTime, member, message);
        
        // Assert
        assertEquals(dateTime, logAction.getDateTime());
        assertEquals(member, logAction.getMember());
        assertEquals(message, logAction.getMessage());
    }
    
    @Test
    public void testSetters() {
        // Arrange
        LogAction logAction = new LogAction();
        Long id = 1L;
        LocalDateTime dateTime = LocalDateTime.now();
        Member member = createMember();
        String message = "Test message";
        
        // Act
        logAction.setId(id);
        logAction.setDateTime(dateTime);
        logAction.setMember(member);
        logAction.setMessage(message);
        
        // Assert
        assertEquals(id, logAction.getId());
        assertEquals(dateTime, logAction.getDateTime());
        assertEquals(member, logAction.getMember());
        assertEquals(message, logAction.getMessage());
    }
    
    @Test
    public void testIsValid() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.now();
        Member member = createMember();
        String message = "Test message";
        
        // Act & Assert
        // Valid log action
        LogAction validLogAction = new LogAction(dateTime, member, message);
        assertTrue(validLogAction.isValid());
        
        // Invalid log action - null dateTime
        LogAction invalidLogAction1 = new LogAction(null, member, message);
        assertFalse(invalidLogAction1.isValid());
        
        // Invalid log action - null message
        LogAction invalidLogAction2 = new LogAction(dateTime, member, null);
        assertFalse(invalidLogAction2.isValid());
        
        // Invalid log action - empty message
        LogAction invalidLogAction3 = new LogAction(dateTime, member, "");
        assertFalse(invalidLogAction3.isValid());
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
} 