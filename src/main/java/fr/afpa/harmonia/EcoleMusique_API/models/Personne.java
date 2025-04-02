package fr.afpa.harmonia.EcoleMusique_API.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "PERSONNE")
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idPersonne")
    private int id;

    @Column(name = "nomPersonne")
    private String nom;

    @Column(name = "prenomPersonne")
    private String prenom;
}
