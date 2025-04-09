package fr.afpa.harmonia.EcoleMusique_API.repositories;

import fr.afpa.harmonia.EcoleMusique_API.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Méthode pour rechercher un utilisateur par son username.
     *
     * @param username Le nom d'utilisateur à rechercher.
     * @return Un Optional contenant l'utilisateur si trouvé, sinon vide.
     */
    Optional<User> findByUsername(String username);
}
