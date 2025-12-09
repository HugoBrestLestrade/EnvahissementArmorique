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
 * Unit tests for GameCharacter class.
 * Tests health, hunger, combat, and consumption logic.
 */
@DisplayName("GameCharacter Tests")
class GameCharacterTest {

    private GameCharacter character;

    @BeforeEach
    void setUp() {
        character = new GameCharacter(
            "Astérix", "M", "Gaulois",
            1.65, 35, 50, 30, 100, 100, 60, 0
        );
    }

    // ========== Health Tests ==========

    @Test
    @DisplayName("Should initialize character with correct attributes")
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

    @Test
    @DisplayName("Should heal character correctly")
    void testToHeal() {
        character.setHealth(50);
        character.ToHeal(30);

        assertEquals(80, character.getHealth());
    }

    @Test
    @DisplayName("Should not exceed maxHealth when healing")
    void testToHealNotExceedMax() {
        character.setHealth(90);
        character.ToHeal(50);

        assertEquals(100, character.getHealth());
    }

    @Test
    @DisplayName("Should not heal with negative or zero amount")
    void testToHealNegativeAmount() {
        int initialHealth = character.getHealth();
        character.ToHeal(-10);

        assertEquals(initialHealth, character.getHealth());

        character.ToHeal(0);
        assertEquals(initialHealth, character.getHealth());
    }

    // ========== Death Tests ==========

    @Test
    @DisplayName("Should detect when character is dead")
    void testIsDead() {
        assertFalse(character.isDead());

        character.setHealth(0);
        assertTrue(character.isDead());

        character.setHealth(-10);
        assertTrue(character.isDead());
    }

    @Test
    @DisplayName("Should detect when character is alive")
    void testStillAlive() {
        assertTrue(character.stillAlive());

        character.setHealth(1);
        assertTrue(character.stillAlive());

        character.setHealth(0);
        assertFalse(character.stillAlive());
    }

    // ========== Combat Tests ==========

    @Test
    @DisplayName("Should detect belligerent character")
    void testIsBelligerent() {
        assertTrue(character.isBelligerent());

        character.setBelligerence(0);
        assertFalse(character.isBelligerent());

        character.setBelligerence(-10);
        assertFalse(character.isBelligerent());
    }

    @Test
    @DisplayName("Should apply damage in combat")
    void testTakeDamage() {
        int initialHealth = character.getHealth();
        character.takeDamage(20);

        assertEquals(initialHealth - 20, character.getHealth());
    }

    @Test
    @DisplayName("Should fight opponent and deal damage")
    void testFight() {
        GameCharacter opponent = new GameCharacter(
            "Romain", "M", "Roman",
            1.75, 30, 40, 25, 100, 100, 50, 0
        );

        int opponentInitialHealth = opponent.getHealth();
        character.fight(opponent);

        assertTrue(opponent.getHealth() < opponentInitialHealth);
    }

    @Test
    @DisplayName("Should not fight dead opponent")
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

    // ========== Eating Tests ==========

    @Test
    @DisplayName("Should eat fresh food")
    void testToEatFreshFood() {
        character.setHunger(50);
        Food boar = new Food(Foods.BOAR, Freshness.FRESH);

        character.ToEat(boar);

        assertEquals(100, character.getHunger());
    }

    @Test
    @DisplayName("Should not eat when dead")
    void testToEatWhenDead() {
        character.setHealth(0);
        Food boar = new Food(Foods.BOAR, Freshness.FRESH);
        int initialHunger = character.getHunger();

        character.ToEat(boar);

        assertEquals(initialHunger, character.getHunger());
    }

    @Test
    @DisplayName("Should lose health eating rotten food")
    void testToEatRottenFood() {
        Food rottenFish = new Food(Foods.FRESH_FISH, Freshness.ROTTEN);
        int initialHealth = character.getHealth();

        character.ToEat(rottenFish);

        assertTrue(character.getHealth() < initialHealth);
    }

    // ========== Potion Tests ==========

    @Test
    @DisplayName("Should drink potion")
    void testToDrinkPotion() {
        Potion potion = new Potion(Foods.SECRET_INGREDIENT, 1);
        int initialStrength = character.getStrength();

        character.ToDrinkPotion(potion);

        assertTrue(character.getStrength() > initialStrength);
    }

    @Test
    @DisplayName("Should not drink potion when dead")
    void testToDrinkPotionWhenDead() {
        character.setHealth(0);
        Potion potion = new Potion(Foods.SECRET_INGREDIENT, 1);
        int initialStrength = character.getStrength();

        character.ToDrinkPotion(potion);

        assertEquals(initialStrength, character.getStrength());
    }

    // ========== Attribute Tests ==========

    @Test
    @DisplayName("Should update attributes correctly")
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

