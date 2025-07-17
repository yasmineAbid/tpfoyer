package tn.esprit.tpfoyer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.service.ChambreServiceImpl;
import tn.esprit.tpfoyer.service.IChambreService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ChambreServiceImplTest {
    @Mock
    private ChambreRepository chambreRepository;
//test
    @InjectMocks
    private ChambreServiceImpl chambreService;

    private Chambre chambre1;
    private Chambre chambre2;

    @BeforeEach
    void setUp() {
        chambre1 = new Chambre();
        chambre1.setIdChambre(1L);
        chambre1.setNumeroChambre(101L);
        chambre1.setTypeC(TypeChambre.SIMPLE);

        chambre2 = new Chambre();
        chambre2.setIdChambre(2L);
        chambre2.setNumeroChambre(102L);
        chambre2.setTypeC(TypeChambre.DOUBLE);
    }

    @Test
    void testRetrieveAllChambres() {
        List<Chambre> chambres = Arrays.asList(chambre1, chambre2);
        when(chambreRepository.findAll()).thenReturn(chambres);

        List<Chambre> result = chambreService.retrieveAllChambres();

        assertEquals(2, result.size());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void testAddChambre() {
        when(chambreRepository.save(chambre1)).thenReturn(chambre1);

        Chambre saved = chambreService.addChambre(chambre1);

        assertNotNull(saved);
        assertEquals(101L, saved.getNumeroChambre());
        verify(chambreRepository, times(1)).save(chambre1);
    }

    @Test
    void testRetrieveChambre() {
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre1));

        Chambre c = chambreService.retrieveChambre(1L);

        assertNotNull(c);
        assertEquals(101L, c.getNumeroChambre());
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    void testModifyChambre() {
        when(chambreRepository.save(chambre2)).thenReturn(chambre2);

        Chambre updated = chambreService.modifyChambre(chambre2);

        assertNotNull(updated);
        assertEquals(TypeChambre.DOUBLE, updated.getTypeC());
        verify(chambreRepository, times(1)).save(chambre2);
    }

    @Test
    void testRemoveChambre() {
        doNothing().when(chambreRepository).deleteById(1L);

        chambreService.removeChambre(1L);

        verify(chambreRepository, times(1)).deleteById(1L);
    }
}
