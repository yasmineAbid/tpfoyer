package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FoyerServiceImplTest {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    private Foyer foyer1;
    private Foyer foyer2;

    @BeforeEach
    void setUp() {
        foyer1 = new Foyer();
        foyer1.setIdFoyer(1L);
        foyer1.setNomFoyer("Foyer Alpha");
        foyer1.setCapaciteFoyer(200);

        foyer2 = new Foyer();
        foyer2.setIdFoyer(2L);
        foyer2.setNomFoyer("Foyer Beta");
        foyer2.setCapaciteFoyer(300);
    }

    @Test
    void testRetrieveAllFoyers() {
        when(foyerRepository.findAll()).thenReturn(Arrays.asList(foyer1, foyer2));

        List<Foyer> foyers = foyerService.retrieveAllFoyers();

        assertNotNull(foyers);
        assertEquals(2, foyers.size());
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveFoyer() {
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer1));

        Foyer f = foyerService.retrieveFoyer(1L);

        assertNotNull(f);
        assertEquals("Foyer Alpha", f.getNomFoyer());
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void testAddFoyer() {
        when(foyerRepository.save(foyer1)).thenReturn(foyer1);

        Foyer saved = foyerService.addFoyer(foyer1);

        assertNotNull(saved);
        assertEquals("Foyer Alpha", saved.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer1);
    }

    @Test
    void testModifyFoyer() {
        when(foyerRepository.save(foyer2)).thenReturn(foyer2);

        Foyer updated = foyerService.modifyFoyer(foyer2);

        assertNotNull(updated);
        assertEquals("Foyer Beta", updated.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer2);
    }

    @Test
    void testRemoveFoyer() {
        doNothing().when(foyerRepository).deleteById(1L);

        foyerService.removeFoyer(1L);

        verify(foyerRepository, times(1)).deleteById(1L);
    }
}
