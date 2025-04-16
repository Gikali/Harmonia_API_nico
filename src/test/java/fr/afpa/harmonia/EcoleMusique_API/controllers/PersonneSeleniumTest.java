package fr.afpa.harmonia.EcoleMusique_API.controllers;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test de bout en bout utilisant Selenium pour la gestion des personnes.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonneSeleniumTest {

    private static WebDriver driver;

    String nom = "SELENIUM";
    String prenom = "Selenium";
    String nomModif = "SELENIUM222";
    String prenomModif = "Selenium222";

    /**
     * Initialisation du drver Sélénium
     */
    @BeforeAll
    static void init() {
        driver = new FirefoxDriver();
    }

    /**
     * Test de la création d'une personne
     */
    @Test
    @Order(1)
    void createPersonneTest() {

        // Accès à la page de liste des personnes
        driver.get("http://localhost:8081/listePersonnes");

        // Clique sur le bouton d'ajout de personne
        WebElement btnAjouter = driver.findElement(By.className("create"));
        btnAjouter.click();

        // Saisi du nom et du prénom
        WebElement inputNom = driver.findElement(By.id("inputNom"));
        inputNom.sendKeys(nom);
        WebElement inputPrenom = driver.findElement(By.id("inputPrenom"));
        inputPrenom.sendKeys(prenom);

        // Clique sur le bouton de validation
        WebElement btnSubmit = driver.findElement(By.id("btnAjouter"));
        btnSubmit.click();

        // Retour à la page de liste des personnes et vérification de la présence de la personne ajoutée
        driver.get("http://localhost:8081/listePersonnes");
        WebElement verification = driver.findElement(By.xpath("//td[text()='" + nom + "']"));
        assertNotNull(verification);
    }

    /**
     * Test de la modification d'une personne
     */
    @Test
    @Order(2)
    void updatePersonneTest() {

        // Accès à la page de liste des personnes
        driver.get("http://localhost:8081/listePersonnes");

        // Clique du bouton de modification du dernier élément ajouté
        WebElement btnModifier = driver.findElements(By.className("update")).getLast();
        btnModifier.click();

        // Saisi des modifications
        WebElement inputNom = driver.findElement(By.id("inputNom"));
        inputNom.clear();
        inputNom.sendKeys(nomModif);
        WebElement inputPrenom = driver.findElement(By.id("inputPrenom"));
        inputPrenom.clear();
        inputPrenom.sendKeys(prenomModif);

        // Clique sur le bouton de validation
        WebElement btnSubmit = driver.findElement(By.id("btnModifier"));
        btnSubmit.click();

        // Retour à la page de liste des personnes et vérification de la présence des données modifiées
        driver.get("http://localhost:8081/listePersonnes");
        WebElement verification = driver.findElement(By.xpath("//td[text()='" + nomModif + "']"));
        assertNotNull(verification);
    }

    /**
     * Test de la suppression d'une personne
     */
    @Test
    @Order(3)
    void deletePersonneTest() {

        // Accès à la page de liste des personnes
        driver.get("http://localhost:8081/listePersonnes");

        // Clique du bouton de suppression du dernier élément ajouté
        WebElement btnModifier = driver.findElements(By.className("delete")).getLast();
        btnModifier.click();

        // Retour à la page de liste des personnes et vérification de la présence des données modifiées
        driver.get("http://localhost:8081/listePersonnes");
        List<WebElement> verification = driver.findElements(By.xpath("//td[text()='" + nomModif + "']"));
        assertTrue(verification.isEmpty());
    }

    /**
     * Fermeture du driver Selenium après les tests
     */
    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
