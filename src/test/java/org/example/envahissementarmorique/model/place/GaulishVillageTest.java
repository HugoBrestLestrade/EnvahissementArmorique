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
 * Unit tests for GaulishVillage class.
 * Tests Gaulois-specific rules and potion management.
 */
@DisplayName("GaulishVillage Tests")
class GaulishVillageTest {

    private GaulishVillage village;
    private GameCharacter gaulois;
    private GameCharacter roman;

    @BeforeEach
    void setUp() {
        ClanLeader chief = new ClanLeader("Abraracourcix", "M", 50, null);
        village = new GaulishVillage("Village des Irréductibles", 2000f, chief);

        gaulois = new Gaulois("Astérix", "M", 1.65, 35, 70, 50, 100, 100, 80, 0);

        roman = new Roman("Centurion", "M", 1.75, 30, 50, 40, 100, 100, 60, 0);
    }

    // ========== Initialization Tests ==========

    @Test
    @DisplayName("Should initialize Gaulish village with correct attributes")
    void testVillageInitialization() {
        assertEquals("Village des Irréductibles", village.getName());
        assertEquals(2000f, village.getArea());
        assertNotNull(village.getChief());
        assertEquals(100, village.getResistanceLevel());
        assertEquals(90, village.getMoraleLevel());
        assertEquals(0, village.getPotionCount());
    }

    @Test
    @DisplayName("Should initialize with custom resistance and morale")
    void testVillageCustomInitialization() {
        ClanLeader chief = new ClanLeader("Chief", "M", 45, null);
        GaulishVillage customVillage = new GaulishVillage("Custom", 1000f, chief, 80, 75);

        assertEquals(80, customVillage.getResistanceLevel());
        assertEquals(75, customVillage.getMoraleLevel());
    }

    // ========== Character Addition Rules Tests ==========

    @Test
    @DisplayName("Should accept Gaulois character")
    void testAcceptGaulois() {
        boolean added = village.addCharacter(gaulois);

        assertTrue(added);
        assertEquals(1, village.getNumberOfCharacters());
        assertTrue(village.getCharacters().contains(gaulois));
    }

    @Test
    @DisplayName("Should reject Roman character")
    void testRejectRoman() {
        boolean added = village.addCharacter(roman);

        assertFalse(added);
        assertEquals(0, village.getNumberOfCharacters());
    }

    @Test
    @DisplayName("Should accept FantasticCreature")
    void testAcceptFantasticCreature() {
        FantasticCreature creature = new FantasticCreature("Idéfix", "M", 0.5, 5, 10, 20, 50, 100, 30, 0);
        boolean added = village.addCharacter(creature);

        assertTrue(added);
        assertTrue(village.getCharacters().contains(creature));
    }

    // ========== Resistance and Morale Tests ==========

    @Test
    @DisplayName("Should increase resistance level")
    void testIncreaseResistance() {
        village.setResistanceLevel(70);
        village.increaseResistance(20);

        assertEquals(90, village.getResistanceLevel());
    }

    @Test
    @DisplayName("Should not exceed max resistance")
    void testResistanceMaxCap() {
        village.setResistanceLevel(95);
        village.increaseResistance(20);

        assertEquals(100, village.getResistanceLevel());
    }

    @Test
    @DisplayName("Should decrease resistance level")
    void testDecreaseResistance() {
        village.decreaseResistance(30);

        assertEquals(70, village.getResistanceLevel());
    }

    @Test
    @DisplayName("Should not go below min resistance")
    void testResistanceMinCap() {
        village.setResistanceLevel(10);
        village.decreaseResistance(20);

        assertEquals(0, village.getResistanceLevel());
    }

    @Test
    @DisplayName("Should increase morale level")
    void testIncreaseMorale() {
        village.setMoraleLevel(60);
        village.increaseMorale(25);

        assertEquals(85, village.getMoraleLevel());
    }

    @Test
    @DisplayName("Should not exceed max morale")
    void testMoraleMaxCap() {
        village.setMoraleLevel(95);
        village.increaseMorale(20);

        assertEquals(100, village.getMoraleLevel());
    }

    @Test
    @DisplayName("Should decrease morale level")
    void testDecreaseMorale() {
        village.decreaseMorale(20);

        assertEquals(70, village.getMoraleLevel());
    }

    @Test
    @DisplayName("Should not go below min morale")
    void testMoraleMinCap() {
        village.setMoraleLevel(5);
        village.decreaseMorale(10);

        assertEquals(0, village.getMoraleLevel());
    }

    // ========== Potion Management Tests ==========

    @Test
    @DisplayName("Should add potion to village")
    void testAddPotion() {
        Potion potion = new Potion(Foods.SECRET_INGREDIENT, 10);
        village.addPotion(potion);

        assertEquals(1, village.getPotionCount());
    }

    @Test
    @DisplayName("Should add multiple potions")
    void testAddMultiplePotions() {
        Potion potion1 = new Potion(Foods.SECRET_INGREDIENT, 10);
        Potion potion2 = new Potion(Foods.SECRET_INGREDIENT, 5);

        village.addPotion(potion1);
        village.addPotion(potion2);

        assertEquals(2, village.getPotionCount());
    }

    @Test
    @DisplayName("Should get potions list")
    void testGetPotions() {
        Potion potion = new Potion(Foods.SECRET_INGREDIENT, 10);
        village.addPotion(potion);

        assertNotNull(village.getPotions());
        assertEquals(1, village.getPotions().size());
        assertTrue(village.getPotions().contains(potion));
    }

    // ========== Population Count Tests ==========

    @Test
    @DisplayName("Should accept multiple Gaulois")
    void testAcceptMultipleGaulois() {
        GameCharacter gaulois1 = new Gaulois("Astérix", "M", 1.65, 35, 70, 50, 100, 100, 80, 0);
        GameCharacter gaulois2 = new Gaulois("Obélix", "M", 1.85, 35, 90, 60, 120, 100, 70, 0);

        village.addCharacter(gaulois1);
        village.addCharacter(gaulois2);

        assertEquals(2, village.getNumberOfCharacters());
        assertTrue(village.getCharacters().contains(gaulois1));
        assertTrue(village.getCharacters().contains(gaulois2));
    }

    // ========== Village Status Tests ==========

    @Test
    @DisplayName("Should maintain resistance and morale levels")
    void testMaintainLevels() {
        village.setMoraleLevel(90);
        village.setResistanceLevel(95);

        assertEquals(90, village.getMoraleLevel());
        assertEquals(95, village.getResistanceLevel());
    }

    @Test
    @DisplayName("Should get Gaulois list")
    void testGetGaulois() {
        GameCharacter gaulois1 = new Gaulois("Astérix", "M", 1.65, 35, 70, 50, 100, 100, 80, 0);
        GameCharacter gaulois2 = new Gaulois("Obélix", "M", 1.85, 35, 90, 60, 120, 100, 70, 0);

        village.addCharacter(gaulois1);
        village.addCharacter(gaulois2);

        assertEquals(2, village.getGaulois().size());
    }

    // ========== Display Tests ==========

    @Test
    @DisplayName("Should display village information without errors")
    void testDisplay() {
        village.addCharacter(gaulois);
        assertDoesNotThrow(() -> village.display());
    }
}

