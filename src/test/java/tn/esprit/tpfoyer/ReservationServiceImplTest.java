package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import tn.esprit.tpfoyer.service.ReservationServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation res1;
    private Reservation res2;

    @BeforeEach
    void setUp() {
        res1 = new Reservation();
        res1.setIdReservation("R1");
        res1.setEstValide(true);
        res1.setAnneeUniversitaire(new Date());

        res2 = new Reservation();
        res2.setIdReservation("R2");
        res2.setEstValide(false);
        res2.setAnneeUniversitaire(new Date());
    }

    @Test
    void testRetrieveAllReservations() {
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(res1, res2));

        List<Reservation> reservations = reservationService.retrieveAllReservations();

        assertNotNull(reservations);
        assertEquals(2, reservations.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveReservation() {
        when(reservationRepository.findById("R1")).thenReturn(Optional.of(res1));

        Reservation r = reservationService.retrieveReservation("R1");

        assertNotNull(r);
        assertTrue(r.isEstValide());
        verify(reservationRepository, times(1)).findById("R1");
    }

    @Test
    void testAddReservation() {
        when(reservationRepository.save(res1)).thenReturn(res1);

        Reservation saved = reservationService.addReservation(res1);

        assertNotNull(saved);
        assertEquals("R1", saved.getIdReservation());
        verify(reservationRepository, times(1)).save(res1);
    }

    @Test
    void testModifyReservation() {
        when(reservationRepository.save(res2)).thenReturn(res2);

        Reservation updated = reservationService.modifyReservation(res2);

        assertNotNull(updated);
        assertEquals("R2", updated.getIdReservation());
        verify(reservationRepository, times(1)).save(res2);
    }

    @Test
    void testRemoveReservation() {
        doNothing().when(reservationRepository).deleteById("R1");

        reservationService.removeReservation("R1");

        verify(reservationRepository, times(1)).deleteById("R1");
    }

    @Test
    void testTrouverResSelonDateEtStatus() {
        Date d = new Date();
        boolean status = true;

        when(reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(d, status))
                .thenReturn(Arrays.asList(res1));

        List<Reservation> result = reservationService.trouverResSelonDateEtStatus(d, status);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).isEstValide());
        verify(reservationRepository, times(1))
                .findAllByAnneeUniversitaireBeforeAndEstValide(d, status);
    }
}
