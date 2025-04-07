package fr.afpa.harmonia.EcoleMusique_API.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Classe de test d'intégration pour le controller de Personne
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PersonneControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test du fonctionnement de l'enchainement des methodes
     *
     * @throws Exception
     */
    public void enchainementTest() throws Exception {

        // Création d'une personne
        String jsonCreate = "{\"nom\":\"NOM\",\"prenom\":\"Prenom\"}";
        mockMvc.perform(post("/personne")
                .contentType(MediaType.APPLICATION_JSON).content(jsonCreate));

        // Récupération de toutes les personnes
        mockMvc.perform(get("/personnes")).andExpect(status().isOk());

        // Récupération d'une personne spécifique
        mockMvc.perform(get("/personne/1")).andExpect(status().isOk());

        // Modification de la personne
        String jsonUpdate = "{\"id\": 1," +
                "\"nom\": \"NOM2\"," +
                "\"prenom\": \"Prenom2\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/personne/1")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonUpdate))
                .andExpect(status().isOk());

        // Supression de la personne
        mockMvc.perform(delete("/personne/1")).andExpect(status().isOk());

    }
}
