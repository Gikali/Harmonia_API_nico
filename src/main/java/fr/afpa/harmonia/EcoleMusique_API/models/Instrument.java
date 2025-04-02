package fr.afpa.harmonia.EcoleMusique_API.models;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name= "Instrument")
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="idInstrument")
    private int idInstrument;

    @Column(name="libelleInstrument")
    private String libelleInstrument;
}