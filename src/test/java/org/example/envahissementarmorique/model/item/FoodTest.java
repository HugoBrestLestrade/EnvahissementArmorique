package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Food class.
 * Tests food creation, freshness degradation, and consumption logic.
 *
 * @author Boudhib Mohamed-Amine
 * @version 1.0
 */
@DisplayName("Food Class Tests")
class FoodTest {

    private Food food;
    private GameCharacter testCharacter;

    @BeforeEach
    void setUp() {
        // Create a test character for consumption tests
        testCharacter = new GameCharacter(
            "Test", "Male", "Gaul", 1.75, 30,
            50, 50, 100, 100, 50, 0
        );
    }

    @Test
    @DisplayName("Should create food with default FRESH freshness")
    void testCreateFoodWithDefaultFreshness() {
        food = new Food(Foods.BOAR);

        assertNotNull(food);
        assertEquals(Freshness.FRESH, food.getFreshness());
        assertEquals(Foods.BOAR, food.getFoods());
    }

    @Test
    @DisplayName("Should create food with specified freshness")
    void testCreateFoodWithSpecifiedFreshness() {
        food = new Food(Foods.FRESH_FISH, Freshness.ROTTEN);

        assertNotNull(food);
        assertEquals(Freshness.ROTTEN, food.getFreshness());
        assertEquals(Foods.FRESH_FISH, food.getFoods());
    }

    @Test
    @DisplayName("Should degrade food freshness")
    void testDegradeFreshness() {
        food = new Food(Foods.BOAR);

        assertEquals(Freshness.FRESH, food.getFreshness());

        food.degrade();
        assertEquals(Freshness.OKAY, food.getFreshness());

        food.degrade();
        assertEquals(Freshness.ROTTEN, food.getFreshness());

        food.degrade();
        assertEquals(Freshness.ROTTEN, food.getFreshness()); // Stays rotten
    }

    @Test
    @DisplayName("Should return correct nutritional value")
    void testGetNutritionalValue() {
        food = new Food(Foods.BOAR);
        assertEquals(50, food.getNutritionalValue());

        food = new Food(Foods.CARROT);
        assertEquals(5, food.getNutritionalValue());

        food = new Food(Foods.MISTLETOE);
        assertEquals(0, food.getNutritionalValue());
    }

    @Test
    @DisplayName("Should return correct name")
    void testGetName() {
        food = new Food(Foods.BOAR);
        assertEquals("Boar", food.getName());

        food = new Food(Foods.WINE);
        assertEquals("Wine", food.getName());
    }

    @Test
    @DisplayName("Should return correct toString with freshness")
    void testToString() {
        food = new Food(Foods.BOAR);
        assertEquals("Boar (Fresh)", food.toString());

        food.degrade();
        assertEquals("Boar (Quite Fresh)", food.toString());

        food.degrade();
        assertEquals("Boar (Rotten)", food.toString());
    }

    @Test
    @DisplayName("Should consume food without throwing exception")
    void testConsumeFood() {
        food = new Food(Foods.BOAR);
        assertDoesNotThrow(() -> food.consume(testCharacter));
    }

    @Test
    @DisplayName("Should increase hunger when consuming fresh food")
    void testConsumeFreshFood() {
        food = new Food(Foods.BOAR, Freshness.FRESH);
        int initialHunger = testCharacter.getHunger();
        int nutritionalValue = food.getNutritionalValue();

        food.consume(testCharacter);

        assertEquals(initialHunger + nutritionalValue, testCharacter.getHunger());
    }

    @Test
    @DisplayName("Should decrease health and hunger when consuming rotten food")
    void testConsumeRottenFood() {
        food = new Food(Foods.BOAR, Freshness.ROTTEN);
        int initialHealth = testCharacter.getHealth();
        int initialHunger = testCharacter.getHunger();

        food.consume(testCharacter);

        // Vérifie que la santé a diminué de 1/5
        assertEquals(initialHealth - (initialHealth / 5), testCharacter.getHealth());

        // Vérifie que la faim a diminué de 1/3
        assertEquals(initialHunger - (initialHunger / 3), testCharacter.getHunger());
    }

    @Test
    @DisplayName("Should handle rotten fish consumption")
    void testRottenFishConsumption() {
        food = new Food(Foods.FRESH_FISH, Freshness.ROTTEN);
        int initialHealth = testCharacter.getHealth();
        int initialHunger = testCharacter.getHunger();

        food.consume(testCharacter);

        // Vérifie que la santé et la faim ont diminué
        assertTrue(testCharacter.getHealth() < initialHealth);
        assertTrue(testCharacter.getHunger() < initialHunger);
    }

    @Test
    @DisplayName("Should increase hunger when consuming OKAY food")
    void testConsumeOkayFood() {
        food = new Food(Foods.CARROT, Freshness.OKAY);
        int initialHunger = testCharacter.getHunger();
        int nutritionalValue = food.getNutritionalValue();

        food.consume(testCharacter);

        // La nourriture OKAY n'est pas pourrie, donc augmente la faim
        assertEquals(initialHunger + nutritionalValue, testCharacter.getHunger());
    }

    @Test
    @DisplayName("Should get correct Foods type")
    void testGetFoods() {
        food = new Food(Foods.LOBSTER);
        assertEquals(Foods.LOBSTER, food.getFoods());
        assertEquals(FoodType.MEATS, food.getFoods().getType());
    }

    @Test
    @DisplayName("Should create different types of food")
    void testDifferentFoodTypes() {
        // Meat
        Food meat = new Food(Foods.BOAR);
        assertEquals(FoodType.MEATS, meat.getFoods().getType());

        // Vegetable
        Food vegetable = new Food(Foods.CARROT);
        assertEquals(FoodType.VEGETABLES, vegetable.getFoods().getType());

        // Liquid
        Food liquid = new Food(Foods.WINE);
        assertEquals(FoodType.LIQUIDS, liquid.getFoods().getType());

        // Spice
        Food spice = new Food(Foods.SALT);
        assertEquals(FoodType.SPICES, spice.getFoods().getType());
    }

    @Test
    @DisplayName("Should handle food with OKAY freshness")
    void testOkayFreshness() {
        food = new Food(Foods.STRAWBERRY, Freshness.OKAY);
        assertEquals(Freshness.OKAY, food.getFreshness());
        assertEquals("Strawberries (Quite Fresh)", food.toString());
    }

    @Test
    @DisplayName("Should verify food is consumable")
    void testFoodIsConsumable() {
        food = new Food(Foods.MEAD);
        assertTrue(food instanceof Consumable);
    }

    @Test
    @DisplayName("Should test multiple degradation cycles")
    void testMultipleDegradationCycles() {
        food = new Food(Foods.HONEY);

        for (int i = 0; i < 5; i++) {
            food.degrade();
        }

        // After multiple degradations, should stay ROTTEN
        assertEquals(Freshness.ROTTEN, food.getFreshness());
    }

    @Test
    @DisplayName("Should maintain food type after degradation")
    void testFoodTypeMaintainedAfterDegradation() {
        food = new Food(Foods.BOAR);
        Foods originalFood = food.getFoods();

        food.degrade();
        food.degrade();

        assertEquals(originalFood, food.getFoods());
        assertEquals("Boar", food.getName());
    }
}

