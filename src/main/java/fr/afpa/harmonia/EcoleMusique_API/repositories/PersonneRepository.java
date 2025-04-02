package fr.afpa.harmonia.EcoleMusique_API.repositories;

import fr.afpa.harmonia.EcoleMusique_API.models.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour le bean Personne
 */
@Repository
public interface PersonneRepository extends JpaRepository<Personne, Integer> {
}
