package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the abstract Consumable class.
 * Tests common consumable behavior through Food and Potion implementations.
 *
 * @author Boudhib Mohamed-Amine
 * @version 1.0
 */
@DisplayName("Consumable Abstract Class Tests")
class ConsumableTest {

    private Character testCharacter;

    @BeforeEach
    void setUp() {
        testCharacter = new Character(
            "Test Character", "Male", "Gaul", 1.75, 30,
            50, 50, 100, 100, 50, 0
        );
    }

    @Test
    @DisplayName("Should not allow null Foods in constructor")
    void testNullFoodsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Food(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Potion(null);
        });
    }

    @Test
    @DisplayName("Should return correct Foods from getFoods()")
    void testGetFoods() {
        Consumable food = new Food(Foods.BOAR);
        assertEquals(Foods.BOAR, food.getFoods());

        Consumable potion = new Potion(Foods.SECRET_INGREDIENT);
        assertEquals(Foods.SECRET_INGREDIENT, potion.getFoods());
    }

    @Test
    @DisplayName("Should return correct name from getName()")
    void testGetName() {
        Consumable food = new Food(Foods.WINE);
        assertEquals("Wine", food.getName());

        Consumable potion = new Potion(Foods.MISTLETOE);
        assertEquals("Mistletoe", potion.getName());
    }

    @Test
    @DisplayName("Should implement consume method in subclasses")
    void testConsumeImplementation() {
        Consumable food = new Food(Foods.BOAR);
        assertDoesNotThrow(() -> food.consume(testCharacter));

        Consumable potion = new Potion(Foods.SECRET_INGREDIENT);
        assertDoesNotThrow(() -> potion.consume(testCharacter));
    }

    @Test
    @DisplayName("Should return correct toString from Foods")
    void testToStringFromFoods() {
        // Note: Food overrides toString, but base Consumable returns foods.getLabel()
        Consumable potion = new Potion(Foods.SECRET_INGREDIENT);
        assertTrue(potion.toString().contains("Secret ingredient"));
    }

    @Test
    @DisplayName("Should verify sealed class permits only Food and Potion")
    void testSealedClassHierarchy() {
        Consumable food = new Food(Foods.CARROT);
        assertTrue(food instanceof Food);

        Consumable potion = new Potion(Foods.MISTLETOE);
        assertTrue(potion instanceof Potion);

        // Verify both are Consumables
        assertTrue(food instanceof Consumable);
        assertTrue(potion instanceof Consumable);
    }

    @Test
    @DisplayName("Should handle different Foods types in consumables")
    void testDifferentFoodsTypes() {
        // Meats
        Consumable meat = new Food(Foods.LOBSTER);
        assertEquals(FoodType.MEATS, meat.getFoods().getType());

        // Vegetables
        Consumable vegetable = new Food(Foods.STRAWBERRY);
        assertEquals(FoodType.VEGETABLES, vegetable.getFoods().getType());

        // Liquids
        Consumable liquid = new Food(Foods.MEAD);
        assertEquals(FoodType.LIQUIDS, liquid.getFoods().getType());

        // Secret Ingredients (typically for potions)
        Consumable secret = new Potion(Foods.SECRET_INGREDIENT);
        assertEquals(FoodType.SECRET_INGREDIENTS, secret.getFoods().getType());
    }

    @Test
    @DisplayName("Should create multiple consumables with same Foods")
    void testMultipleConsumablesWithSameFoods() {
        Consumable food1 = new Food(Foods.BOAR);
        Consumable food2 = new Food(Foods.BOAR);

        assertEquals(food1.getFoods(), food2.getFoods());
        assertEquals(food1.getName(), food2.getName());
    }

    @Test
    @DisplayName("Should verify Foods is final and immutable")
    void testFoodsImmutability() {
        Consumable consumable = new Food(Foods.HONEY);
        Foods originalFoods = consumable.getFoods();

        // Foods should be the same reference (immutable enum)
        assertSame(originalFoods, consumable.getFoods());
    }

    @Test
    @DisplayName("Should test consumable polymorphism")
    void testConsumablePolymorphism() {
        Consumable[] consumables = {
            new Food(Foods.BOAR),
            new Food(Foods.WINE),
            new Potion(Foods.SECRET_INGREDIENT),
            new Potion(Foods.MISTLETOE)
        };

        for (Consumable consumable : consumables) {
            assertNotNull(consumable.getFoods());
            assertNotNull(consumable.getName());
            assertDoesNotThrow(() -> consumable.consume(testCharacter));
        }
    }

    @Test
    @DisplayName("Should verify consumable behavior consistency")
    void testConsumableBehaviorConsistency() {
        Consumable food = new Food(Foods.FRESH_FISH);
        Consumable potion = new Potion(Foods.IDEFIX_HAIR);

        // Both should have non-null Foods
        assertNotNull(food.getFoods());
        assertNotNull(potion.getFoods());

        // Both should have non-null names
        assertNotNull(food.getName());
        assertNotNull(potion.getName());

        // Both should be consumable
        assertDoesNotThrow(() -> food.consume(testCharacter));
        assertDoesNotThrow(() -> potion.consume(testCharacter));
    }

    @Test
    @DisplayName("Should throw exception with meaningful message for null Foods")
    void testNullFoodsExceptionMessage() {
        Exception foodException = assertThrows(IllegalArgumentException.class, () -> {
            new Food(null);
        });
        assertTrue(foodException.getMessage().contains("Foods cannot be null"));

        Exception potionException = assertThrows(IllegalArgumentException.class, () -> {
            new Potion(null);
        });
        assertTrue(potionException.getMessage().contains("Foods cannot be null"));
    }
}

