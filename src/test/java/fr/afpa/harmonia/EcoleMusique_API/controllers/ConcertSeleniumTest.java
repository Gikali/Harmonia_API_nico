package fr.afpa.harmonia.EcoleMusique_API.controllers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test Selenium pour l'application de gestion des concerts.
 * <p>
 * Cette classe utilise Selenium WebDriver avec Firefox pour automatiser
 * les tests d'interface utilisateur sur l'application. Elle couvre l'ajout,
 * la mise à jour et la suppression de concerts via l'interface web.
 * </p>
 */
public class ConcertSeleniumTest {

    private static WebDriver driver;

    /**
     * Initialise le WebDriver avant l'exécution de tous les tests.
     */
    @BeforeAll
    public static void setup() {
        // Définir le chemin vers geckodriver si nécessaire
        driver = new FirefoxDriver();
    }

    /**
     * Teste l'ajout d'un concert via l'interface web.
     * <p>
     * Ce test navigue vers la page d'ajout, remplit le formulaire,
     * soumet le formulaire et vérifie que le concert apparaît dans la liste.
     * </p>
     */
    @Test
    public void testAddConcert() {
        // Accéder à la page d'ajout d'un concert
        driver.get("http://localhost:8081/concert/create");

        // Remplir le formulaire (supposons un champ avec id="nomConcert")
        WebElement inputNom = driver.findElement(By.id("nomConcert"));
        inputNom.sendKeys("NewConcert Selenium");

        // Cliquer sur le bouton de validation (par exemple, id="submit")
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();

        // Après ajout, vérifier que le concert apparaît dans la liste
        driver.get("http://localhost:8081/concerts");
        WebElement concertAjoute = driver.findElement(By.xpath("//td[text()='NewConcert Selenium']"));
        assertNotNull(concertAjoute);
    }

    /**
     * Teste la mise à jour d'un concert via l'interface web.
     * <p>
     * Ce test navigue vers la page listant les concerts, clique sur le bouton de modification
     * d'un concert existant, modifie le nom du concert, soumet le formulaire, puis vérifie
     * que le nouveau nom apparaît dans la liste.
     * </p>
     */
    @Test
    public void testUpdateConcert() {
        // Accéder à la page listant les concerts
        driver.get("http://localhost:8081/concerts");

        // Cliquer sur le bouton de modification pour un concert existant
        // On suppose ici que le bouton a une classe de la forme "update-<idConcert>"
        WebElement updateButton = driver.findElement(By.cssSelector(".update-3"));
        updateButton.click();

        // Sur la page d'édition, modifier le nom du concert
        WebElement inputNom = driver.findElement(By.id("nomConcert"));
        inputNom.clear();
        inputNom.sendKeys("UpdatedConcert Selenium");

        // Valider la modification
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();

        // Vérifier que le nouveau nom est affiché dans la liste
        driver.get("http://localhost:8081/concerts");
        WebElement concertMisAJour = driver.findElement(By.xpath("//*[text()='UpdatedConcert Selenium']"));
        assertNotNull(concertMisAJour);
    }

    /**
     * Teste la suppression d'un concert via l'interface web.
     * <p>
     * Ce test navigue vers la page listant les concerts, clique sur le bouton de suppression
     * pour un concert, et vérifie que le concert n'est plus présent dans la liste.
     * </p>
     */
    @Test
    public void testDeleteConcert() {
        // Accéder à la page listant les concerts
        driver.get("http://localhost:8081/concerts");

        // Cliquer sur le bouton de suppression pour le concert à supprimer
        // On suppose que le bouton a une classe de la forme "delete-<idConcert>"
        WebElement deleteButton = driver.findElement(By.cssSelector(".delete-3"));
        deleteButton.click();

        // Une fois la suppression effectuée, vérifier que le concert n'est plus affiché
        // Ici, on recherche tous les éléments correspondant au concert supprimé
        List<WebElement> concertsSupprimes = driver.findElements(By.xpath("//*[text()='Concert to Delete']"));
        assertTrue(concertsSupprimes.isEmpty());
    }

    /**
     * Ferme le WebDriver après l'exécution de tous les tests.
     */
    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
