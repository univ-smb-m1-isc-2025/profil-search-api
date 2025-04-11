package usmb.info803.profile_search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import usmb.info803.profile_search.tag.Tag;
import usmb.info803.profile_search.tag.TagRepository;
import usmb.info803.profile_search.tag.TagService;

public class TagServiceTest {

	@Mock
	private TagRepository tagRepository;

	@InjectMocks
	private TagService tagService;

	@BeforeEach
	void setUp() {
			MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAll() {
		List<Tag> tags = Arrays.asList(new Tag("Tag 1"), new Tag("Tag 2"));
		when(tagRepository.findAll()).thenReturn(tags);

		List<Tag> result = tagService.all();

		assertThat(result).isEqualTo(tags);
		verify(tagRepository, times(1)).findAll();
	}

	@Test
	void testTagById() {
		Tag tag = new Tag("Sample Tag");
		when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));

		Tag result = tagService.tag(1L);

		assertThat(result).isEqualTo(tag);
		verify(tagRepository, times(1)).findById(1L);
	}

	@Test
	void testTagByString() {
		Tag tag = new Tag("Sample Tag");
		when(tagRepository.findByTag("Sample Tag")).thenReturn(Optional.of(tag));

		Tag result = tagService.tag("Sample Tag");

		assertThat(result).isEqualTo(tag);
		verify(tagRepository, times(1)).findByTag("Sample Tag");
	}

	@Test
	void testCreateTagWhenNotExists() {
		when(tagRepository.findByTag("New Tag")).thenReturn(Optional.empty());

		boolean result = tagService.create("New Tag");

		assertThat(result).isTrue();
		verify(tagRepository, times(1)).findByTag("New Tag");
		verify(tagRepository, times(1)).save(any(Tag.class));
	}

	@Test
	void testCreateTagWhenExists() {
		Tag existingTag = new Tag("Existing Tag");
		when(tagRepository.findByTag("Existing Tag")).thenReturn(Optional.of(existingTag));

		boolean result = tagService.create("Existing Tag");

		assertThat(result).isFalse();
		verify(tagRepository, times(1)).findByTag("Existing Tag");
		verify(tagRepository, never()).save(any(Tag.class));
	}
}
