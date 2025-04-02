package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Personne;
import fr.afpa.harmonia.EcoleMusique_API.repositories.PersonneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonneController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonneRepository personneRepository;

    @Test
    public void getPersonnesTest() throws Exception {
        mockMvc.perform(get("/personnes")).andExpect(status().isOk());
    }

    @Test
    public void getPersonneTest(int id) throws Exception {
        mockMvc.perform(get("/personne/" + id)).andExpect(status().isOk());
    }

//    TODO : Tests des méthodes create/update/delete une fois les modifications du controller effectuées
//    @Test
//    public void createPersonneTest(Personne personne) throws Exception {}
//
//    public void updatePersonneTest(Personne personne) throws Exception {}
//
//    public void deletePersonneTest(Personne personne) throws Exception {}

}
