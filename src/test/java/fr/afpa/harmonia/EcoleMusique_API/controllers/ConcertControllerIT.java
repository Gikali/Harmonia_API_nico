package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Concert;
import fr.afpa.harmonia.EcoleMusique_API.repositories.ConcertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests d'intégration pour le contrôleur de concerts.
 * <p>
 * Cette classe démarre l'ensemble du contexte de l'application avec une base de données en mémoire
 * et vérifie le parcours complet des requêtes HTTP pour l'ajout, la mise à jour et la suppression de concerts.
 * </p>
 */

@SpringBootTest
@AutoConfigureMockMvc
class ConcertControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConcertRepository concertRepository;

    /**
     * Méthode de préparation exécutée avant chaque test.
     * <p>
     * Elle efface tous les enregistrements de la base de données afin d'assurer un état propre pour chaque test.
     * </p>
     */
    @BeforeEach
    void setup() {
        // Nettoyage de la base avant chaque test
        concertRepository.deleteAll();
    }

    /**
     * Teste l'ajout d'un concert via l'API REST.
     * <p>
     * Le test envoie une requête POST pour ajouter un concert et vérifie que la réponse JSON
     * contient un identifiant généré ainsi que le nom correct du concert.
     * </p>
     *
     * @throws Exception en cas d'erreur lors de l'exécution du test
     */
    @Test
    void testAddConcert() throws Exception {
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
     * Le test crée un concert initial, puis envoie une requête PUT pour mettre à jour le concert.
     * Il vérifie que la réponse JSON contient l'identifiant du concert et le nouveau nom.
     * </p>
     *
     * @throws Exception en cas d'erreur lors de l'exécution du test
     */
    @Test
    void testUpdateConcert() throws Exception {
        // Création d'un concert initial
        Concert concert = new Concert();
        concert.setNomConcert("Concert Original");
        concert = concertRepository.save(concert);

        // Préparation de la requête de mise à jour
        String updatedJson = "{\"identifiantConcert\":" + concert.getIdentifiantConcert() + ", \"nomConcert\":\"Concert Updated\"}";
        Integer expectedId = Math.toIntExact(concert.getIdentifiantConcert());
        // On suppose que l'endpoint PUT /concert/update/{id} gère la mise à jour
        mockMvc.perform(put("/concert/update/" + concert.getIdentifiantConcert())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identifiantConcert", is(expectedId)))
                .andExpect(jsonPath("$.nomConcert", is("Concert Updated")));
    }

    /**
     * Teste la suppression d'un concert via l'API REST.
     * <p>
     * Le test crée un concert, envoie une requête DELETE pour le supprimer, puis vérifie via l'endpoint
     * de récupération que le concert n'existe plus dans la base.
     * </p>
     *
     * @throws Exception en cas d'erreur lors de l'exécution du test
     */
    @Test
    void testDeleteConcert() throws Exception {
        // Création d'un concert à supprimer
        Concert concert = new Concert();
        concert.setNomConcert("Concert to Delete");
        concert = concertRepository.save(concert);

        // Suppression du concert via l'endpoint DELETE
        mockMvc.perform(delete("/concert/delete/" + concert.getIdentifiantConcert()))
                .andExpect(status().isOk());

        // Vérification que le concert a bien été supprimé via l'endpoint GET
        mockMvc.perform(get("/concerts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
