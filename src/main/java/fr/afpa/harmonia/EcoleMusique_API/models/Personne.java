package fr.afpa.harmonia.EcoleMusique_API.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Bean représentant une personne
 */
@Data
@Entity
@Table(name = "PERSONNE")
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_personne")
    private int id;

    @Column(name = "nom_personne", length = 50, nullable = false)
    private String nom;

    @Column(name = "prenom_personne", length = 30, nullable = false)
    private String prenom;
}
