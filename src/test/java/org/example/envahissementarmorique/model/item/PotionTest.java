package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Potion class.
 * Tests potion creation, consumption, and properties.
 *
 * @author Boudhib Mohamed-Amine
 * @version 1.0
 */
@DisplayName("Potion Class Tests")
class PotionTest {

    private Potion potion;
    private Character testCharacter;

    @BeforeEach
    void setUp() {
        // Create a test character for consumption tests
        testCharacter = new Character(
            "Test Druid", "Male", "Gaul", 1.70, 40,
            30, 60, 100, 100, 20, 0
        );
    }

    @Test
    @DisplayName("Should create potion with SECRET_INGREDIENT")
    void testCreatePotionWithSecretIngredient() {
        potion = new Potion(Foods.SECRET_INGREDIENT);

        assertNotNull(potion);
        assertEquals(Foods.SECRET_INGREDIENT, potion.getFoods());
    }

    @Test
    @DisplayName("Should create potion with various ingredients")
    void testCreatePotionWithVariousIngredients() {
        // Potions can be made from different Foods
        Potion potion1 = new Potion(Foods.SECRET_INGREDIENT);
        assertEquals(Foods.SECRET_INGREDIENT, potion1.getFoods());

        Potion potion2 = new Potion(Foods.MISTLETOE);
        assertEquals(Foods.MISTLETOE, potion2.getFoods());

        Potion potion3 = new Potion(Foods.FRESH_FOUR_LEAF_CLOVER);
        assertEquals(Foods.FRESH_FOUR_LEAF_CLOVER, potion3.getFoods());
    }

    @Test
    @DisplayName("Should consume potion without throwing exception")
    void testConsumePotion() {
        potion = new Potion(Foods.SECRET_INGREDIENT);
        assertDoesNotThrow(() -> potion.consume(testCharacter));
    }

    @Test
    @DisplayName("Should verify potion is consumable")
    void testPotionIsConsumable() {
        potion = new Potion(Foods.SECRET_INGREDIENT);
        assertTrue(potion instanceof Consumable);
    }

    @Test
    @DisplayName("Should get correct Foods from potion")
    void testGetFoods() {
        potion = new Potion(Foods.SECRET_INGREDIENT);
        assertEquals(Foods.SECRET_INGREDIENT, potion.getFoods());
        assertEquals(FoodType.SECRET_INGREDIENTS, potion.getFoods().getType());
    }

    @Test
    @DisplayName("Should create potion with special ingredient foods")
    void testPotionWithSpecialIngredients() {
        // Test with different potion-suitable ingredients
        Potion secretPotion = new Potion(Foods.SECRET_INGREDIENT);
        assertNotNull(secretPotion);

        Potion mistletoePotion = new Potion(Foods.MISTLETOE);
        assertNotNull(mistletoePotion);

        Potion cloverPotion = new Potion(Foods.FRESH_FOUR_LEAF_CLOVER);
        assertNotNull(cloverPotion);

        Potion hairPotion = new Potion(Foods.IDEFIX_HAIR);
        assertNotNull(hairPotion);
    }

    @Test
    @DisplayName("Should have zero nutrition for typical potion ingredients")
    void testPotionIngredientNutrition() {
        potion = new Potion(Foods.SECRET_INGREDIENT);
        assertEquals(0, potion.getFoods().getNutrition());

        potion = new Potion(Foods.MISTLETOE);
        assertEquals(0, potion.getFoods().getNutrition());

        potion = new Potion(Foods.IDEFIX_HAIR);
        assertEquals(0, potion.getFoods().getNutrition());
    }

    @Test
    @DisplayName("Should consume multiple potions sequentially")
    void testConsumeMultiplePotions() {
        Potion potion1 = new Potion(Foods.SECRET_INGREDIENT);
        Potion potion2 = new Potion(Foods.MISTLETOE);
        Potion potion3 = new Potion(Foods.FRESH_FOUR_LEAF_CLOVER);

        assertDoesNotThrow(() -> {
            potion1.consume(testCharacter);
            potion2.consume(testCharacter);
            potion3.consume(testCharacter);
        });
    }

    @Test
    @DisplayName("Should maintain potion properties")
    void testPotionPropertiesMaintained() {
        potion = new Potion(Foods.SECRET_INGREDIENT);

        Foods originalFood = potion.getFoods();
        String originalName = potion.getName();

        // Consume the potion
        potion.consume(testCharacter);

        // Properties should remain the same
        assertEquals(originalFood, potion.getFoods());
        assertEquals(originalName, potion.getName());
    }

    @Test
    @DisplayName("Should create potion from any Foods enum")
    void testPotionFromAnyFoods() {
        // Potions can technically be created from any Foods
        // though some make more sense than others
        assertDoesNotThrow(() -> {
            new Potion(Foods.SECRET_INGREDIENT);
            new Potion(Foods.MISTLETOE);
            new Potion(Foods.ROCK_OIL);
            new Potion(Foods.IDEFIX_HAIR);
        });
    }

    @Test
    @DisplayName("Should test potion equality based on Foods")
    void testPotionComparison() {
        Potion potion1 = new Potion(Foods.SECRET_INGREDIENT);
        Potion potion2 = new Potion(Foods.SECRET_INGREDIENT);
        Potion potion3 = new Potion(Foods.MISTLETOE);

        // Same Foods type
        assertEquals(potion1.getFoods(), potion2.getFoods());

        // Different Foods type
        assertNotEquals(potion1.getFoods(), potion3.getFoods());
    }
}

