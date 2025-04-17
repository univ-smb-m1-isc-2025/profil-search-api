package usmb.info803.profile_search.tag;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;

public class TagControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TagService tagService;

    @InjectMocks
    private TagController tagController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        tagService = mock(TagService.class); // Manually mock TagService
        objectMapper = new ObjectMapper();  // Manually instantiate ObjectMapper
        tagController = new TagController(tagService); // Inject the mock into the controller
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
    }

    @Test
    public void testGetAllTags() throws Exception {
        Tag tag1 = new Tag("Tag1");
        Tag tag2 = new Tag("Tag2");
        tag1.setId(1L);
        tag2.setId(2L);
        List<Tag> tags = Arrays.asList(tag1, tag2);
        when(tagService.all()).thenReturn(tags);

        mockMvc.perform(get("/api/tags/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(tags.size()))
                .andExpect(jsonPath("$[0].tag").value("Tag1"))
                .andExpect(jsonPath("$[1].tag").value("Tag2"));

        verify(tagService, times(1)).all();
    }

    @Test
    public void testGetTagById() throws Exception {
        Tag tag = new Tag("Tag1");
        tag.setId(1L);
        when(tagService.tag(1L)).thenReturn(tag);

        mockMvc.perform(get("/api/tags/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tag").value("Tag1"));

        verify(tagService, times(1)).tag(1L);
    }

    @Test
    public void testCreateTag() throws Exception {
        String name = "NewTag";
        CreateTagBody tagName = new CreateTagBody(name);
        Tag tag = new Tag(name);
        tag.setId(1L);
        when(tagService.create(name)).thenReturn(tag);

        mockMvc.perform(post("/api/tags/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagName)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tag").value(name));

        verify(tagService, times(1)).create(name);
    }

    @Test
    public void testCreateTagAlreadyExists() throws Exception {
        String tagName = "ExistingTag";
        CreateTagBody tagNameBody = new CreateTagBody(tagName);
        Tag tag = new Tag(tagName);
        tag.setId(1L);
        when(tagService.create(tagName)).thenReturn(null); // Simulate tag already exists

        mockMvc.perform(post("/api/tags/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagNameBody)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format("Tag already exists : %s", tagName)));

        verify(tagService, times(1)).create(tagName);
    }

    @Test
    public void testCreateTagEmpty() throws Exception {
        String tagName = "";
        CreateTagBody tagNameBody = new CreateTagBody(tagName);

        mockMvc.perform(post("/api/tags/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagNameBody)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Tag is empty"));

        verify(tagService, never()).create(tagName);
    }
}
