package tn.esprit.tpfoyer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;
import java.util.Arrays;
import java.util.List;
import tn.esprit.tpfoyer.service.BlocServiceImpl;
public class BlocServiceImplTest {
    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllBlocs() {
        // Arrange
        Bloc b1 = new Bloc();
        b1.setIdBloc(1L);
        b1.setNomBloc("Bloc A");
        b1.setCapaciteBloc(100);

        Bloc b2 = new Bloc();
        b2.setIdBloc(2L);
        b2.setNomBloc("Bloc B");
        b2.setCapaciteBloc(50);

        when(blocRepository.findAll()).thenReturn(Arrays.asList(b1, b2));

        // Act
        List<Bloc> blocs = blocService.retrieveAllBlocs();

        // Assert
        assertNotNull(blocs);
        assertEquals(2, blocs.size());
        verify(blocRepository, times(1)).findAll();
    }
}
