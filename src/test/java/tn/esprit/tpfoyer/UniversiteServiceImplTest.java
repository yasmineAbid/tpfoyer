package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniversiteServiceImplTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private Universite u1;
    private Universite u2;

    @BeforeEach
    void setUp() {
        u1 = new Universite();
        u1.setIdUniversite(1L);
        u1.setNomUniversite("ESPRIT");
        u1.setAdresse("Ariana");

        u2 = new Universite();
        u2.setIdUniversite(2L);
        u2.setNomUniversite("ENIT");
        u2.setAdresse("Tunis");
    }

    @Test
    void testRetrieveAllUniversites() {
        when(universiteRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<Universite> universites = universiteService.retrieveAllUniversites();

        assertNotNull(universites);
        assertEquals(2, universites.size());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveUniversite() {
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(u1));

        Universite u = universiteService.retrieveUniversite(1L);

        assertNotNull(u);
        assertEquals("ESPRIT", u.getNomUniversite());
        verify(universiteRepository, times(1)).findById(1L);
    }

    @Test
    void testAddUniversite() {
        when(universiteRepository.save(u1)).thenReturn(u1);

        Universite saved = universiteService.addUniversite(u1);

        assertNotNull(saved);
        assertEquals("ESPRIT", saved.getNomUniversite());
        verify(universiteRepository, times(1)).save(u1);
    }

    @Test
    void testModifyUniversite() {
        when(universiteRepository.save(u2)).thenReturn(u2);

        Universite updated = universiteService.modifyUniversite(u2);

        assertNotNull(updated);
        assertEquals("ENIT", updated.getNomUniversite());
        verify(universiteRepository, times(1)).save(u2);
    }

    @Test
    void testRemoveUniversite() {
        doNothing().when(universiteRepository).deleteById(1L);

        universiteService.removeUniversite(1L);

        verify(universiteRepository, times(1)).deleteById(1L);
    }
}
