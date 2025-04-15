package fr.afpa.harmonia.EcoleMusique_API.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.afpa.harmonia.EcoleMusique_API.models.Instrument;
import fr.afpa.harmonia.EcoleMusique_API.repositories.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InstrumentController.class)
/**
 * Classe de test unitaire pour InstrumentController.
 */
public class InstrumentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private InstrumentRepository instrumentRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/instrument")).andExpect(status().isOk());
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

    @Test
    void shouldReturnListOfInstrumentsAsJson() throws Exception {
        // Création des instruments
        Instrument existingInstrument = new Instrument();
        existingInstrument.setIdInstrument(1);
        existingInstrument.setLibelleInstrument("Piano");

        Instrument updatedInstrument = new Instrument();
        updatedInstrument.setIdInstrument(2);
        updatedInstrument.setLibelleInstrument("Violon");

        // Simulation du service
        List<Instrument> instruments = List.of(existingInstrument, updatedInstrument);
        when(instrumentRepository.findAll()).thenReturn(instruments);

        // Test de l'API REST
        mockMvc.perform(get("/instrument"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].idInstrument").value(1))
                .andExpect(jsonPath("$[0].libelleInstrument").value("Piano"))
                .andExpect(jsonPath("$[1].idInstrument").value(2))
                .andExpect(jsonPath("$[1].libelleInstrument").value("Violon"));

    }
/*
    @Test
    void shouldCreateNewInstrumentViaJson() throws Exception {
        String jsonInstrument = "{\"libelleInstrument\":\"newInstrument\"}"; // ❌ pas de idInstrument

        mockMvc.perform(post("/instrument/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInstrument))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idInstrument").value(1))
                .andExpect(jsonPath("$[0].libelleInstrument").value("newInstrument"));

    } */

}