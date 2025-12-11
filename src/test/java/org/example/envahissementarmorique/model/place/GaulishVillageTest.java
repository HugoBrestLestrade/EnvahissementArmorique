package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.*;
import org.example.envahissementarmorique.model.character.base.Gaulish.Gaulois;
import org.example.envahissementarmorique.model.character.base.Roman.Roman;
import org.example.envahissementarmorique.model.item.Potion;
import org.example.envahissementarmorique.model.item.Foods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link GaulishVillage}.
 * <p>
 * Cette classe teste les règles spécifiques aux Gaulois ainsi que la gestion des potions
 * dans un village gaulois.
 * </p>
 *
 * <p>Les tests incluent notamment :</p>
 * <ul>
 *     <li>L’initialisation du village avec les bons attributs et niveaux de résistance et de morale.</li>
 *     <li>L’ajout et le rejet de personnages selon qu’ils sont Gaulois, Romains ou créatures fantastiques.</li>
 *     <li>La gestion des niveaux de résistance et de morale (augmentation, diminution, limites).</li>
 *     <li>La gestion des potions : ajout, récupération de la liste et comptage.</li>
 *     <li>Le comptage et la récupération des Gaulois dans le village.</li>
 *     <li>L’affichage des informations du village sans erreur.</li>
 * </ul>
 *
 * <p>Chaque méthode de test est annotée avec {@link Test} et {@link DisplayName} pour
 * une meilleure lisibilité.</p>
 *
 * @author
 * @version 1.0
 */
@DisplayName("Tests de la classe GaulishVillage")
class GaulishVillageTest {

    private GaulishVillage village;
    private GameCharacter gaulois;
    private GameCharacter roman;

    /**
     * Initialise un village et des personnages avant chaque méthode de test.
     */
    @BeforeEach
    void setUp() {
        ClanLeader chief = new ClanLeader("Abraracourcix", "M", 50, null);
        village = new GaulishVillage("Village des Irréductibles", 2000f, chief);

        gaulois = new Gaulois("Astérix", "M", 1.65, 35, 70, 50, 100, 100, 80, 0);

        roman = new Roman("Centurion", "M", 1.75, 30, 50, 40, 100, 100, 60, 0);
    }

    /**
     * Vérifie l’initialisation du village avec les bons attributs.
     */
    @Test
    @DisplayName("Doit initialiser le village gaulois avec les bons attributs")
    void testVillageInitialization() {
        assertEquals("Village des Irréductibles", village.getName());
        assertEquals(2000f, village.getArea());
        assertNotNull(village.getChief());
        assertEquals(100, village.getResistanceLevel());
        assertEquals(90, village.getMoraleLevel());
        assertEquals(0, village.getPotionCount());
    }

    /**
     * Vérifie l’initialisation personnalisée avec niveaux de résistance et de morale.
     */
    @Test
    @DisplayName("Doit initialiser avec résistance et morale personnalisées")
    void testVillageCustomInitialization() {
        ClanLeader chief = new ClanLeader("Chief", "M", 45, null);
        GaulishVillage customVillage = new GaulishVillage("Custom", 1000f, chief, 80, 75);

        assertEquals(80, customVillage.getResistanceLevel());
        assertEquals(75, customVillage.getMoraleLevel());
    }

    /**
     * Vérifie l’acceptation d’un personnage Gaulois.
     */
    @Test
    @DisplayName("Doit accepter un personnage Gaulois")
    void testAcceptGaulois() {
        boolean added = village.addCharacter(gaulois);

        assertTrue(added);
        assertEquals(1, village.getNumberOfCharacters());
        assertTrue(village.getCharacters().contains(gaulois));
    }

    /**
     * Vérifie le rejet d’un personnage Romain.
     */
    @Test
    @DisplayName("Doit rejeter un personnage Romain")
    void testRejectRoman() {
        boolean added = village.addCharacter(roman);

        assertFalse(added);
        assertEquals(0, village.getNumberOfCharacters());
    }

    /**
     * Vérifie l’acceptation d’une créature fantastique.
     */
    @Test
    @DisplayName("Doit accepter une créature fantastique")
    void testAcceptFantasticCreature() {
        FantasticCreature creature = new FantasticCreature("Idéfix", "M", 0.5, 5, 10, 20, 50, 100, 30, 0);
        boolean added = village.addCharacter(creature);

        assertTrue(added);
        assertTrue(village.getCharacters().contains(creature));
    }

    /**
     * Vérifie l’augmentation du niveau de résistance.
     */
    @Test
    @DisplayName("Doit augmenter le niveau de résistance")
    void testIncreaseResistance() {
        village.setResistanceLevel(70);
        village.increaseResistance(20);

        assertEquals(90, village.getResistanceLevel());
    }

    /**
     * Vérifie que le niveau de résistance ne dépasse pas le maximum.
     */
    @Test
    @DisplayName("Doit limiter le niveau de résistance au maximum")
    void testResistanceMaxCap() {
        village.setResistanceLevel(95);
        village.increaseResistance(20);

        assertEquals(100, village.getResistanceLevel());
    }

    /**
     * Vérifie la diminution du niveau de résistance.
     */
    @Test
    @DisplayName("Doit diminuer le niveau de résistance")
    void testDecreaseResistance() {
        village.decreaseResistance(30);

        assertEquals(70, village.getResistanceLevel());
    }

    /**
     * Vérifie que le niveau de résistance ne descend pas en dessous du minimum.
     */
    @Test
    @DisplayName("Doit limiter le niveau de résistance au minimum")
    void testResistanceMinCap() {
        village.setResistanceLevel(10);
        village.decreaseResistance(20);

        assertEquals(0, village.getResistanceLevel());
    }

    /**
     * Vérifie l’augmentation du niveau de morale.
     */
    @Test
    @DisplayName("Doit augmenter le niveau de morale")
    void testIncreaseMorale() {
        village.setMoraleLevel(60);
        village.increaseMorale(25);

        assertEquals(85, village.getMoraleLevel());
    }

    /**
     * Vérifie que le niveau de morale ne dépasse pas le maximum.
     */
    @Test
    @DisplayName("Doit limiter le niveau de morale au maximum")
    void testMoraleMaxCap() {
        village.setMoraleLevel(95);
        village.increaseMorale(20);

        assertEquals(100, village.getMoraleLevel());
    }

    /**
     * Vérifie la diminution du niveau de morale.
     */
    @Test
    @DisplayName("Doit diminuer le niveau de morale")
    void testDecreaseMorale() {
        village.decreaseMorale(20);

        assertEquals(70, village.getMoraleLevel());
    }

    /**
     * Vérifie que le niveau de morale ne descend pas en dessous du minimum.
     */
    @Test
    @DisplayName("Doit limiter le niveau de morale au minimum")
    void testMoraleMinCap() {
        village.setMoraleLevel(5);
        village.decreaseMorale(10);

        assertEquals(0, village.getMoraleLevel());
    }

    /**
     * Vérifie l’ajout d’une potion au village.
     */
    @Test
    @DisplayName("Doit ajouter une potion au village")
    void testAddPotion() {
        Potion potion = new Potion(Foods.SECRET_INGREDIENT, 10);
        village.addPotion(potion);

        assertEquals(1, village.getPotionCount());
    }

    /**
     * Vérifie l’ajout de plusieurs potions.
     */
    @Test
    @DisplayName("Doit ajouter plusieurs potions")
    void testAddMultiplePotions() {
        Potion potion1 = new Potion(Foods.SECRET_INGREDIENT, 10);
        Potion potion2 = new Potion(Foods.SECRET_INGREDIENT, 5);

        village.addPotion(potion1);
        village.addPotion(potion2);

        assertEquals(2, village.getPotionCount());
    }

    /**
     * Vérifie la récupération de la liste des potions.
     */
    @Test
    @DisplayName("Doit récupérer la liste des potions")
    void testGetPotions() {
        Potion potion = new Potion(Foods.SECRET_INGREDIENT, 10);
        village.addPotion(potion);

        assertNotNull(village.getPotions());
        assertEquals(1, village.getPotions().size());
        assertTrue(village.getPotions().contains(potion));
    }

    /**
     * Vérifie l’ajout de plusieurs Gaulois et le comptage correct.
     */
    @Test
    @DisplayName("Doit accepter plusieurs Gaulois")
    void testAcceptMultipleGaulois() {
        GameCharacter gaulois1 = new Gaulois("Astérix", "M", 1.65, 35, 70, 50, 100, 100, 80, 0);
        GameCharacter gaulois2 = new Gaulois("Obélix", "M", 1.85, 35, 90, 60, 120, 100, 70, 0);

        village.addCharacter(gaulois1);
        village.addCharacter(gaulois2);

        assertEquals(2, village.getNumberOfCharacters());
        assertTrue(village.getCharacters().contains(gaulois1));
        assertTrue(village.getCharacters().contains(gaulois2));
    }

    /**
     * Vérifie que les niveaux de résistance et de morale sont maintenus.
     */
    @Test
    @DisplayName("Doit maintenir les niveaux de résistance et de morale")
    void testMaintainLevels() {
        village.setMoraleLevel(90);
        village.setResistanceLevel(95);

        assertEquals(90, village.getMoraleLevel());
        assertEquals(95, village.getResistanceLevel());
    }

    /**
     * Vérifie la récupération de la liste des Gaulois.
     */
    @Test
    @DisplayName("Doit récupérer la liste des Gaulois")
    void testGetGaulois() {
        GameCharacter gaulois1 = new Gaulois("Astérix", "M", 1.65, 35, 70, 50, 100, 100, 80, 0);
        GameCharacter gaulois2 = new Gaulois("Obélix", "M", 1.85, 35, 90, 60, 120, 100, 70, 0);

        village.addCharacter(gaulois1);
        village.addCharacter(gaulois2);

        assertEquals(2, village.getGaulois().size());
    }

    /**
     * Vérifie l’affichage du village sans générer d’erreur.
     */
    @Test
    @DisplayName("Doit afficher les informations du village sans erreur")
    void testDisplay() {
        village.addCharacter(gaulois);
        assertDoesNotThrow(() -> village.display());
    }
}
