package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Groupe;
import fr.afpa.harmonia.EcoleMusique_API.service.GroupeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class GroupeController {
    @Autowired
    private GroupeService groupeService;

    @PostMapping("/groupe/create")
    public Groupe createGroupe(@RequestBody Groupe groupe) {
        return groupeService.saveGroupe(groupe);
    }

    @GetMapping("/groupes")
    public Iterable<Groupe> getAllGroupes() {
        return groupeService.getAllGroupes();
    }

    @GetMapping("/groupe/{id}")
    public Groupe getGroupe(@PathVariable("id") int id) {
        Optional<Groupe> groupe = groupeService.getGroupeById(id);
        return groupe.orElse(null);
    }

    @PutMapping("/groupe/update/{id}")
    public Groupe updateGroupe(@PathVariable("id") int id, @RequestBody Groupe groupe) {
        Optional<Groupe> e = groupeService.getGroupeById(id);
        if (e.isPresent()) {
            Groupe current = e.get();

            String nomGroupe = groupe.getNomGroupe();
            //if(nomGroupe.equals(current.getNomGroupe())) {}
            if (nomGroupe != null) {
                current.setNomGroupe(nomGroupe);
            }
            groupeService.saveGroupe(current);
            return current;
        } else {
            return null;
        }
    }

    @DeleteMapping("/groupe/delete/{id}")
    public void deleteGroupe(@PathVariable("id") int id) {
        groupeService.deleteGroupe(id);
    }

}
