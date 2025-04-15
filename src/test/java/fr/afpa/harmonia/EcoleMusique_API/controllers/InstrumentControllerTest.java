package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Instrument;
import fr.afpa.harmonia.EcoleMusique_API.repositories.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InstrumentController.class)
/**
 * Classe de test unitaire pour InstrumentController.
 */
public class InstrumentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private InstrumentRepository instrumentRepository;

    @Test
     void findAll() throws Exception {
        mockMvc.perform(get("/instruments")).andExpect(status().isOk());
    }


    @Test
     void addInstrument() throws Exception {
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

    @Test
    void getInstrumentById() throws Exception {
        Instrument instrument = new Instrument();
        instrument.setIdInstrument(1);
        instrument.setLibelleInstrument("Piano");

        when(instrumentRepository.findById(1)).thenReturn(Optional.of(instrument));

        mockMvc.perform(get("/instrument/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateInstrument() throws Exception {
        Instrument existingInstrument = new Instrument();
        existingInstrument.setIdInstrument(1);
        existingInstrument.setLibelleInstrument("Piano");

        Instrument updatedInstrument = new Instrument();
        updatedInstrument.setIdInstrument(1);
        updatedInstrument.setLibelleInstrument("Violon");

        when(instrumentRepository.findById(1)).thenReturn(Optional.of(existingInstrument));
        when(instrumentRepository.save(any(Instrument.class))).thenReturn(updatedInstrument);

        String jsonUpdated = "{\"libelleInstrument\":\"Violon\"}";

        mockMvc.perform(put("/instrument/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdated))
                .andExpect(status().isOk());
    }

    @Test
    void deleteInstrument() throws Exception {
        mockMvc.perform(delete("/instrument/delete/1"))
                .andExpect(status().isOk());
    }



}



