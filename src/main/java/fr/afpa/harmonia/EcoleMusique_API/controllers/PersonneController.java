package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Personne;
import fr.afpa.harmonia.EcoleMusique_API.repositories.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        // Si la personne existe dans la bdd, on vérifie les données et on effectue la modification
        Optional<Personne> e = personneRepository.findById(id);
        if (e.isPresent()) {
            Personne personneBDD = e.get();

            String nom = personne.getNom();
            if (nom != null) {
                personneBDD.setNom(nom);
            }
            String prenom = personne.getPrenom();
            if (prenom != null) {
                personneBDD.setPrenom(prenom);
            }
            return personneRepository.save(personneBDD);
            // Sinon la personne n'existe pas dans la bbd et on renvoie null
        } else {
            return null;
        }
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
