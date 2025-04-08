package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Concert;
import fr.afpa.harmonia.EcoleMusique_API.repositories.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des concerts.
 * <p>
 * Ce contrôleur fournit les endpoints nécessaires pour créer, lister, récupérer, mettre à jour
 * et supprimer des concerts.
 * </p>
 */
@RestController
public class ConcertController {

    @Autowired
    private ConcertRepository concertRepository;

    /**
     * Crée un nouveau concert.
     * @param concert Le concert à créer, envoyé dans le corps de la requête.
     * @return Le concert créé.
     */
    @PostMapping("/concert/create")
    public Concert createConcert(@RequestBody Concert concert) {
        return concertRepository.save(concert);
    }

    /**
     * Récupère la liste de tous les concerts.
     *
     * @return Un itérable contenant tous les concerts.
     */
    @GetMapping("/concerts")
    public Iterable<Concert> listConcert() {
        return concertRepository.findAll();
    }

    /**
     * Récupère un concert par son identifiant.
     *
     * @param id L'identifiant du concert à récupérer.
     * @return Le concert correspondant si trouvé, sinon {@code null}.
     */
    @GetMapping("/concert/{id}")
    public Concert getConcert(@PathVariable("id") Long id) {
        Optional<Concert> concert = concertRepository.findById(id);
        return concert.orElse(null);
    }

    /**
     * Met à jour un concert existant.
     * <p>
     * Seul le nom du concert est mis à jour si une nouvelle valeur est fournie.
     * </p>
     *
     * @param id      L'identifiant du concert à mettre à jour.
     * @param concert Le concert contenant les nouvelles informations.
     * @return Le concert mis à jour si trouvé, sinon {@code null}.
     */
    @PutMapping("/concert/update/{id}")
    public Concert updateConcert(@PathVariable("id") Long id, @RequestBody Concert concert) {
        Optional<Concert> c = concertRepository.findById(id);
        if (c.isPresent()) {
            Concert current = c.get();

            String nomConcert = concert.getNomConcert();
            if (nomConcert != null) {
                current.setNomConcert(nomConcert);
            }
            concertRepository.save(current);
            return current;
        } else {
            return null;
        }
    }

    /**
     * Supprime un concert par son identifiant.
     *
     * @param id L'identifiant du concert à supprimer.
     */
    @DeleteMapping("/concert/delete/{id}")
    public void deleteConcert(@PathVariable("id") Long id) {
        concertRepository.deleteById(id);
    }
}
