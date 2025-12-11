package org.example.envahissementarmorique.model.character.base;

import org.example.envahissementarmorique.model.item.Food;
import org.example.envahissementarmorique.model.item.Foods;
import org.example.envahissementarmorique.model.item.Freshness;
import org.example.envahissementarmorique.model.item.Potion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link GameCharacter}.
 * <p>
 * Cette classe vérifie le bon fonctionnement des différentes méthodes
 * et comportements d'un personnage de jeu, incluant :
 * <ul>
 *     <li>La gestion de la santé et du soin.</li>
 *     <li>La détection de la mort et de la vie.</li>
 *     <li>Les combats et l'application de dégâts.</li>
 *     <li>La consommation de nourriture et de potions.</li>
 *     <li>Les getters et setters des attributs.</li>
 * </ul>
 * </p>
 * Chaque méthode de test est annotée avec {@link Test} et possède une description
 * via {@link DisplayName} pour une meilleure lisibilité dans les rapports de test.
 *
 * @author Envahissement
 * @version 1.0
 */
@DisplayName("Tests de la classe GameCharacter")
class GameCharacterTest {

    /** Instance du personnage de test. */
    private GameCharacter character;

    /**
     * Initialisation avant chaque test.
     * <p>
     * Crée un personnage "Astérix" avec des valeurs prédéfinies pour tous les attributs.
     * </p>
     */
    @BeforeEach
    void setUp() {
        character = new GameCharacter(
                "Astérix", "M", "Gaulois",
                1.65, 35, 50, 30, 100, 100, 60, 0
        );
    }

    // ================= Tests santé =================

    /**
     * Vérifie que le personnage est correctement initialisé avec les attributs passés au constructeur.
     */
    @Test
    @DisplayName("Initialisation correcte du personnage")
    void testCharacterInitialization() {
        assertEquals("Astérix", character.getName());
        assertEquals("M", character.getGenre());
        assertEquals("Gaulois", character.getFaction());
        assertEquals(1.65, character.getHeight(), 0.01);
        assertEquals(35, character.getAge());
        assertEquals(50, character.getStrength());
        assertEquals(30, character.getEndurance());
        assertEquals(100, character.getHealth());
        assertEquals(100, character.getHunger());
        assertEquals(60, character.getBelligerence());
        assertEquals(0, character.getMagicpotion());
    }

    /**
     * Vérifie que la méthode de soin augmente correctement la santé.
     */
    @Test
    @DisplayName("Soin du personnage")
    void testToHeal() {
        character.setHealth(50);
        character.ToHeal(30);

        assertEquals(80, character.getHealth());
    }

    /**
     * Vérifie que la santé ne dépasse pas la valeur maximale lors du soin.
     */
    @Test
    @DisplayName("Soin ne dépassant pas la santé maximale")
    void testToHealNotExceedMax() {
        character.setHealth(90);
        character.ToHeal(50);

        assertEquals(100, character.getHealth());
    }

    /**
     * Vérifie que le soin avec une valeur négative ou nulle n'affecte pas la santé.
     */
    @Test
    @DisplayName("Soin avec valeur négative ou nulle")
    void testToHealNegativeAmount() {
        int initialHealth = character.getHealth();
        character.ToHeal(-10);

        assertEquals(initialHealth, character.getHealth());

        character.ToHeal(0);
        assertEquals(initialHealth, character.getHealth());
    }

    // ================= Tests mort et vie =================

    /**
     * Vérifie la détection correcte de la mort du personnage.
     */
    @Test
    @DisplayName("Détection de la mort")
    void testIsDead() {
        assertFalse(character.isDead());

        character.setHealth(0);
        assertTrue(character.isDead());

        character.setHealth(-10);
        assertTrue(character.isDead());
    }

    /**
     * Vérifie la détection correcte si le personnage est toujours en vie.
     */
    @Test
    @DisplayName("Détection de la vie")
    void testStillAlive() {
        assertTrue(character.stillAlive());

        character.setHealth(1);
        assertTrue(character.stillAlive());

        character.setHealth(0);
        assertFalse(character.stillAlive());
    }

    // ================= Tests combat =================

    /**
     * Vérifie si le personnage est détecté comme belliqueux.
     */
    @Test
    @DisplayName("Détection du caractère belliqueux")
    void testIsBelligerent() {
        assertTrue(character.isBelligerent());

        character.setBelligerence(0);
        assertFalse(character.isBelligerent());

        character.setBelligerence(-10);
        assertFalse(character.isBelligerent());
    }

    /**
     * Vérifie l'application correcte des dégâts.
     */
    @Test
    @DisplayName("Application des dégâts")
    void testTakeDamage() {
        int initialHealth = character.getHealth();
        character.takeDamage(20);

        assertEquals(initialHealth - 20, character.getHealth());
    }

    /**
     * Vérifie que le combat réduit la santé de l'adversaire.
     */
    @Test
    @DisplayName("Combat avec un adversaire vivant")
    void testFight() {
        GameCharacter opponent = new GameCharacter(
                "Romain", "M", "Roman",
                1.75, 30, 40, 25, 100, 100, 50, 0
        );

        int opponentInitialHealth = opponent.getHealth();
        character.fight(opponent);

        assertTrue(opponent.getHealth() < opponentInitialHealth);
    }

    /**
     * Vérifie que le personnage ne peut pas combattre un adversaire déjà mort.
     */
    @Test
    @DisplayName("Combat avec adversaire mort")
    void testFightDeadOpponent() {
        GameCharacter deadOpponent = new GameCharacter(
                "Mort", "M", "Roman",
                1.75, 30, 40, 25, 0, 100, 50, 0
        );

        int characterInitialHealth = character.getHealth();
        character.fight(deadOpponent);

        assertEquals(0, deadOpponent.getHealth());
        assertEquals(characterInitialHealth, character.getHealth());
    }

    // ================= Tests alimentation =================

    /**
     * Vérifie que le personnage peut manger de la nourriture fraîche.
     */
    @Test
    @DisplayName("Manger de la nourriture fraîche")
    void testToEatFreshFood() {
        character.setHunger(50);
        Food boar = new Food(Foods.BOAR, Freshness.FRESH);

        character.ToEat(boar);

        assertEquals(100, character.getHunger());
    }

    /**
     * Vérifie qu'un personnage mort ne peut pas manger.
     */
    @Test
    @DisplayName("Impossible de manger quand mort")
    void testToEatWhenDead() {
        character.setHealth(0);
        Food boar = new Food(Foods.BOAR, Freshness.FRESH);
        int initialHunger = character.getHunger();

        character.ToEat(boar);

        assertEquals(initialHunger, character.getHunger());
    }

    /**
     * Vérifie que la consommation de nourriture pourrie réduit la santé.
     */
    @Test
    @DisplayName("Perte de santé en mangeant de la nourriture pourrie")
    void testToEatRottenFood() {
        Food rottenFish = new Food(Foods.FRESH_FISH, Freshness.ROTTEN);
        int initialHealth = character.getHealth();

        character.ToEat(rottenFish);

        assertTrue(character.getHealth() < initialHealth);
    }

    // ================= Tests potions =================

    /**
     * Vérifie que boire une potion augmente correctement la force.
     */
    @Test
    @DisplayName("Boire une potion")
    void testToDrinkPotion() {
        Potion potion = new Potion(Foods.SECRET_INGREDIENT, 1);
        int initialStrength = character.getStrength();

        character.ToDrinkPotion(potion);

        assertTrue(character.getStrength() > initialStrength);
    }

    /**
     * Vérifie qu'un personnage mort ne peut pas boire de potion.
     */
    @Test
    @DisplayName("Impossible de boire une potion quand mort")
    void testToDrinkPotionWhenDead() {
        character.setHealth(0);
        Potion potion = new Potion(Foods.SECRET_INGREDIENT, 1);
        int initialStrength = character.getStrength();

        character.ToDrinkPotion(potion);

        assertEquals(initialStrength, character.getStrength());
    }

    // ================= Tests des setters =================

    /**
     * Vérifie que les setters mettent correctement à jour les attributs.
     */
    @Test
    @DisplayName("Mise à jour des attributs")
    void testSetters() {
        character.setName("Obélix");
        assertEquals("Obélix", character.getName());

        character.setFaction("Roman");
        assertEquals("Roman", character.getFaction());

        character.setStrength(100);
        assertEquals(100, character.getStrength());

        character.setEndurance(50);
        assertEquals(50, character.getEndurance());

        character.setHunger(75);
        assertEquals(75, character.getHunger());

        character.setBelligerence(80);
        assertEquals(80, character.getBelligerence());

        character.setMagicpotion(10);
        assertEquals(10, character.getMagicpotion());
    }
}
