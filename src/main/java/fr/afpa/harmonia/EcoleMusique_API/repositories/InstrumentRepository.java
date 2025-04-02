package fr.afpa.harmonia.EcoleMusique_API.repositories;

import fr.afpa.harmonia.EcoleMusique_API.models.Instrument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepository extends CrudRepository<Instrument, Integer> {
}
