package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Groupe;
import fr.afpa.harmonia.EcoleMusique_API.repositories.GroupeRepository;
import org.junit.jupiter.api.BeforeEach;
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
 * Tests d'intégration pour le contrôleur de groupes.
 * <p>
 * Cette classe démarre l'ensemble du contexte de l'application avec une base de données en mémoire
 * et vérifie le parcours complet des requêtes HTTP pour l'ajout, la mise à jour et la suppression de groupes.
 * </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
class GroupeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GroupeRepository groupeRepository;

    /**
     * Méthode de préparation exécutée avant chaque test.
     * <p>
     * Elle efface tous les enregistrements de la base de données afin d'assurer un état propre pour chaque test.
     * </p>
     */
    @BeforeEach
    void setup() {
        // Nettoyage de la base avant chaque test
        groupeRepository.deleteAll();
    }

    /**
     * Teste l'ajout d'un groupe via l'API REST.
     * <p>
     * Le test envoie une requête POST pour ajouter un groupe et vérifie que la réponse JSON
     * contient un identifiant généré ainsi que le nom correct du groupe.
     * </p>
     *
     * @throws Exception en cas d'erreur lors de l'exécution du test
     */
    @Test
    void testAddGroupe() throws Exception {
        String jsonGroupe = "{\"nomGroupe\":\"NewGroupe\"}";

        mockMvc.perform(post("/groupe/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonGroupe))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identifiantGroupe", notNullValue()))
                .andExpect(jsonPath("$.nomGroupe", is("NewGroupe")));
    }

    /**
     * Teste la mise à jour d'un groupe via l'API REST.
     * <p>
     * Le test crée un groupe initial, puis envoie une requête PUT pour mettre à jour le groupe.
     * Il vérifie que la réponse JSON contient l'identifiant du groupe et le nouveau nom.
     * </p>
     *
     * @throws Exception en cas d'erreur lors de l'exécution du test
     */
    @Test
    void testUpdateGroupe() throws Exception {
        // Création d'un groupe initial
        Groupe groupe = new Groupe();
        groupe.setNomGroupe("Groupe Original");
        groupe = groupeRepository.save(groupe);

        // Préparation de la requête de mise à jour
        String updatedJson = "{\"identifiantGroupe\":" + groupe.getIdentifiantGroupe() + ", \"nomGroupe\":\"Groupe Updated\"}";
        Integer expectedId = Math.toIntExact(groupe.getIdentifiantGroupe());
        // On suppose que l'endpoint PUT /groupe/update/{id} gère la mise à jour
        mockMvc.perform(put("/groupe/update/" + groupe.getIdentifiantGroupe())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identifiantGroupe", is(expectedId)))
                .andExpect(jsonPath("$.nomGroupe", is("Groupe Updated")));
    }

    /**
     * Teste la suppression d'un groupe via l'API REST.
     * <p>
     * Le test crée un groupe, envoie une requête DELETE pour le supprimer, puis vérifie via l'endpoint
     * de récupération que le groupe n'existe plus dans la base.
     * </p>
     *
     * @throws Exception en cas d'erreur lors de l'exécution du test
     */
    @Test
    void testDeleteGroupe() throws Exception {
        // Création d'un groupe à supprimer
        Groupe groupe = new Groupe();
        groupe.setNomGroupe("Groupe to Delete");
        groupe = groupeRepository.save(groupe);

        // Suppression du groupe via l'endpoint DELETE
        mockMvc.perform(delete("/groupe/delete/" + groupe.getIdentifiantGroupe()))
                .andExpect(status().isOk());

        // Vérification que le groupe a bien été supprimé via l'endpoint GET
        mockMvc.perform(get("/groupes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}