package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etudiant1;
    private Etudiant etudiant2;

    @BeforeEach
    void setUp() {
        etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1L);
        etudiant1.setNomEtudiant("Dupont");
        etudiant1.setPrenomEtudiant("Jean");
        etudiant1.setCinEtudiant(12345678L);
        etudiant1.setDateNaissance(new Date());

        etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(2L);
        etudiant2.setNomEtudiant("Martin");
        etudiant2.setPrenomEtudiant("Marie");
        etudiant2.setCinEtudiant(87654321L);
        etudiant2.setDateNaissance(new Date());
    }

    @Test
    void testRetrieveAllEtudiants() {
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(etudiant1, etudiant2));

        List<Etudiant> etudiants = etudiantService.retrieveAllEtudiants();

        assertNotNull(etudiants);
        assertEquals(2, etudiants.size());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveEtudiant() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant1));

        Etudiant e = etudiantService.retrieveEtudiant(1L);

        assertNotNull(e);
        assertEquals("Dupont", e.getNomEtudiant());
        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    void testAddEtudiant() {
        when(etudiantRepository.save(etudiant1)).thenReturn(etudiant1);

        Etudiant saved = etudiantService.addEtudiant(etudiant1);

        assertNotNull(saved);
        assertEquals("Jean", saved.getPrenomEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant1);
    }

    @Test
    void testModifyEtudiant() {
        when(etudiantRepository.save(etudiant2)).thenReturn(etudiant2);

        Etudiant updated = etudiantService.modifyEtudiant(etudiant2);

        assertNotNull(updated);
        assertEquals("Marie", updated.getPrenomEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant2);
    }

    @Test
    void testRemoveEtudiant() {
        doNothing().when(etudiantRepository).deleteById(1L);

        etudiantService.removeEtudiant(1L);

        verify(etudiantRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRecupererEtudiantParCin() {
        when(etudiantRepository.findEtudiantByCinEtudiant(12345678L)).thenReturn(etudiant1);

        Etudiant e = etudiantService.recupererEtudiantParCin(12345678L);

        assertNotNull(e);
        assertEquals("Jean", e.getPrenomEtudiant());
        verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(12345678L);
    }
}
