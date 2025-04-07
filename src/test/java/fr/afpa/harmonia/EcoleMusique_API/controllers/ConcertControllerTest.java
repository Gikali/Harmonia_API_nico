package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Concert;
import fr.afpa.harmonia.EcoleMusique_API.repositories.ConcertRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests unitaires du contrôleur de concerts utilisant des mocks pour simuler la couche repository.
 * <p>
 * Cette classe utilise l'annotation {@code @WebMvcTest} pour ne charger que les composants web liés au
 * {@link ConcertController} et simuler le comportement du {@link ConcertRepository} via l'annotation {@code @MockBean}.
 * </p>
 */
@WebMvcTest(controllers = ConcertController.class)
class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConcertRepository concertRepository;

    /**
     * Teste l'ajout d'un concert via l'API REST.
     * <p>
     * Le test simule la sauvegarde d'un concert et vérifie que la réponse JSON contient un identifiant généré
     * ainsi que le nom du concert.
     * </p>
     *
     * @throws Exception en cas d'erreur lors de l'exécution du test
     */
    @Test
    void testAddConcert() throws Exception {
        // Simulation de la sauvegarde d'un concert
        Concert savedConcert = new Concert();
        savedConcert.setIdentifiantConcert(1);
        savedConcert.setNomConcert("NewConcert");

        when(concertRepository.save(any(Concert.class))).thenReturn(savedConcert);

        String jsonConcert = "{\"nomConcert\":\"NewConcert\"}";

        mockMvc.perform(post("/concert/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConcert))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identifiantConcert", notNullValue()))
                .andExpect(jsonPath("$.nomConcert", is("NewConcert")));
    }

    /**
     * Teste la mise à jour d'un concert via l'API REST.
     * <p>
     * Le test simule la récupération d'un concert existant puis sa mise à jour avec un nouveau nom.
     * Il vérifie ensuite que la réponse JSON contient l'identifiant du concert et le nouveau nom.
     * </p>
     *
     * @throws Exception en cas d'erreur lors de l'exécution du test
     */
    @Test
    void testUpdateConcert() throws Exception {
        // Simulation de la récupération et de la mise à jour d'un concert existant
        Concert concertOriginal = new Concert();
        concertOriginal.setIdentifiantConcert(1);
        concertOriginal.setNomConcert("Concert Original");

        Concert updatedConcert = new Concert();
        updatedConcert.setIdentifiantConcert(1);
        updatedConcert.setNomConcert("Concert Updated");

        when(concertRepository.findById(1L)).thenReturn(Optional.of(concertOriginal));
        when(concertRepository.save(any(Concert.class))).thenReturn(updatedConcert);

        String jsonUpdate = "{\"nomConcert\":\"Concert Updated\"}";

        mockMvc.perform(put("/concert/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identifiantConcert", is(1)))
                .andExpect(jsonPath("$.nomConcert", is("Concert Updated")));
    }

    /**
     * Teste la suppression d'un concert via l'API REST.
     * <p>
     * Le test simule la récupération d'un concert à supprimer et vérifie que l'endpoint de suppression
     * retourne un statut HTTP OK.
     * </p>
     *
     * @throws Exception en cas d'erreur lors de l'exécution du test
     */
    @Test
    void testDeleteConcert() throws Exception {
        // Simulation de la récupération d'un concert à supprimer
        Concert concert = new Concert();
        concert.setIdentifiantConcert(1);
        concert.setNomConcert("Concert to Delete");

        when(concertRepository.findById(1L)).thenReturn(Optional.of(concert));

        // On suppose que l'endpoint DELETE renvoie simplement un statut OK
        mockMvc.perform(delete("/concert/delete/1"))
                .andExpect(status().isOk());
    }
}
