package usmb.info803.profile_search.tagCandidature;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import usmb.info803.profile_search.candidature.Candidature;
import usmb.info803.profile_search.tag.Tag;
import usmb.info803.profile_search.tag_candidature.TagCandidature;
import usmb.info803.profile_search.tag_candidature.TagCandidatureRepository;
import usmb.info803.profile_search.tag_candidature.TagCandidatureService;

public class TagCandidatureServiceTest {

    @Mock
    private TagCandidatureRepository tagCandidatureRepository;

    @InjectMocks
    private TagCandidatureService tagCandidatureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByTagId() {
        Long tagId = 1L;
        List<TagCandidature> expected = Arrays.asList(new TagCandidature());
        when(tagCandidatureRepository.findByTagId(tagId)).thenReturn(expected);

        List<TagCandidature> result = tagCandidatureService.findByTagId(tagId);

        assertEquals(expected, result);
        verify(tagCandidatureRepository, times(1)).findByTagId(tagId);
    }

    @Test
    void testFindByCandidatureId() {
        Long candidatureId = 1L;
        List<TagCandidature> expected = Arrays.asList(new TagCandidature());
        when(tagCandidatureRepository.findByCandidatureId(candidatureId)).thenReturn(expected);

        List<TagCandidature> result = tagCandidatureService.findByCandidatureId(candidatureId);

        assertEquals(expected, result);
        verify(tagCandidatureRepository, times(1)).findByCandidatureId(candidatureId);
    }

    @Test
    void testFindByTagIdAndCandidatureId() {
        Long tagId = 1L;
        Long candidatureId = 2L;
        TagCandidature expected = new TagCandidature();
        when(tagCandidatureRepository.findByTagIdAndCandidatureId(tagId, candidatureId)).thenReturn(expected);

        TagCandidature result = tagCandidatureService.findByTagIdAndCandidatureId(tagId, candidatureId);

        assertEquals(expected, result);
        verify(tagCandidatureRepository, times(1)).findByTagIdAndCandidatureId(tagId, candidatureId);
    }

    @Test
    void testSave() {
        Tag tag = new Tag();
        Candidature candidature = new Candidature();
        TagCandidature tagCandidature = new TagCandidature(tag, candidature);
        when(tagCandidatureRepository.save(tagCandidature)).thenReturn(tagCandidature);

        TagCandidature result = tagCandidatureService.save(tagCandidature);

        assertEquals(tagCandidature, result);
        verify(tagCandidatureRepository, times(1)).save(tagCandidature);
    }

    @Test
    void testDeleteByCandidatureId() {
        Long candidatureId = 1L;

        tagCandidatureService.deleteByCandidatureId(candidatureId);

        verify(tagCandidatureRepository, times(1)).deleteByCandidatureId(candidatureId);
    }

    @Test
    void testDeleteByTagIdAndCandidatureId() {
        Long tagId = 1L;
        Long candidatureId = 2L;

        tagCandidatureService.deleteByTagIdAndCandidatureId(tagId, candidatureId);

        verify(tagCandidatureRepository, times(1)).deleteByTagIdAndCandidatureId(tagId, candidatureId);
    }

    @Test
    void testDelete() {
        TagCandidature tagCandidature = new TagCandidature();

        tagCandidatureService.delete(tagCandidature);

        verify(tagCandidatureRepository, times(1)).delete(tagCandidature);
    }
}
