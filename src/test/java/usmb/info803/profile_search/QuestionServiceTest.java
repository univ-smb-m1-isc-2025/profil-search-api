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

import usmb.info803.profile_search.question.Question;
import usmb.info803.profile_search.question.QuestionRepository;
import usmb.info803.profile_search.question.QuestionService;

public class QuestionServiceTest {

	@Mock
	private QuestionRepository questionRepository;

	@InjectMocks
	private QuestionService questionService;

	@BeforeEach
	void setUp() {
			MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAll() {
		List<Question> questions = Arrays.asList(new Question("Question 1"), new Question("Question 2"));
		when(questionRepository.findAll()).thenReturn(questions);

		List<Question> result = questionService.all();

		assertThat(result).isEqualTo(questions);
		verify(questionRepository, times(1)).findAll();
	}

	@Test
	void testQuestionById() {
		Question question = new Question("Sample Question");
		when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

		Question result = questionService.question(1L);

		assertThat(result).isEqualTo(question);
		verify(questionRepository, times(1)).findById(1L);
	}

	@Test
	void testQuestionByString() {
		Question question = new Question("Sample Question");
		when(questionRepository.findByQuestion("Sample Question")).thenReturn(Optional.of(question));

		Question result = questionService.question("Sample Question");

		assertThat(result).isEqualTo(question);
		verify(questionRepository, times(1)).findByQuestion("Sample Question");
	}

	@Test
	void testCreateQuestionWhenNotExists() {
		when(questionRepository.findByQuestion("New Question")).thenReturn(Optional.empty());

		boolean result = questionService.create("New Question");

		assertThat(result).isTrue();
		verify(questionRepository, times(1)).findByQuestion("New Question");
		verify(questionRepository, times(1)).save(any(Question.class));
	}

	@Test
	void testCreateQuestionWhenExists() {
		Question existingQuestion = new Question("Existing Question");
		when(questionRepository.findByQuestion("Existing Question")).thenReturn(Optional.of(existingQuestion));

		boolean result = questionService.create("Existing Question");

		assertThat(result).isFalse();
		verify(questionRepository, times(1)).findByQuestion("Existing Question");
		verify(questionRepository, never()).save(any(Question.class));
	}
}
