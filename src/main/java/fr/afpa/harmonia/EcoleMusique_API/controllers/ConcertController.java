package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.Concert;
import fr.afpa.harmonia.EcoleMusique_API.repositories.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ConcertController {

    @Autowired
    private ConcertRepository concertRepository;

    @PostMapping("/concert/create")
    public Concert createConcert(@RequestBody Concert concert){
        return concertRepository.save(concert);
    }

    @GetMapping("/concerts")
    public Iterable<Concert> listConcert(){
        return concertRepository.findAll();
    }

    @GetMapping("/concert/{id}")
    public Concert getConcert(@PathVariable("id") Long id){
        Optional<Concert> concert = concertRepository.findById(id);
        return concert.orElse(null);
    }

    @PutMapping("/concert/update/{id}")
    public Concert updateConcert(@PathVariable("id") Long id, @RequestBody Concert concert){
        Optional<Concert> c = concertRepository.findById(id);
        if(c.isPresent()){
            Concert current = c.get();

            String nomConcert = concert.getNomConcert();
            if(nomConcert != null){
                current.setNomConcert(nomConcert);
            }
            concertRepository.save(current);
            return current;
        } else {
            return null;
        }
    }

    @DeleteMapping("/concert/delete/{id}")
    public void deleteConcert(@PathVariable("id") Long id){
        concertRepository.deleteById(id);
    }
}
