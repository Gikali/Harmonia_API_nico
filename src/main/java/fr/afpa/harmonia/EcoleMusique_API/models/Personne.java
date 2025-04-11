package fr.afpa.harmonia.EcoleMusique_API.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Bean représentant une personne.
 */
@Data
@Entity
@Table(name = "PERSONNE")
public class Personne {

    /**
     * Identifiant de la personne
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_personne")
    private int id;

    /**
     * Nom de la personne
     */
    @Column(name = "nom_personne", length = 50, nullable = false)
    private String nom;

    /**
     * Prénom de la personne
     */
    @Column(name = "prenom_personne", length = 30, nullable = false)
    private String prenom;


    /**
     * Méthode vérifiant la validité des attributs d'une personne.
     *
     * @return True si tous les attributs sont valides, False si au moins un attribut est invalide
     */
    public boolean validateValues() {
        return (this.nom != null) && (this.prenom != null)
                && (!this.nom.isEmpty()) && (!this.prenom.isEmpty())
                && (this.nom.length() <= 50) && (this.prenom.length() <= 30);
    }
}
