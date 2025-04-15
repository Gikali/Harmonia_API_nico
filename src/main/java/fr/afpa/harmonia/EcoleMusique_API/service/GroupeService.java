package fr.afpa.harmonia.EcoleMusique_API.service;

import fr.afpa.harmonia.EcoleMusique_API.models.Groupe;
import fr.afpa.harmonia.EcoleMusique_API.repositories.GroupeRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class GroupeService {

    @Autowired
    private GroupeRepository groupeRepository;

    public Optional<Groupe> getGroupeById(int id) {
        return groupeRepository.findById(id);
    }

    public Iterable<Groupe> getAllGroupes() {
        return groupeRepository.findAll();
    }

    public void deleteGroupe(int id) {
        groupeRepository.deleteById(id);
    }

    public Groupe saveGroupe(Groupe groupe) {
        Groupe savedGroupe = groupeRepository.save(groupe);
        return savedGroupe;
    }

    //Manque createGroupe ?
}
