package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Personne;
import fr.afpa.harmonia.EcoleMusique_API.repositories.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Cnntrolleur pour le bean Personne
 */
@RestController
public class PersonneController {

    @Autowired
    private PersonneRepository personneRepository;

    /**
     * Renvoie toutes les personnes de la BDD
     *
     * @return List - Liste des personnes
     */
    @GetMapping("/personnes")
    public List<Personne> getPersonnes() {
        return personneRepository.findAll();
    }

    @GetMapping("/personne/{id}")
    public Personne getPersonne(@PathVariable("id") int id) {
        Optional<Personne> personne = personneRepository.findById(id);
        if (personne.isPresent()) {
            return personne.get();
        }
        return null;
    }

    @PostMapping("personne")
    public void createPersonne(@RequestBody Personne personne) {
        personneRepository.save(personne);
    }

    @PutMapping("personne")
    public void updatePersonne(@RequestBody Personne personne) {
        personneRepository.save(personne);
    }

    @DeleteMapping("/personne")
    public void deletePersonne(@RequestBody Personne personne) {
        personneRepository.delete(personne);
    }

}
