package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Concert;
import fr.afpa.harmonia.EcoleMusique_API.repositories.ConcertRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ConcertController.class)
class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConcertRepository concertRepository;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/concerts")).andExpect(status().isOk());
    }

    @Test
    void addConcert() throws Exception {
        // Création d'un concert sans identifiant (tel qu'il est envoyé par le client)
        Concert concertToSave = new Concert();
        concertToSave.setNomConcert("NewConcert");

        // Simulation du comportement du repository :
        // lorsqu'on sauvegarde, on renvoie un concert avec identifiant généré
        Concert savedConcert = new Concert();
        savedConcert.setIdentifiantConcert(5);
        savedConcert.setNomConcert("NewConcert");

        when(concertRepository.save(any(Concert.class))).thenReturn(savedConcert);

        // JSON à envoyer dans la requête POST
        String jsonConcert = "{\"nomConcert\":\"NewConcert\"}";

        // Effectue la requête POST et vérifie le résultat
        mockMvc.perform(post("/concert/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConcert))
                .andExpect(status().isOk());
    }
}
