package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Personne;
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

@WebMvcTest(PersonneController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonneRepository personneRepository;


    /**
     * Test de l'affichage de toutes les personnes
     *
     * @throws Exception
     */
    @Test
    public void getPersonnesTest() throws Exception {
        mockMvc.perform(get("/personnes")).andExpect(status().isOk());
    }

    /**
     * Test de l'ajout d'une personne
     *
     * @throws Exception
     */
    @Test
    public void createPersonneTest() throws Exception {
        // Création du JSON à transmettre
        String json = "{\"nomPersonne\": \"NOM\",\"prenomPersonne\": \"Prenom\"}";

        // Test de la création de la nouvelle personne
        mockMvc.perform(MockMvcRequestBuilders.post("/personne")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    /**
     * Test de la modification d'une personne
     *
     * @throws Exception
     */
    @Test
    public void updatePersonneTest(Personne personne) throws Exception {
        // Création d'une personne à modifier
        String jsonCreate = "{\"nomPersonne\":\"NOM\",\"prenomPersonne\":\"Prenom\"}";
        mockMvc.perform(post("/personne")
                .contentType(MediaType.APPLICATION_JSON).content(jsonCreate));

        // Création du JSON à transmettre
        String jsonUpdate = "{\"idPersonne\": 1," +
                "\"nomPersonne\": \"NOM2\"," +
                "\"prenomPersonne\": \"Prenom2\"}";

        // Test de modification de la personne
        mockMvc.perform(MockMvcRequestBuilders.put("/personne/1")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonUpdate))
                .andExpect(status().isOk());
    }

    /**
     * Test de la suppression d'une personne
     *
     * @throws Exception
     */
    public void deletePersonneTest() throws Exception {
        // Création d'une personne à supprimer
        String json = "{\"nomPersonne\":\"NOM\",\"prenomPersonne\":\"Prenom\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/personne")
                .contentType(MediaType.APPLICATION_JSON).content(json));

        // Vérification du fonctionnement de la suppression
        mockMvc.perform(delete("/personne/1")).andExpect(status().isOk());
    }

    /**
     * Test de l'affichage d'une personne
     *
     * @throws Exception
     */
    @Test
    public void getPersonneTest() throws Exception {
        // Création d'une personne à rechercher
        String json = "{\"nomPersonne\":\"NOM\",\"prenomPersonne\":\"Prenom\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/personne")
                .contentType(MediaType.APPLICATION_JSON).content(json));

        // Vérification du fonctionnement de la recherche
        mockMvc.perform(get("/personne/1")).andExpect(status().isOk());
    }

}
