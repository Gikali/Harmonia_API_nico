package fr.afpa.harmonia.EcoleMusique_API.models;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

/**
 * Représente un utilisateur dans l'application.
 * <p>
 * Cette entité est mappée sur la table "user" dans la base de données.
 * L'identifiant de l'utilisateur est généré
 * automatiquement grâce à la stratégie d'identité.
 * </p>
 */
@Data
@Entity
@Table(name = "user")
public class User {

    /**
     * L'identifiant unique de l'utilisateur.
     * <p>
     * Ce champ est la clé primaire de l'entité et est généré automatiquement par la base de données.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private long identifiantUser;

    /**
     * Le pseudo de l'utilisateur.
     * <p>
     * Ce champ représente le pseudonyme de l'utilisateur et est limité à 50 caractères.
     * </p>
     */
    @Column(name = "username", length = 50, nullable = false)
    private String username;

    /**
     * Le mot de passe de l'utilisateur.
     * <p>
     * Ce champ représente le mot de passe de l'utilisateur et est limité à 50 caractères.
     * </p>
     */
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    /**
     * Le role de l'utilisateur.
     * <p>
     * Ce champ représente le role de l'utilisateur.
     * </p>
     */
    @Column(name = "role", length = 5, nullable = false)
    private String role;
}
