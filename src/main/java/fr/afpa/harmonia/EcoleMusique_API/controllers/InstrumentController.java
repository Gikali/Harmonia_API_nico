package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Instrument;
import fr.afpa.harmonia.EcoleMusique_API.repositories.InstrumentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
/**
 * Contrôleur gérant les opérations liées aux instruments.
 */
public class InstrumentController {

    @Autowired
    private InstrumentRepository instrumentRepository;


    @PostMapping("/instrument/create")
    /**
     * Méthode automatique.
     * @return Résultat de la méthode.
     */
    public Instrument createInstrument(@RequestBody Instrument instrument) {
        return instrumentRepository.save(instrument);
    }

    @GetMapping("/instruments")
    /**
     * Méthode automatique.
     * @return Résultat de la méthode.
     */
    public Iterable<Instrument> getInstruments() {
        return instrumentRepository.findAll();
    }

    @GetMapping("/instrument/{id}")
    /**
     * Méthode automatique.
     * @return Résultat de la méthode.
     */
    public Instrument getInstrumentById(@PathVariable int id) {
        Optional<Instrument> instrument = instrumentRepository.findById(id);
        if (instrument.isPresent()) {
            return instrument.get();
        } else {
            return null;
        }
    }

    @PutMapping("/instrument/{id}")
    /**
     * Méthode automatique.
     * @return Résultat de la méthode.
     */
    public Instrument updateInstrument(@PathVariable("id") int id, @RequestBody Instrument instrument) {
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

    @PostMapping("instrument/save")
    public Instrument save(@RequestBody Instrument instrument) {
        return instrumentRepository.save(instrument); // Ajout ou modif automatique
    }


    /**
     * Méthode automatique.
     *
     * @return Résultat de la méthode.
     */
    @DeleteMapping("/instrument/delete/{id}")
    public void deleteInstrument(@PathVariable int id) {
        instrumentRepository.deleteById(id);

    }

}

