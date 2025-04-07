package fr.afpa.harmonia.EcoleMusique_API.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Représente un concert dans l'application.
 * <p>
 * Cette entité est mappée sur la table "concert" dans la base de données.
 * L'identifiant du concert est généré automatiquement grâce à la stratégie d'identité.
 * </p>
 */
@Data
@Entity
@Table(name = "concert")
public class Concert {

    /**
     * L'identifiant unique du concert.
     * <p>
     * Ce champ est la clé primaire de l'entité et est généré automatiquement par la base de données.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_concert")
    private long identifiantConcert;

    /**
     * Le nom du concert.
     * <p>
     * Ce champ représente le nom du concert et est limité à 50 caractères.
     * </p>
     */
    @Column(name = "nom_concert", length = 50)
    private String nomConcert;
}
