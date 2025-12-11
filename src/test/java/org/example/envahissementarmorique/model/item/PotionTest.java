package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link Potion}.
 * <p>
 * Cette classe teste la création, la consommation et les propriétés des potions.
 * </p>
 *
 * <p>Les tests incluent notamment :</p>
 * <ul>
 *     <li>La création de potions avec différents ingrédients.</li>
 *     <li>La vérification que la potion est consommable.</li>
 *     <li>La vérification de l’ingrédient {@link Foods} associé à la potion.</li>
 *     <li>La consommation séquentielle de plusieurs potions.</li>
 *     <li>La conservation des propriétés (nom, ingrédient) après consommation.</li>
 *     <li>La comparaison de potions basées sur leur ingrédient.</li>
 * </ul>
 *
 * <p>Chaque méthode de test est annotée avec {@link Test} et {@link DisplayName} pour une meilleure lisibilité.</p>
 *
 * @author
 * @version 1.0
 */
@DisplayName("Tests de la classe Potion")
class PotionTest {

    private Potion potion;
    private GameCharacter testCharacter;

    /**
     * Initialise un personnage de test avant chaque méthode.
     */
    @BeforeEach
    void setUp() {
        testCharacter = new GameCharacter(
                "Test Druid", "Male", "Gaul", 1.70, 40,
                30, 60, 100, 100, 20, 0
        );
    }

    /**
     * Vérifie la création d’une potion avec l’ingrédient SECRET_INGREDIENT.
     */
    @Test
    @DisplayName("Doit créer une potion avec SECRET_INGREDIENT")
    void testCreatePotionWithSecretIngredient() {
        potion = new Potion(Foods.SECRET_INGREDIENT);

        assertNotNull(potion);
        assertEquals(Foods.SECRET_INGREDIENT, potion.getFoods());
    }

    /**
     * Vérifie la création de potions avec divers ingrédients.
     */
    @Test
    @DisplayName("Doit créer des potions avec divers ingrédients")
    void testCreatePotionWithVariousIngredients() {
        Potion potion1 = new Potion(Foods.SECRET_INGREDIENT);
        assertEquals(Foods.SECRET_INGREDIENT, potion1.getFoods());

        Potion potion2 = new Potion(Foods.MISTLETOE);
        assertEquals(Foods.MISTLETOE, potion2.getFoods());

        Potion potion3 = new Potion(Foods.FRESH_FOUR_LEAF_CLOVER);
        assertEquals(Foods.FRESH_FOUR_LEAF_CLOVER, potion3.getFoods());
    }

    /**
     * Vérifie que la potion peut être consommée sans générer d’exception.
     */
    @Test
    @DisplayName("Doit consommer une potion sans lancer d’exception")
    void testConsumePotion() {
        potion = new Potion(Foods.SECRET_INGREDIENT);
        assertDoesNotThrow(() -> potion.consume(testCharacter));
    }

    /**
     * Vérifie que la potion est bien une instance de {@link Consumable}.
     */
    @Test
    @DisplayName("Doit vérifier que la potion est consommable")
    void testPotionIsConsumable() {
        potion = new Potion(Foods.SECRET_INGREDIENT);
        assertTrue(potion instanceof Consumable);
    }

    /**
     * Vérifie que l’ingrédient associé à la potion est correct.
     */
    @Test
    @DisplayName("Doit récupérer correctement l’ingrédient de la potion")
    void testGetFoods() {
        potion = new Potion(Foods.SECRET_INGREDIENT);
        assertEquals(Foods.SECRET_INGREDIENT, potion.getFoods());
        assertEquals(FoodType.SECRET_INGREDIENTS, potion.getFoods().getType());
    }

    /**
     * Vérifie la création de potions avec des ingrédients spéciaux.
     */
    @Test
    @DisplayName("Doit créer des potions avec des ingrédients spéciaux")
    void testPotionWithSpecialIngredients() {
        Potion secretPotion = new Potion(Foods.SECRET_INGREDIENT);
        assertNotNull(secretPotion);

        Potion mistletoePotion = new Potion(Foods.MISTLETOE);
        assertNotNull(mistletoePotion);

        Potion cloverPotion = new Potion(Foods.FRESH_FOUR_LEAF_CLOVER);
        assertNotNull(cloverPotion);

        Potion hairPotion = new Potion(Foods.IDEFIX_HAIR);
        assertNotNull(hairPotion);
    }

    /**
     * Vérifie que les ingrédients typiques de potion ont une valeur nutritionnelle nulle.
     */
    @Test
    @DisplayName("Les ingrédients de potion doivent avoir une nutrition nulle")
    void testPotionIngredientNutrition() {
        potion = new Potion(Foods.SECRET_INGREDIENT);
        assertEquals(0, potion.getFoods().getNutrition());

        potion = new Potion(Foods.MISTLETOE);
        assertEquals(0, potion.getFoods().getNutrition());

        potion = new Potion(Foods.IDEFIX_HAIR);
        assertEquals(0, potion.getFoods().getNutrition());
    }

    /**
     * Vérifie la consommation successive de plusieurs potions.
     */
    @Test
    @DisplayName("Doit consommer plusieurs potions successivement")
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

    /**
     * Vérifie que les propriétés de la potion sont conservées après consommation.
     */
    @Test
    @DisplayName("Doit maintenir les propriétés de la potion après consommation")
    void testPotionPropertiesMaintained() {
        potion = new Potion(Foods.SECRET_INGREDIENT);

        Foods originalFood = potion.getFoods();
        String originalName = potion.getName();

        potion.consume(testCharacter);

        assertEquals(originalFood, potion.getFoods());
        assertEquals(originalName, potion.getName());
    }

    /**
     * Vérifie que la potion peut être créée à partir de n’importe quelle valeur de {@link Foods}.
     */
    @Test
    @DisplayName("Doit créer une potion à partir de n’importe quel Foods")
    void testPotionFromAnyFoods() {
        assertDoesNotThrow(() -> {
            new Potion(Foods.SECRET_INGREDIENT);
            new Potion(Foods.MISTLETOE);
            new Potion(Foods.ROCK_OIL);
            new Potion(Foods.IDEFIX_HAIR);
        });
    }

    /**
     * Vérifie l’égalité des potions basée sur leur ingrédient.
     */
    @Test
    @DisplayName("Doit comparer les potions selon leur ingrédient")
    void testPotionComparison() {
        Potion potion1 = new Potion(Foods.SECRET_INGREDIENT);
        Potion potion2 = new Potion(Foods.SECRET_INGREDIENT);
        Potion potion3 = new Potion(Foods.MISTLETOE);

        assertEquals(potion1.getFoods(), potion2.getFoods());
        assertNotEquals(potion1.getFoods(), potion3.getFoods());
    }
}
