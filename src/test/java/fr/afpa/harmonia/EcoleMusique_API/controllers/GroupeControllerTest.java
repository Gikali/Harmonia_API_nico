package fr.afpa.harmonia.EcoleMusique_API.controllers;


import fr.afpa.harmonia.EcoleMusique_API.service.GroupeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GroupeController.class)
class GroupeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GroupeService groupeService;

    @Test
    void getAllGroupesTest() throws Exception {
        mockMvc.perform(get("/groupes")).andExpect(status().isOk());
    }

}
