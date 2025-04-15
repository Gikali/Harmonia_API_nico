package fr.afpa.harmonia.EcoleMusique_API.repositories;

import fr.afpa.harmonia.EcoleMusique_API.models.Personne;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour le bean Personne
 */
@Repository
public interface PersonneRepository extends CrudRepository<Personne, Integer> {
}
