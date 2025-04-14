package usmb.info803.profile_search.member;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import usmb.info803.profile_search.entreprise.Entreprise;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    public void testSaveNewMember() {
        Entreprise entreprise = new Entreprise();
        entreprise.setName("SomeEntreprise");
        entreprise.setId(1L);

        Member member = new Member();
        member.setId(1L);
        member.setNom("John");
        member.setPrenom("Doe");
        member.setEmail("john.doe@example.com");
        member.setActif(true);
        member.setEntreprise(entreprise);

        when(memberRepository.findById(1L)).thenReturn(Optional.empty());
        when(memberRepository.save(member)).thenReturn(member);

        boolean result = memberService.save(member);

        assertTrue(result);
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    public void testSaveExistingMember() {
        Entreprise entreprise = new Entreprise();
        entreprise.setName("SomeEntreprise");
        entreprise.setId(1L);

        Member existingMember = new Member();
        existingMember.setId(1L);
        existingMember.setNom("John");
        existingMember.setPrenom("Doe");
        existingMember.setEmail("john.doe@example.com");
        existingMember.setActif(true);
        existingMember.setEntreprise(entreprise);

        Member updatedMember = new Member();
        updatedMember.setId(1L);
        updatedMember.setNom("Jane");
        updatedMember.setPrenom("Smith");
        updatedMember.setEmail("jane.smith@example.com");
        updatedMember.setActif(false);
        updatedMember.setEntreprise(entreprise);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(existingMember));
        when(memberRepository.save(existingMember)).thenReturn(existingMember);

        boolean result = memberService.save(updatedMember);

        assertTrue(result);
        assertEquals("Jane", existingMember.getNom());
        assertEquals("Smith", existingMember.getPrenom());
        assertEquals("jane.smith@example.com", existingMember.getEmail());
        assertFalse(existingMember.isActif());
        verify(memberRepository, times(1)).save(existingMember);
    }

    @Test
    public void testMembers() {
        Member member1 = new Member();
        Member member2 = new Member();
        List<Member> members = Arrays.asList(member1, member2);

        when(memberRepository.findAll()).thenReturn(members);

        List<Member> result = memberService.members();

        assertEquals(2, result.size());
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    public void testMemberById() {
        Member member = new Member();
        member.setId(1L);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        Member result = memberService.memberById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteExistingMember() {
        when(memberRepository.existsById(1L)).thenReturn(true);

        boolean result = memberService.delete(1L);

        assertTrue(result);
        verify(memberRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteNonExistingMember() {
        when(memberRepository.existsById(1L)).thenReturn(false);

        boolean result = memberService.delete(1L);

        assertFalse(result);
        verify(memberRepository, never()).deleteById(1L);
    }
}
