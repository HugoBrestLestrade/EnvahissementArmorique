package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.*;
import org.example.envahissementarmorique.model.item.Food;
import org.example.envahissementarmorique.model.item.Foods;
import org.example.envahissementarmorique.model.item.Freshness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Enclosure class.
 * Tests creature-specific management rules.
 */
@DisplayName("Enclosure Tests")
class EnclosureTest {

    private Enclosure enclosure;
    private FantasticCreature creature1;
    private FantasticCreature creature2;
    private GameCharacter gaulois;

    @BeforeEach
    void setUp() {
        enclosure = new Enclosure("Enclos Magique", 500f);

        creature1 = new FantasticCreature("Idéfix", "M", 0.5, 5, 15, 25, 50, 100, 30, 0);
        creature2 = new FantasticCreature("Pégase", "M", 2.0, 10, 40, 50, 80, 100, 60, 0);

        gaulois = new Gaulois("Astérix", "M", 1.65, 35, 70, 50, 100, 100, 80, 0);
    }

    // ========== Initialization Tests ==========

    @Test
    @DisplayName("Should initialize enclosure with correct attributes")
    void testEnclosureInitialization() {
        assertEquals("Enclos Magique", enclosure.getName());
        assertEquals(500f, enclosure.getArea());
        assertNull(enclosure.getChief());
        assertEquals(0, enclosure.getNumberOfCharacters());
    }

    @Test
    @DisplayName("Should calculate max capacity based on area")
    void testMaxCapacityCalculation() {
        // 1 creature per 10m²
        assertEquals(50, enclosure.getMaxCapacity());
    }

    @Test
    @DisplayName("Should initialize with custom max capacity")
    void testCustomMaxCapacity() {
        Enclosure customEnclosure = new Enclosure("Small Enclosure", 200f, 10);
        assertEquals(10, customEnclosure.getMaxCapacity());
    }

    // ========== Character Addition Rules Tests ==========

    @Test
    @DisplayName("Should accept FantasticCreature")
    void testAcceptFantasticCreature() {
        boolean added = enclosure.addCharacter(creature1);

        assertTrue(added);
        assertEquals(1, enclosure.getNumberOfCharacters());
        assertTrue(enclosure.getCharacters().contains(creature1));
    }

    @Test
    @DisplayName("Should accept multiple creatures")
    void testAcceptMultipleCreatures() {
        enclosure.addCharacter(creature1);
        enclosure.addCharacter(creature2);

        assertEquals(2, enclosure.getNumberOfCharacters());
    }

    @Test
    @DisplayName("Should reject non-FantasticCreature")
    void testRejectNonCreature() {
        boolean added = enclosure.addCharacter(gaulois);

        assertFalse(added);
        assertEquals(0, enclosure.getNumberOfCharacters());
    }

    @Test
    @DisplayName("Should reject null character")
    void testRejectNullCharacter() {
        boolean added = enclosure.addCharacter(null);

        assertFalse(added);
        assertEquals(0, enclosure.getNumberOfCharacters());
    }

    // ========== Capacity Tests ==========

    @Test
    @DisplayName("Should reject creature when enclosure is full")
    void testRejectWhenFull() {
        Enclosure smallEnclosure = new Enclosure("Small", 30f, 2);

        smallEnclosure.addCharacter(creature1);
        smallEnclosure.addCharacter(creature2);

        FantasticCreature creature3 = new FantasticCreature("Extra", "F", 1.0, 8, 30, 40, 60, 100, 50, 0);
        boolean added = smallEnclosure.addCharacter(creature3);

        assertFalse(added);
        assertEquals(2, smallEnclosure.getNumberOfCharacters());
    }

    @Test
    @DisplayName("Should calculate available space correctly")
    void testGetAvailableSpace() {
        enclosure.addCharacter(creature1);
        enclosure.addCharacter(creature2);

        assertEquals(48, enclosure.getAvailableSpace());
    }

    @Test
    @DisplayName("Should detect when enclosure is full")
    void testIsFull() {
        Enclosure tinyEnclosure = new Enclosure("Tiny", 20f, 1);

        assertFalse(tinyEnclosure.isFull());

        tinyEnclosure.addCharacter(creature1);

        assertTrue(tinyEnclosure.isFull());
    }

    // ========== Feeding Tests ==========

    @Test
    @DisplayName("Should feed all creatures")
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

        // At least one should have been fed
        assertTrue(creature1.getHunger() > 50 || creature2.getHunger() > 40);
    }

    @Test
    @DisplayName("Should not feed when no food available")
    void testFeedWithoutFood() {
        creature1.setHunger(50);
        enclosure.addCharacter(creature1);

        enclosure.feedAll();

        assertEquals(50, creature1.getHunger());
    }

    // ========== Healing Tests ==========

    @Test
    @DisplayName("Should heal all creatures")
    void testHealAllCreatures() {
        creature1.setHealth(30);
        creature2.setHealth(40);
        enclosure.addCharacter(creature1);
        enclosure.addCharacter(creature2);

        enclosure.healAll(20);

        assertEquals(50, creature1.getHealth());
        assertEquals(60, creature2.getHealth());
    }

    @Test
    @DisplayName("Should not heal dead creatures")
    void testHealSkipsDeadCreatures() {
        creature1.setHealth(0);
        creature2.setHealth(40);
        enclosure.addCharacter(creature1);
        enclosure.addCharacter(creature2);

        enclosure.healAll(20);

        assertEquals(0, creature1.getHealth());
        assertEquals(60, creature2.getHealth());
    }

    // ========== Calming Tests ==========

    @Test
    @DisplayName("Should calm aggressive creatures")
    void testCalmCreatures() {
        creature1.setBelligerence(80);
        creature2.setBelligerence(90);
        enclosure.addCharacter(creature1);
        enclosure.addCharacter(creature2);

        enclosure.calmCreatures();

        assertEquals(60, creature1.getBelligerence());
        assertEquals(70, creature2.getBelligerence());
    }

    @Test
    @DisplayName("Should not calm already calm creatures")
    void testCalmAlreadyCalmCreatures() {
        creature1.setBelligerence(30);
        enclosure.addCharacter(creature1);

        enclosure.calmCreatures();

        assertEquals(30, creature1.getBelligerence());
    }

    // ========== Capacity Management Tests ==========

    @Test
    @DisplayName("Should update max capacity")
    void testSetMaxCapacity() {
        enclosure.setMaxCapacity(100);
        assertEquals(100, enclosure.getMaxCapacity());
    }

    // ========== Display Tests ==========

    @Test
    @DisplayName("Should display enclosure information without errors")
    void testDisplay() {
        enclosure.addCharacter(creature1);
        Food boar = new Food(Foods.BOAR, Freshness.FRESH);
        enclosure.addFood(boar);

        assertDoesNotThrow(() -> enclosure.display());
    }

    @Test
    @DisplayName("Should display empty enclosure without errors")
    void testDisplayEmpty() {
        assertDoesNotThrow(() -> enclosure.display());
    }
}

