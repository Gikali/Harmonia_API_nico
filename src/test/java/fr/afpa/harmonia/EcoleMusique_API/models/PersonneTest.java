package fr.afpa.harmonia.EcoleMusique_API.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests unitaires pour le bean Personne
 */
public class PersonneTest {

    static Personne personne;

    /**
     * Initialisation d'une nouvelle personne pour chaque test
     */
    @BeforeEach
    void init() {
        personne = new Personne();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"AaaaaaaaaaBbbbbbbbbbCcccccccccDdddddddddEeeeeeeeeeZ"})
    void nomInvalide(String nom) {
        personne.setNom(nom);
        personne.setPrenom("notEmpty");
        assertFalse(personne.validateValues());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Dupond", "Saint-Exupery", "AaaaaaaaaaBbbbbbbbbbCcccccccccDdddddddddEeeeeeeeee"})
    void nomValide(String nom) {
        personne.setNom(nom);
        personne.setPrenom("notEmpty");
        assertTrue(personne.validateValues());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"AaaaaaaaaaBbbbbbbbbbCcccccccccZ"})
    void prenomInvalide(String prenom) {
        personne.setPrenom(prenom);
        personne.setNom("notEmpty");
        assertFalse(personne.validateValues());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Michel", "Charles-Henry", "AaaaaaaaaaBbbbbbbbbbCccccccccc"})
    void prenomValide(String prenom) {
        personne.setPrenom(prenom);
        personne.setNom("notEmpty");
        assertTrue(personne.validateValues());
    }

    @Test
    void validateValuesNullStrings() {
        assertFalse(personne.validateValues());
    }

    @Test
    void validateValuesEmptyStrings() {
        personne.setNom("");
        personne.setPrenom("");
        assertFalse(personne.validateValues());
    }

}
