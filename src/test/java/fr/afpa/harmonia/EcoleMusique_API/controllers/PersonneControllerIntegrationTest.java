package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Personne;
import fr.afpa.harmonia.EcoleMusique_API.repositories.PersonneRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Classe de test d'intégration pour le controller de Personne.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PersonneControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonneRepository personneRepository;

    /**
     * Test du fonctionnement de l'enchainement des methodes.
     *
     * @throws Exception
     */
    @Test
    public void enchainementTest() throws Exception {

        // Test de création d'une personne
        String jsonCreate = "{\"nom\":\"NOM\",\"prenom\":\"Prenom\"}";
        mockMvc.perform(post("/personne")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonCreate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("NOM")))
                .andExpect(jsonPath("$.prenom", is("Prenom")));

        // Test de récupération de toutes les personnes
        mockMvc.perform(get("/personnes")).andExpect(status().isOk());

        // Création d'une personne à manipuler pour les tests suivants
        Personne personneTest = new Personne();
        personneTest.setNom("TEST");
        personneTest.setPrenom("Test");
        int idTest = personneRepository.save(personneTest).getId();

        // Test de récupération d'une personne spécifique
        mockMvc.perform(get("/personne/" + idTest)).andExpect(status().isOk());

        // Test de modification de la personne
        String jsonUpdate = "{\"id\": " + idTest + "," +
                "\"nom\": \"NOM2\"," +
                "\"prenom\": \"Prenom2\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/personne/" + idTest)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonUpdate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("NOM2")))
                .andExpect(jsonPath("$.prenom", is("Prenom2")));

        // Test de supression de la personne
        mockMvc.perform(delete("/personne/" + idTest)).andExpect(status().isOk());

    }
}
