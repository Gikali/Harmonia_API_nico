package fr.afpa.harmonia.EcoleMusique_API.controllers;


import fr.afpa.harmonia.EcoleMusique_API.models.Groupe;
import fr.afpa.harmonia.EcoleMusique_API.repositories.GroupeRepository;
import fr.afpa.harmonia.EcoleMusique_API.service.GroupeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests unitaires du contrôleur de groupes utilisant des mocks pour simuler la couche repository.
 * <p>
 * Cette classe utilise l'annotation {@code @WebMvcTest} pour ne charger que les composants web liés au
 * {@link GroupeController} et simuler le comportement du {@link GroupeRepository} via l'annotation {@code @MockBean}.
 * </p>
 */

@WebMvcTest(controllers = GroupeController.class)


public class GroupeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GroupeService groupeService;



    @Test
    public void getAllGroupesTest() throws Exception {
        mockMvc.perform(get("/groupes")).andExpect(status().isOk());
    }

    /**
     * Teste l'ajout d'un groupe via l'API REST.
     * <p>
     * Le test simule la sauvegarde d'un groupe et vérifie que la réponse JSON contient un identifiant généré
     * ainsi que le nom du groupe.
     * </p>
     *
     * @throws Exception en cas d'erreur lors de l'exécution du test
     */

    @Test
    void addGroupeTest() throws Exception {
        Groupe groupe = new Groupe();
        groupe.setNomGroupe("TestAjout");

        Groupe savedGroupe = new Groupe();
        savedGroupe.setIdentifiantGroupe(5);
        savedGroupe.setNomGroupe("TestAjout");

        when(groupeService.saveGroupe(any(Groupe.class))).thenReturn(savedGroupe);
        String jsonGroupe = "{\"nom_groupe\":\"TestAjout\"}";

        mockMvc.perform(post("/instrument/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonGroupe))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identifiantGroupe", notNullValue()))
                .andExpect(jsonPath("$.nomGroupe", is("TestAjout")));
    }

    /**
     * Teste la mise à jour d'un groupe via l'API REST.
     * <p>
     * Le test simule la récupération d'un groupe existant puis sa mise à jour avec un nouveau nom.
     * Il vérifie ensuite que la réponse JSON contient l'identifiant du groupe et le nouveau nom.
     * </p>
     *
     * @throws Exception en cas d'erreur lors de l'exécution du test
     */
    @Test
    void testUpdateGroupe() throws Exception {
        // Simulation de la récupération et de la mise à jour d'un groupe existant
        Groupe groupeOriginal = new Groupe();
        groupeOriginal.setIdentifiantGroupe(1);
        groupeOriginal.setNomGroupe("Groupe Original");

        Groupe updatedGroupe = new Groupe();
        updatedGroupe.setIdentifiantGroupe(1);
        updatedGroupe.setNomGroupe("Groupe Updated");

        when(groupeService.getGroupeById(1)).thenReturn(Optional.of(groupeOriginal));
        when(groupeService.saveGroupe(any(Groupe.class))).thenReturn(updatedGroupe);

        String jsonUpdate = "{\"nomGroupe\":\"Groupe Updated\"}";

        mockMvc.perform(put("/groupe/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identifiantGroupe", is(1)))
                .andExpect(jsonPath("$.nomGroupe", is("Groupe Updated")));
    }

    /**
     * Teste la suppression d'un groupe via l'API REST.
     * <p>
     * Le test simule la récupération d'un groupe à supprimer et vérifie que l'endpoint de suppression
     * retourne un statut HTTP OK.
     * </p>
     *
     * @throws Exception en cas d'erreur lors de l'exécution du test
     */
    @Test
    void testDeleteGroupe() throws Exception {
        // Simulation de la récupération d'un groupe à supprimer
        Groupe groupe = new Groupe();
        groupe.setIdentifiantGroupe(1);
        groupe.setNomGroupe("Groupe to Delete");

        when(groupeService.getGroupeById(1)).thenReturn(Optional.of(groupe));

        // On suppose que l'endpoint DELETE renvoie simplement un statut OK
        mockMvc.perform(delete("/groupe/delete/1"))
                .andExpect(status().isOk());
    }
}



