package usmb.info803.profile_search.entreprise;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EntrepriseServiceTest {

    @Mock
    private EntrepriseRepository entrepriseRepository;

    @InjectMocks 
    private EntrepriseService entrepriseService;

    @Test
    void shouldListAllEntreprises() {
        // Given
        List<Entreprise> expected = Arrays.asList(
            new Entreprise("USMB"),
            new Entreprise("Google")
        );
        when(entrepriseRepository.findAll()).thenReturn(expected);

        // When
        List<Entreprise> actual = entrepriseService.entreprises();

        // Then
        assertThat(actual).isEqualTo(expected);
        verify(entrepriseRepository).findAll();
    }

    @Test
    void shouldFindEntrepriseById() {
        // Given
        Entreprise expected = new Entreprise("USMB");
        when(entrepriseRepository.findById(1L)).thenReturn(Optional.of(expected));

        // When
        Entreprise actual = entrepriseService.entreprise(1L);

        // Then
        assertThat(actual).isEqualTo(expected);
        verify(entrepriseRepository).findById(1L);
    }

    @Test
    void shouldCreateNewEntreprise() {
        // Given
        String name = "Google";
        when(entrepriseRepository.findByName(name)).thenReturn(null);
        
        // When
        boolean result = entrepriseService.create(name);

        // Then
        assertThat(result).isTrue();
        verify(entrepriseRepository).save(any(Entreprise.class));
    }

    @Test
    void shouldNotCreateDuplicateEntreprise() {
        // Given
        String name = "Google";
        when(entrepriseRepository.findByName(name))
            .thenReturn(new Entreprise(name));

        // When
        boolean result = entrepriseService.create(name);

        // Then
        assertThat(result).isFalse();
        verify(entrepriseRepository, never()).save(any());
    }
}