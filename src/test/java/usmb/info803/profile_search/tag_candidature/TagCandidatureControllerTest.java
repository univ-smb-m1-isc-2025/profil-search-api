package usmb.info803.profile_search.tag_candidature;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import usmb.info803.profile_search.candidature.Candidature;
import usmb.info803.profile_search.candidature.CandidatureService;
import usmb.info803.profile_search.tag.Tag;
import usmb.info803.profile_search.tag.TagService;

public class TagCandidatureControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TagCandidatureService tagCandidatureService;

    @Mock
    private TagService tagService;

    @Mock
    private CandidatureService candidatureService;

    @InjectMocks
    private TagCandidatureController tagCandidatureController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        tagCandidatureService = mock(TagCandidatureService.class);
        tagService = mock(TagService.class);
        candidatureService = mock(CandidatureService.class);
        tagCandidatureController = new TagCandidatureController(tagCandidatureService, tagService, candidatureService);
        mockMvc = MockMvcBuilders.standaloneSetup(tagCandidatureController).build();
    }

    @Test
    public void testAddTagToCandidature_Success() throws Exception {
        Long tagId = 1L;
        Long candidatureId = 1L;

        Tag tag = new Tag();
        tag.setId(tagId);
        tag.setTag("TestTag");

        Candidature candidature = new Candidature();
        candidature.setId(candidatureId);

        TagCandidature tagCandidature = new TagCandidature(tag, candidature);

        when(tagService.tag(tagId)).thenReturn(tag);
        when(candidatureService.getCandidatureById(candidatureId)).thenReturn(candidature);
        when(tagCandidatureService.save(any(TagCandidature.class))).thenReturn(tagCandidature);

        mockMvc.perform(get("/api/tag-candidatures/add/{tagId}/{candidatureId}", tagId, candidatureId))
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Tag %s added to Candidature %s", tag.getTag(), candidature.getId())));
    }

    @Test
    public void testAddTagToCandidature_TagNotFound() throws Exception {
        Long tagId = 1L;
        Long candidatureId = 1L;

        when(tagService.tag(tagId)).thenReturn(null);

        mockMvc.perform(get("/api/tag-candidatures/add/{tagId}/{candidatureId}", tagId, candidatureId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Tag not found"));
    }

    @Test
    public void testAddTagToCandidature_CandidatureNotFound() throws Exception {
        Long tagId = 1L;
        Long candidatureId = 1L;

        Tag tag = new Tag();
        tag.setId(tagId);

        when(tagService.tag(tagId)).thenReturn(tag);
        when(candidatureService.getCandidatureById(candidatureId)).thenReturn(null);

        mockMvc.perform(get("/api/tag-candidatures/add/{tagId}/{candidatureId}", tagId, candidatureId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Candidature not found"));
    }

    @Test
    public void testDeleteTagFromCandidature_Success() throws Exception {
        Long tagId = 1L;
        Long candidatureId = 1L;

        TagCandidature tagCandidature = new TagCandidature();

        when(tagCandidatureService.findByTagIdAndCandidatureId(tagId, candidatureId)).thenReturn(tagCandidature);

        mockMvc.perform(delete("/api/tag-candidatures/delete/{tagId}/{candidatureId}", tagId, candidatureId))
                .andExpect(status().isOk())
                .andExpect(content().string("Tag removed from Candidature"));

        verify(tagCandidatureService, times(1)).delete(tagCandidature);
    }

    @Test
    public void testDeleteTagFromCandidature_NotFound() throws Exception {
        Long tagId = 1L;
        Long candidatureId = 1L;

        when(tagCandidatureService.findByTagIdAndCandidatureId(tagId, candidatureId)).thenReturn(null);

        mockMvc.perform(delete("/api/tag-candidatures/delete/{tagId}/{candidatureId}", tagId, candidatureId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("TagCandidature not found"));
    }

    @Test
    public void testDeleteAllTagsFromCandidature_Success() throws Exception {
        Long candidatureId = 1L;

        mockMvc.perform(delete("/api/tag-candidatures/deleteAll/{candidatureId}", candidatureId))
                .andExpect(status().isOk())
                .andExpect(content().string("All tags removed from Candidature"));

        verify(tagCandidatureService, times(1)).deleteByCandidatureId(candidatureId);
    }

}