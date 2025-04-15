package fr.afpa.harmonia.EcoleMusique_API.models;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NonNull;


@Data
@Entity
@Table(name= "Instrument")
/**
 * Représente un instrument de musique dans le système.
 */
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="idInstrument")
    private int idInstrument;

    @Column(name="libelleInstrument")
    private String libelleInstrument;



}