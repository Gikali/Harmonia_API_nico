package fr.afpa.harmonia.EcoleMusique_API.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ConcertControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findById() throws Exception {
        mockMvc.perform(get("/concerts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomConcert").value(is("Tomorrowland")));
    }
}
