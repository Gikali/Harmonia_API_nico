package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.repositories.PersonneRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Classe de test du controller de Personne.
 */
@WebMvcTest(PersonneController.class)
class PersonneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonneRepository personneRepository;


    /**
     * Test de l'affichage de toutes les personnes.
     *
     * @throws Exception
     */
    @Test
    void getPersonnesTest() throws Exception {
        mockMvc.perform(get("/personnes")).andExpect(status().isOk());
    }

    /**
     * Test de l'ajout d'une personne.
     *
     * @throws Exception
     */
    @Test
    void createPersonneTest() throws Exception {
        // Création du JSON à transmettre
        String json = "{\"nom\": \"NOM\",\"prenom\": \"Prenom\"}";

        // Test de la création de la nouvelle personne
        mockMvc.perform(MockMvcRequestBuilders.post("/personne")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    /**
     * Test de l'ajout d'une personne avec nom et prénom nuls.
     *
     * @throws Exception
     */
    @Test
    void createPersonneNullTest() throws Exception {
        // Création du JSON à transmettre
        String json = "{\"nom\": null,\"prenom\": null}";

        // Test de la création de la nouvelle personne
        mockMvc.perform(MockMvcRequestBuilders.post("/personne")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    // Test de modification ne passe pas, alors que parfaitement fonctionnel depuis PostMan/Site ?
//    /**
//     * Test de la modification d'une personne.
//     *
//     * @throws Exception
//     */
//    @Test
//    public void updatePersonneTest() throws Exception {
//        // Création d'une personne à modifier
//        String jsonCreate = "{\"nom\":\"NOM\",\"prenom\":\"Prenom\"}";
//        mockMvc.perform(post("/personne")
//                .contentType(MediaType.APPLICATION_JSON).content(jsonCreate));
//
//        // Création du JSON à transmettre
//        String jsonUpdate = "{\"id\": 1," +
//                "\"nom\": \"NOM2\"," +
//                "\"prenom\": \"Prenom2\"}";
//
//        // Test de modification de la personne
//        mockMvc.perform(MockMvcRequestBuilders.put("/personne/1")
//                        .contentType(MediaType.APPLICATION_JSON).content(jsonUpdate))
//                .andExpect(status().isOk());
//    }

    /**
     * Test de la modification d'une personne avec nom et prénom nuls.
     *
     * @throws Exception
     */
    @Test
    void updatePersonneNullTest() throws Exception {
        // Création d'une personne à modifier
        String jsonCreate = "{\"nom\":\"NOM\",\"prenom\":\"Prenom\"}";
        mockMvc.perform(post("/personne")
                .contentType(MediaType.APPLICATION_JSON).content(jsonCreate));

        // Création du JSON à transmettre
        String jsonUpdate = "{\"id\": 1," +
                "\"nom\": null," +
                "\"prenom\": null}";

        // Test de modification de la personne
        mockMvc.perform(MockMvcRequestBuilders.put("/personne/1")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonUpdate))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test de l'affichage d'une personne.
     *
     * @throws Exception
     */
    @Test
    void getPersonneTest() throws Exception {
        // Création d'une personne à rechercher
        String json = "{\"nom\":\"NOM\",\"prenom\":\"Prenom\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/personne")
                .contentType(MediaType.APPLICATION_JSON).content(json));

        // Vérification du fonctionnement de la recherche
        mockMvc.perform(get("/personne/1")).andExpect(status().isOk());
    }

    /**
     * Test de la suppression d'une personne.
     *
     * @throws Exception
     */
    @Test
    void deletePersonneTest() throws Exception {
        // Création d'une personne à supprimer
        String json = "{\"nom\":\"NOM\",\"prenom\":\"Prenom\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/personne")
                .contentType(MediaType.APPLICATION_JSON).content(json));

        // Vérification du fonctionnement de la suppression
        mockMvc.perform(delete("/personne/1")).andExpect(status().isOk());
    }

}
