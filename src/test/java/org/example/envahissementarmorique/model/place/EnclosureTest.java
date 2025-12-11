package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.*;
import org.example.envahissementarmorique.model.character.base.Gaulish.Gaulois;
import org.example.envahissementarmorique.model.item.Food;
import org.example.envahissementarmorique.model.item.Foods;
import org.example.envahissementarmorique.model.item.Freshness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link Enclosure}.
 * <p>
 * Cette classe teste les règles de gestion spécifiques aux créatures dans un enclos,
 * y compris l’ajout de créatures, la capacité maximale, l’alimentation, la guérison
 * et le calme des créatures.
 * </p>
 *
 * <p>Les tests incluent notamment :</p>
 * <ul>
 *     <li>L’initialisation de l’enclos avec les bons attributs.</li>
 *     <li>Le calcul de la capacité maximale basée sur la superficie.</li>
 *     <li>L’ajout et le rejet de personnages selon les règles.</li>
 *     <li>La gestion de la capacité maximale et de l’espace disponible.</li>
 *     <li>L’alimentation de toutes les créatures avec de la nourriture disponible.</li>
 *     <li>La guérison et le soin des créatures vivantes uniquement.</li>
 *     <li>Le calme des créatures agressives et la non-modification des créatures déjà calmes.</li>
 *     <li>L’affichage des informations de l’enclos, même vide.</li>
 * </ul>
 *
 * <p>Chaque méthode de test est annotée avec {@link Test} et {@link DisplayName} pour
 * une meilleure lisibilité.</p>
 *
 * @author
 * @version 1.0
 */
@DisplayName("Tests de la classe Enclosure")
class EnclosureTest {

    private Enclosure enclosure;
    private FantasticCreature creature1;
    private FantasticCreature creature2;
    private GameCharacter gaulois;

    /**
     * Initialise un enclos et des créatures avant chaque méthode de test.
     */
    @BeforeEach
    void setUp() {
        enclosure = new Enclosure("Enclos Magique", 500f);

        creature1 = new FantasticCreature("Idéfix", "M", 0.5, 5, 15, 25, 50, 100, 30, 0);
        creature2 = new FantasticCreature("Pégase", "M", 2.0, 10, 40, 50, 80, 100, 60, 0);

        gaulois = new Gaulois("Astérix", "M", 1.65, 35, 70, 50, 100, 100, 80, 0);
    }

    /**
     * Vérifie l’initialisation de l’enclos avec les bons attributs.
     */
    @Test
    @DisplayName("Doit initialiser l’enclos avec les bons attributs")
    void testEnclosureInitialization() {
        assertEquals("Enclos Magique", enclosure.getName());
        assertEquals(500f, enclosure.getArea());
        assertNull(enclosure.getChief());
        assertEquals(0, enclosure.getNumberOfCharacters());
    }

    /**
     * Vérifie le calcul de la capacité maximale en fonction de la superficie.
     */
    @Test
    @DisplayName("Doit calculer la capacité maximale en fonction de la superficie")
    void testMaxCapacityCalculation() {
        assertEquals(50, enclosure.getMaxCapacity());
    }

    /**
     * Vérifie l’initialisation avec une capacité maximale personnalisée.
     */
    @Test
    @DisplayName("Doit initialiser avec une capacité maximale personnalisée")
    void testCustomMaxCapacity() {
        Enclosure customEnclosure = new Enclosure("Small Enclosure", 200f, 10);
        assertEquals(10, customEnclosure.getMaxCapacity());
    }

    /**
     * Vérifie l’acceptation d’une {@link FantasticCreature}.
     */
    @Test
    @DisplayName("Doit accepter une FantasticCreature")
    void testAcceptFantasticCreature() {
        boolean added = enclosure.addCharacter(creature1);

        assertTrue(added);
        assertEquals(1, enclosure.getNumberOfCharacters());
        assertTrue(enclosure.getCharacters().contains(creature1));
    }

    /**
     * Vérifie l’acceptation de plusieurs créatures.
     */
    @Test
    @DisplayName("Doit accepter plusieurs créatures")
    void testAcceptMultipleCreatures() {
        enclosure.addCharacter(creature1);
        enclosure.addCharacter(creature2);

        assertEquals(2, enclosure.getNumberOfCharacters());
    }

    /**
     * Vérifie le rejet des personnages non-créatures.
     */
    @Test
    @DisplayName("Doit rejeter les personnages non-créatures")
    void testRejectNonCreature() {
        boolean added = enclosure.addCharacter(gaulois);

        assertFalse(added);
        assertEquals(0, enclosure.getNumberOfCharacters());
    }

    /**
     * Vérifie le rejet des caractères nuls.
     */
    @Test
    @DisplayName("Doit rejeter un personnage nul")
    void testRejectNullCharacter() {
        boolean added = enclosure.addCharacter(null);

        assertFalse(added);
        assertEquals(0, enclosure.getNumberOfCharacters());
    }

    /**
     * Vérifie le rejet lorsqu’enclos plein.
     */
    @Test
    @DisplayName("Doit rejeter une créature quand l’enclos est plein")
    void testRejectWhenFull() {
        Enclosure smallEnclosure = new Enclosure("Small", 30f, 2);

        smallEnclosure.addCharacter(creature1);
        smallEnclosure.addCharacter(creature2);

        FantasticCreature creature3 = new FantasticCreature("Extra", "F", 1.0, 8, 30, 40, 60, 100, 50, 0);
        boolean added = smallEnclosure.addCharacter(creature3);

        assertFalse(added);
        assertEquals(2, smallEnclosure.getNumberOfCharacters());
    }

    /**
     * Vérifie le calcul de l’espace disponible.
     */
    @Test
    @DisplayName("Doit calculer l’espace disponible correctement")
    void testGetAvailableSpace() {
        enclosure.addCharacter(creature1);
        enclosure.addCharacter(creature2);

        assertEquals(48, enclosure.getAvailableSpace());
    }

    /**
     * Vérifie la détection d’un enclos plein.
     */
    @Test
    @DisplayName("Doit détecter quand l’enclos est plein")
    void testIsFull() {
        Enclosure tinyEnclosure = new Enclosure("Tiny", 20f, 1);

        assertFalse(tinyEnclosure.isFull());

        tinyEnclosure.addCharacter(creature1);

        assertTrue(tinyEnclosure.isFull());
    }

    /**
     * Vérifie que toutes les créatures sont nourries.
     */
    @Test
    @DisplayName("Doit nourrir toutes les créatures")
    void testFeedAllCreatures() {
        creature1.setHunger(50);
        creature2.setHunger(40);
        enclosure.addCharacter(creature1);
        enclosure.addCharacter(creature2);

        Food boar = new Food(Foods.BOAR, Freshness.FRESH);
        Food fish = new Food(Foods.FRESH_FISH, Freshness.FRESH);
        enclosure.addFood(boar);
        enclosure.addFood(fish);

        enclosure.feedAll();

        assertTrue(creature1.getHunger() > 50 || creature2.getHunger() > 40);
    }

    /**
     * Vérifie que les créatures ne sont pas nourries lorsqu’il n’y a pas de nourriture.
     */
    @Test
    @DisplayName("Doit ne pas nourrir quand il n’y a pas de nourriture")
    void testFeedWithoutFood() {
        creature1.setHunger(50);
        enclosure.addCharacter(creature1);

        enclosure.feedAll();

        assertEquals(50, creature1.getHunger());
    }

    /**
     * Vérifie que toutes les créatures sont soignées.
     */
    @Test
    @DisplayName("Doit soigner toutes les créatures")
    void testHealAllCreatures() {
        creature1.setHealth(30);
        creature2.setHealth(40);
        enclosure.addCharacter(creature1);
        enclosure.addCharacter(creature2);

        enclosure.healAll(20);

        assertEquals(50, creature1.getHealth());
        assertEquals(60, creature2.getHealth());
    }

    /**
     * Vérifie que les créatures mortes ne sont pas soignées.
     */
    @Test
    @DisplayName("Doit ne pas soigner les créatures mortes")
    void testHealSkipsDeadCreatures() {
        creature1.setHealth(0);
        creature2.setHealth(40);
        enclosure.addCharacter(creature1);
        enclosure.addCharacter(creature2);

        enclosure.healAll(20);

        assertEquals(0, creature1.getHealth());
        assertEquals(60, creature2.getHealth());
    }

    /**
     * Vérifie que les créatures agressives sont calmées.
     */
    @Test
    @DisplayName("Doit calmer les créatures agressives")
    void testCalmCreatures() {
        creature1.setBelligerence(80);
        creature2.setBelligerence(90);
        enclosure.addCharacter(creature1);
        enclosure.addCharacter(creature2);

        enclosure.calmCreatures();

        assertEquals(60, creature1.getBelligerence());
        assertEquals(70, creature2.getBelligerence());
    }

    /**
     * Vérifie que les créatures déjà calmes ne sont pas modifiées.
     */
    @Test
    @DisplayName("Doit ne pas calmer les créatures déjà calmes")
    void testCalmAlreadyCalmCreatures() {
        creature1.setBelligerence(30);
        enclosure.addCharacter(creature1);

        enclosure.calmCreatures();

        assertEquals(30, creature1.getBelligerence());
    }

    /**
     * Vérifie la mise à jour de la capacité maximale.
     */
    @Test
    @DisplayName("Doit mettre à jour la capacité maximale")
    void testSetMaxCapacity() {
        enclosure.setMaxCapacity(100);
        assertEquals(100, enclosure.getMaxCapacity());
    }

    /**
     * Vérifie l’affichage de l’enclos avec des créatures et de la nourriture.
     */
    @Test
    @DisplayName("Doit afficher les informations de l’enclos sans erreur")
    void testDisplay() {
        enclosure.addCharacter(creature1);
        Food boar = new Food(Foods.BOAR, Freshness.FRESH);
        enclosure.addFood(boar);

        assertDoesNotThrow(() -> enclosure.display());
    }

    /**
     * Vérifie l’affichage d’un enclos vide.
     */
    @Test
    @DisplayName("Doit afficher un enclos vide sans erreur")
    void testDisplayEmpty() {
        assertDoesNotThrow(() -> enclosure.display());
    }
}
