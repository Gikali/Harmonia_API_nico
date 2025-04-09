package fr.afpa.harmonia.EcoleMusique_API.controllers;

import fr.afpa.harmonia.EcoleMusique_API.models.User;
import fr.afpa.harmonia.EcoleMusique_API.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des utilisateurs.
 * <p>
 * Ce contrôleur fournit les endpoints nécessaires pour inscrire, connecter
 * modifier, supprimer et lister les utilisateurs
 * </p>
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Endpoint pour la récupération (connexion) d'un nouvel utilisateur.
     * Vérifie que l'utilisateur existe,
     * encode le mot de passe avec Argon2,
     * puis renvoi l'utilisateur.
     *
     * @param loginRequest l'objet User reçu dans le body de la requête
     * @return l'utilisateur récupéré sans le mot de passe ou un message d'erreur
     */
    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());

        if (userOpt.isPresent()) {
            User foundUser = userOpt.get();

            // Création d'une instance d'Argon2PasswordEncoder pour vérifier le mot de passe.
            Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 4096, 3);
            if (encoder.matches(loginRequest.getPassword(), foundUser.getPassword())) {
                // Pour des raisons de sécurité, on peut supprimer le mot de passe avant de renvoyer l'objet
                foundUser.setPassword(null);
                return ResponseEntity.ok(foundUser);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Mot de passe incorrect");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Utilisateur non trouvé");
        }
    }

    /**
     * Endpoint pour l'enregistrement (inscription) d'un nouvel utilisateur.
     * Vérifie que l'utilisateur n'existe pas déjà,
     * encode le mot de passe avec Argon2,
     * puis sauvegarde l'utilisateur.
     *
     * @param newUser l'objet User reçu dans le body de la requête
     * @return l'utilisateur créé sans le mot de passe ou un message d'erreur
     */
    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody User newUser) {
        // Vérifier si l'utilisateur existe déjà par username
        Optional<User> existingUser = userRepository.findByUsername(newUser.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Le nom d'utilisateur existe déjà.");
        }

        // Encoder le mot de passe avec Argon2 : paramètres typiques (à ajuster selon les besoins)
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 4096, 3);
        String encodedPassword = encoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);

        // Sauvegarder l'utilisateur dans la base de données
        User savedUser = userRepository.save(newUser);
        // Par mesure de sécurité, on met à null le mot de passe avant de renvoyer l'objet
        savedUser.setPassword(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * Endpoint pour renvoyer la liste de tous les utilisateurs inscrits.
     * Pour des raisons de sécurité, on masque le mot de passe de chaque utilisateur.
     * URL : GET /users
     * @return La liste des utilisateurs (sans le champ password) avec HTTP 200 OK.
     */
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        // Masquer le mot de passe pour chaque utilisateur avant de renvoyer la réponse
        users.forEach(u -> u.setPassword(null));
        return ResponseEntity.ok(users);
    }

    /**
     * Endpoint pour modifier un utilisateur existant.
     * On s'attend à recevoir l'identifiant de l'utilisateur dans l'URL et
     * un objet User dans le corps de la requête contenant les champs à mettre à jour.
     * Si un nouveau mot de passe est fourni, il sera ré-encodé avec Argon2.
     */
    @PutMapping("/user/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Utilisateur non trouvé");
        }
        User existingUser = optionalUser.get();

        // Mise à jour du username et du role si fournis
        if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getRole() != null && !updatedUser.getRole().isEmpty()) {
            existingUser.setRole(updatedUser.getRole());
        }
        // S'il y a un nouveau mot de passe fourni, on ré-encode avec Argon2
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            // Paramètres typiques : saltLength=16, hashLength=32, parallelism=1, memory=4096, iterations=3
            Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 4096, 3);
            String encodedPassword = encoder.encode(updatedUser.getPassword());
            existingUser.setPassword(encodedPassword);
        }

        // Sauvegarder l'utilisateur modifié
        User savedUser = userRepository.save(existingUser);
        // Masquer le mot de passe avant de renvoyer la réponse
        savedUser.setPassword(null);

        return ResponseEntity.ok(savedUser);
    }

    /**
     * Endpoint pour supprimer un utilisateur par son identifiant.
     */
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Utilisateur non trouvé");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("Utilisateur supprimé avec succès");
    }
}
