package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Personne;
import fr.afpa.harmonia.EcoleMusique_API.repositories.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Controlleur pour le bean Personne.
 */
@RestController
public class PersonneController {

    @Autowired
    private PersonneRepository personneRepository;

    /**
     * Renvoie toutes les personnes de la BDD.
     *
     * @return List - Liste des personnes
     */
    @GetMapping("/personnes")
    public Iterable<Personne> getPersonnes() {
        return personneRepository.findAll();
    }

    /**
     * Renvoie la Personne ayant l'identifiant assé en paramètre.
     *
     * @param id L'identifiant de la personne recherchée
     * @return Personne - La Personne si elle se trouve dans la base de données ; null sinon
     */
    @GetMapping("/personne/{id}")
    public Personne getPersonne(@PathVariable("id") int id) {
        Optional<Personne> personne = personneRepository.findById(id);
        if (personne.isPresent()) {
            return personne.get();
        }
        return null;
    }

    /**
     * Ajoute la personne en paramètre dans la base de données.
     *
     * @param personne La personne à ajouter
     * @return Personne - La personne ajoutée dans la base de données
     */
    @PostMapping("personne")
    public Personne createPersonne(@RequestBody Personne personne) {
        if (!personne.validateValues()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return personneRepository.save(personne);
    }

    /**
     * Modifie la personne en paramètre dans la base de données.
     *
     * @param id       L'identifiant de la personne à modifier
     * @param personne Personne contenant les nouvelles informations à enregistrer
     * @return Personne - La personne modifiée, ou null si elle n'est pas présente dans la base de données
     */
    @PutMapping("personne/{id}")
    public Personne updatePersonne(@PathVariable("id") int id, @RequestBody Personne personne) {

        // Tentative de récupération de la personne via son id
        Optional<Personne> p = personneRepository.findById(id);

        // Si la personne n'existe pas ou que les données saisies sont invalides on renvoie un code erreur
        if ((!personne.validateValues()) || (p.isEmpty())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

        // Sinon on effectue la modification
        Personne personneBDD = p.get();
        personneBDD.setNom(personne.getNom());
        personneBDD.setPrenom(personne.getPrenom());
        return personneRepository.save(personneBDD);
    }

    /**
     * Supprime dans la base de données la personne dont l'identifiant est passé en paramètre.
     *
     * @param id L'identifiant de la personne à supprimer
     */
    @DeleteMapping("/personne/{id}")
    public void deletePersonne(@PathVariable("id") int id) {
        personneRepository.deleteById(id);
    }

}
