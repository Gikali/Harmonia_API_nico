package fr.afpa.harmonia.EcoleMusique_API.repositories;

import fr.afpa.harmonia.EcoleMusique_API.models.Concert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertRepository extends CrudRepository<Concert, Long> {

}
