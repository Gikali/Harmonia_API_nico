package fr.afpa.harmonia.EcoleMusique_API.models;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="groupe")

public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idGroupe")
    private int idGroupe;

    @Column(name="nomGroupe")
    private String nomGroupe;

}
