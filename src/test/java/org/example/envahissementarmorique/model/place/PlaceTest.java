package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.ClanLeader;
import org.example.envahissementarmorique.model.item.Food;
import org.example.envahissementarmorique.model.item.Foods;
import org.example.envahissementarmorique.model.item.Freshness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Place class.
 * Tests character and food management.
 */
@DisplayName("Place Tests")
class PlaceTest {

    private Place testPlace;
    private GameCharacter character1;
    private GameCharacter character2;

    // Concrete implementation for testing
    private static class TestPlace extends Place {
        public TestPlace(String name, float area, ClanLeader chief) {
            super(name, area, chief);
        }

        @Override
        protected boolean canAddCharacter(GameCharacter c) {
            return c != null && !c.isDead();
        }
    }

    @BeforeEach
    void setUp() {
        testPlace = new TestPlace("Test Village", 1000f, null);

        character1 = new GameCharacter(
            "Hero1", "M", "Gaulois",
            1.70, 30, 50, 40, 100, 100, 50, 0
        );

        character2 = new GameCharacter(
            "Hero2", "F", "Gaulois",
            1.65, 28, 45, 35, 80, 100, 40, 0
        );
    }

    // ========== Initialization Tests ==========

    @Test
    @DisplayName("Should initialize place with correct attributes")
    void testPlaceInitialization() {
        assertEquals("Test Village", testPlace.getName());
        assertEquals(1000f, testPlace.getArea());
        assertNull(testPlace.getChief());
        assertEquals(0, testPlace.getNumberOfCharacters());
        assertTrue(testPlace.getCharacters().isEmpty());
        assertTrue(testPlace.getFoods().isEmpty());
    }

    // ========== Character Management Tests ==========

    @Test
    @DisplayName("Should add character to place")
    void testAddCharacter() {
        boolean added = testPlace.addCharacter(character1);

        assertTrue(added);
        assertEquals(1, testPlace.getNumberOfCharacters());
        assertTrue(testPlace.getCharacters().contains(character1));
    }

    @Test
    @DisplayName("Should add multiple characters")
    void testAddMultipleCharacters() {
        testPlace.addCharacter(character1);
        testPlace.addCharacter(character2);

        assertEquals(2, testPlace.getNumberOfCharacters());
        assertTrue(testPlace.getCharacters().contains(character1));
        assertTrue(testPlace.getCharacters().contains(character2));
    }

    @Test
    @DisplayName("Should not add null character")
    void testAddNullCharacter() {
        boolean added = testPlace.addCharacter(null);

        assertFalse(added);
        assertEquals(0, testPlace.getNumberOfCharacters());
    }

    @Test
    @DisplayName("Should not add dead character")
    void testAddDeadCharacter() {
        character1.setHealth(0);
        boolean added = testPlace.addCharacter(character1);

        assertFalse(added);
        assertEquals(0, testPlace.getNumberOfCharacters());
    }

    @Test
    @DisplayName("Should remove character from place")
    void testRemoveCharacter() {
        testPlace.addCharacter(character1);
        testPlace.addCharacter(character2);

        boolean removed = testPlace.removeCharacter(character1);

        assertTrue(removed);
        assertEquals(1, testPlace.getNumberOfCharacters());
        assertFalse(testPlace.getCharacters().contains(character1));
        assertTrue(testPlace.getCharacters().contains(character2));
    }

    @Test
    @DisplayName("Should return false when removing non-existent character")
    void testRemoveNonExistentCharacter() {
        testPlace.addCharacter(character1);

        boolean removed = testPlace.removeCharacter(character2);

        assertFalse(removed);
        assertEquals(1, testPlace.getNumberOfCharacters());
    }

    @Test
    @DisplayName("Should remove dead characters")
    void testRemoveDeadCharacters() {
        testPlace.addCharacter(character1);
        testPlace.addCharacter(character2);

        character1.setHealth(0);
        testPlace.removeDeadCharacters();

        assertEquals(1, testPlace.getNumberOfCharacters());
        assertFalse(testPlace.getCharacters().contains(character1));
        assertTrue(testPlace.getCharacters().contains(character2));
    }

    @Test
    @DisplayName("Should get character by name")
    void testGetCharacterByName() {
        testPlace.addCharacter(character1);
        testPlace.addCharacter(character2);

        GameCharacter found = testPlace.getCharacterByName("Hero1");

        assertNotNull(found);
        assertEquals("Hero1", found.getName());
        assertSame(character1, found);
    }

    @Test
    @DisplayName("Should return null when character not found by name")
    void testGetCharacterByNameNotFound() {
        testPlace.addCharacter(character1);

        GameCharacter found = testPlace.getCharacterByName("NonExistent");

        assertNull(found);
    }

    @Test
    @DisplayName("Should count alive characters")
    void testGetAliveCharactersCount() {
        testPlace.addCharacter(character1);
        testPlace.addCharacter(character2);

        assertEquals(2, testPlace.getAliveCharactersCount());

        character1.setHealth(0);
        assertEquals(1, testPlace.getAliveCharactersCount());
    }

    // ========== Healing Tests ==========

    @Test
    @DisplayName("Should heal all characters")
    void testHealAll() {
        character1.setHealth(50);
        character2.setHealth(40);
        testPlace.addCharacter(character1);
        testPlace.addCharacter(character2);

        testPlace.healAll(30);

        assertEquals(80, character1.getHealth());
        assertEquals(70, character2.getHealth());
    }

    @Test
    @DisplayName("Should not heal dead characters")
    void testHealAllSkipsDead() {
        character1.setHealth(0);
        character2.setHealth(50);
        testPlace.addCharacter(character1);
        testPlace.addCharacter(character2);

        testPlace.healAll(30);

        assertEquals(0, character1.getHealth());
        assertEquals(80, character2.getHealth());
    }

    // ========== Food Management Tests ==========

    @Test
    @DisplayName("Should add food to place")
    void testAddFood() {
        Food boar = new Food(Foods.BOAR, Freshness.FRESH);
        testPlace.addFood(boar);

        assertEquals(1, testPlace.getFoods().size());
        assertTrue(testPlace.getFoods().contains(boar));
    }

    @Test
    @DisplayName("Should add multiple foods")
    void testAddMultipleFoods() {
        Food boar = new Food(Foods.BOAR, Freshness.FRESH);
        Food fish = new Food(Foods.FRESH_FISH, Freshness.FRESH);

        testPlace.addFood(boar);
        testPlace.addFood(fish);

        assertEquals(2, testPlace.getFoods().size());
    }

    @Test
    @DisplayName("Should remove food from place")
    void testRemoveFood() {
        Food boar = new Food(Foods.BOAR, Freshness.FRESH);
        testPlace.addFood(boar);

        testPlace.removeFood(boar);

        assertEquals(0, testPlace.getFoods().size());
    }

    @Test
    @DisplayName("Should clear all foods")
    void testClearFoods() {
        Food boar = new Food(Foods.BOAR, Freshness.FRESH);
        Food fish = new Food(Foods.FRESH_FISH, Freshness.FRESH);
        testPlace.addFood(boar);
        testPlace.addFood(fish);

        testPlace.clearFoods();

        assertEquals(0, testPlace.getFoods().size());
    }

    @Test
    @DisplayName("Should feed all characters")
    void testFeedAll() {
        character1.setHunger(50);
        character2.setHunger(60);
        testPlace.addCharacter(character1);
        testPlace.addCharacter(character2);

        Food boar = new Food(Foods.BOAR, Freshness.FRESH);
        Food fish = new Food(Foods.FRESH_FISH, Freshness.FRESH);
        testPlace.addFood(boar);
        testPlace.addFood(fish);

        testPlace.feedAll();

        assertTrue(character1.getHunger() > 50 || character2.getHunger() > 60);
        assertTrue(testPlace.getFoods().size() < 2);
    }

    // ========== Chief Management Tests ==========

    @Test
    @DisplayName("Should set and get chief")
    void testSetChief() {
        ClanLeader chief = new ClanLeader("Chief", "M", 45, testPlace);
        testPlace.setChief(chief);

        assertNotNull(testPlace.getChief());
        assertEquals("Chief", testPlace.getChief().getName());
    }
}

