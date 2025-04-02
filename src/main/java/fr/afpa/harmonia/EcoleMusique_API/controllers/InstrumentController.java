package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Instrument;
import fr.afpa.harmonia.EcoleMusique_API.repositories.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class InstrumentController {

    @Autowired
    private  InstrumentRepository instrumentRepository;



    @PostMapping("/instrument")
    public Instrument createInstrument(@RequestBody Instrument instrument) {
        return instrumentRepository.save(instrument);
    }

    @GetMapping("/instruments")
    public Iterable<Instrument> getInstruments() {
        return instrumentRepository.findAll();
    }

    @GetMapping("/instrument/{id}")
    public Instrument getInstrumentById(@RequestParam("id") int id) {
        Optional<Instrument> instrument = instrumentRepository.findById(id);
        if (instrument.isPresent()) {
            return instrument.get();
        } else {
            return null;
        }
    }

    @PutMapping("/instrument/{id}")
    public Instrument updateInstrument(@RequestBody Instrument instrument, @PathVariable int id) {
        Optional<Instrument> e = instrumentRepository.findById(id);
        if (e.isPresent()) {
            Instrument instr = e.get();

            String libelleInstrument = instrument.getLibelleInstrument();
            if (libelleInstrument != null) {
                instr.setLibelleInstrument(libelleInstrument);
            }
            instrumentRepository.save(instr);
            return instrument;
        } else {
            return null;
        }
    }

    @DeleteMapping("/instrument/{id}")
    public void deleteInstrument(@PathVariable int id) {
        instrumentRepository.deleteById(id);
    }
}
