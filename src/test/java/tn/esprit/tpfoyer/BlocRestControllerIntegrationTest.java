package tn.esprit.tpfoyer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.*;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlocRestControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
//test
    @Autowired
    private BlocRepository blocRepository;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/tpfoyer/bloc";
    }

    @Test
    void testAddAndRetrieveBlocIntegration() {
        // Créer un bloc
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc Integration");
        bloc.setCapaciteBloc(200);

        ResponseEntity<Bloc> postResponse = restTemplate.postForEntity(getBaseUrl() + "/add-bloc", bloc, Bloc.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
        Long idBloc = postResponse.getBody().getIdBloc();

        // Récupérer tous les blocs
        ResponseEntity<Bloc[]> getResponse = restTemplate.getForEntity(getBaseUrl() + "/retrieve-all-blocs", Bloc[].class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertTrue(getResponse.getBody().length >= 1);

        // Modifier le bloc
        bloc.setIdBloc(idBloc);
        bloc.setNomBloc("Bloc Modifié");
        HttpEntity<Bloc> requestUpdate = new HttpEntity<>(bloc);
        ResponseEntity<Bloc> updateResponse = restTemplate.exchange(getBaseUrl() + "/modify-bloc", HttpMethod.PUT, requestUpdate, Bloc.class);
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertEquals("Bloc Modifié", updateResponse.getBody().getNomBloc());

        // Supprimer le bloc
        restTemplate.delete(getBaseUrl() + "/remove-bloc/" + idBloc);

        // Vérifier qu'il est supprimé
        List<Bloc> blocsRestants = blocRepository.findAll();
        assertTrue(blocsRestants.stream().noneMatch(b -> b.getIdBloc()== (idBloc)));
    }
}
