package fr.afpa.harmonia.EcoleMusique_API.repositories;

import fr.afpa.harmonia.EcoleMusique_API.models.Groupe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupeRepository  extends CrudRepository<Groupe, Integer> {
}
