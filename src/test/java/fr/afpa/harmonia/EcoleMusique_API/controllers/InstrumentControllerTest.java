package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Instrument;
import fr.afpa.harmonia.EcoleMusique_API.repositories.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InstrumentController.class)
public class InstrumentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private InstrumentRepository instrumentRepository;

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/instruments")).andExpect(status().isOk());
    }


    @Test
    public void addInstrument() throws Exception {
        Instrument instrument = new Instrument();
        instrument.setLibelleInstrument("NewInstrument");

        Instrument savecInstrument = new Instrument();
        savecInstrument.setIdInstrument(5);
        savecInstrument.setLibelleInstrument("NewInstrument");

        when(instrumentRepository.save(any(Instrument.class))).thenReturn(savecInstrument);

        String jsonInstrument = "{\"libelle_instrument\":\"NewInstrument\"}";

        mockMvc.perform(post("/instrument/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInstrument))
                .andExpect(status().isOk());
    }
}



