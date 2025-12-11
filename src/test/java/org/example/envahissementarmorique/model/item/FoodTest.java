package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link Food}.
 * <p>
 * Cette classe teste la création de nourriture, la dégradation de la fraîcheur
 * et la logique de consommation des aliments.
 * </p>
 *
 * <p>
 * Les tests incluent :
 * <ul>
 *     <li>La création de nourriture avec fraîcheur par défaut (FRESH) ou spécifiée.</li>
 *     <li>La dégradation progressive de la fraîcheur (FRESH → OKAY → ROTTEN).</li>
 *     <li>Le calcul correct de la valeur nutritionnelle selon le type de nourriture.</li>
 *     <li>La modification des attributs du personnage lors de la consommation
 *         (santé et faim).</li>
 *     <li>La gestion spécifique des aliments pourris et du poisson pourri.</li>
 *     <li>La cohérence du type de nourriture après plusieurs dégradations.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Chaque méthode de test est annotée avec {@link Test} et possède une description
 * via {@link DisplayName} pour faciliter la lecture dans les rapports de test.
 * </p>
 *
 * @author Boud
 * @version 1.0
 */
@DisplayName("Tests de la classe Food")
class FoodTest {

    /** Instance de nourriture utilisée pour les tests. */
    private Food food;

    /** Personnage utilisé pour tester la consommation des aliments. */
    private GameCharacter testCharacter;

    /**
     * Initialisation avant chaque test.
     * <p>
     * Crée un personnage générique avec des attributs par défaut.
     * </p>
     */
    @BeforeEach
    void setUp() {
        testCharacter = new GameCharacter(
                "Test", "Male", "Gaul", 1.75, 30,
                50, 50, 100, 100, 50, 0
        );
    }

    /**
     * Vérifie la création d’un aliment avec fraîcheur par défaut (FRESH).
     */
    @Test
    @DisplayName("Doit créer un aliment avec fraîcheur FRESH par défaut")
    void testCreateFoodWithDefaultFreshness() {
        food = new Food(Foods.BOAR);
        assertNotNull(food);
        assertEquals(Freshness.FRESH, food.getFreshness());
        assertEquals(Foods.BOAR, food.getFoods());
    }

    /**
     * Vérifie la création d’un aliment avec une fraîcheur spécifiée.
     */
    @Test
    @DisplayName("Doit créer un aliment avec une fraîcheur spécifiée")
    void testCreateFoodWithSpecifiedFreshness() {
        food = new Food(Foods.FRESH_FISH, Freshness.ROTTEN);
        assertNotNull(food);
        assertEquals(Freshness.ROTTEN, food.getFreshness());
        assertEquals(Foods.FRESH_FISH, food.getFoods());
    }

    /**
     * Vérifie la dégradation progressive de la fraîcheur d’un aliment.
     */
    @Test
    @DisplayName("Doit dégrader la fraîcheur de l’aliment")
    void testDegradeFreshness() {
        food = new Food(Foods.BOAR);
        assertEquals(Freshness.FRESH, food.getFreshness());

        food.degrade();
        assertEquals(Freshness.OKAY, food.getFreshness());

        food.degrade();
        assertEquals(Freshness.ROTTEN, food.getFreshness());

        food.degrade();
        assertEquals(Freshness.ROTTEN, food.getFreshness()); // Reste pourri
    }

    /**
     * Vérifie le calcul correct de la valeur nutritionnelle.
     */
    @Test
    @DisplayName("Doit retourner la valeur nutritionnelle correcte")
    void testGetNutritionalValue() {
        food = new Food(Foods.BOAR);
        assertEquals(50, food.getNutritionalValue());

        food = new Food(Foods.CARROT);
        assertEquals(5, food.getNutritionalValue());

        food = new Food(Foods.MISTLETOE);
        assertEquals(0, food.getNutritionalValue());
    }

    /**
     * Vérifie le nom de l’aliment via {@link Food#getName()}.
     */
    @Test
    @DisplayName("Doit retourner le nom correct de l’aliment")
    void testGetName() {
        food = new Food(Foods.BOAR);
        assertEquals("Boar", food.getName());

        food = new Food(Foods.WINE);
        assertEquals("Wine", food.getName());
    }

    /**
     * Vérifie la méthode {@link Food#toString()} avec la fraîcheur.
     */
    @Test
    @DisplayName("Doit retourner un toString correct avec la fraîcheur")
    void testToString() {
        food = new Food(Foods.BOAR);
        assertEquals("Boar (Fresh)", food.toString());

        food.degrade();
        assertEquals("Boar (Quite Fresh)", food.toString());

        food.degrade();
        assertEquals("Boar (Rotten)", food.toString());
    }

    /**
     * Vérifie que la consommation d’un aliment ne lance pas d’exception.
     */
    @Test
    @DisplayName("Doit consommer un aliment sans lancer d’exception")
    void testConsumeFood() {
        food = new Food(Foods.BOAR);
        assertDoesNotThrow(() -> food.consume(testCharacter));
    }

    /**
     * Vérifie que la consommation d’un aliment frais augmente la faim.
     */
    @Test
    @DisplayName("Doit augmenter la faim lors de la consommation d’un aliment frais")
    void testConsumeFreshFood() {
        food = new Food(Foods.BOAR, Freshness.FRESH);
        int initialHunger = testCharacter.getHunger();
        int nutritionalValue = food.getNutritionalValue();

        food.consume(testCharacter);

        assertEquals(initialHunger + nutritionalValue, testCharacter.getHunger());
    }

    /**
     * Vérifie que la consommation d’un aliment pourri diminue santé et faim.
     */
    @Test
    @DisplayName("Doit diminuer la santé et la faim lors de la consommation d’un aliment pourri")
    void testConsumeRottenFood() {
        food = new Food(Foods.BOAR, Freshness.ROTTEN);
        int initialHealth = testCharacter.getHealth();
        int initialHunger = testCharacter.getHunger();

        food.consume(testCharacter);

        assertEquals(initialHealth - (initialHealth / 5), testCharacter.getHealth());
        assertEquals(initialHunger - (initialHunger / 3), testCharacter.getHunger());
    }

    /**
     * Vérifie la consommation spécifique du poisson pourri.
     */
    @Test
    @DisplayName("Doit gérer la consommation du poisson pourri")
    void testRottenFishConsumption() {
        food = new Food(Foods.FRESH_FISH, Freshness.ROTTEN);
        int initialHealth = testCharacter.getHealth();
        int initialHunger = testCharacter.getHunger();

        food.consume(testCharacter);

        assertTrue(testCharacter.getHealth() < initialHealth);
        assertTrue(testCharacter.getHunger() < initialHunger);
    }

    /**
     * Vérifie que la consommation d’un aliment OKAY augmente correctement la faim.
     */
    @Test
    @DisplayName("Doit augmenter la faim lors de la consommation d’un aliment OKAY")
    void testConsumeOkayFood() {
        food = new Food(Foods.CARROT, Freshness.OKAY);
        int initialHunger = testCharacter.getHunger();
        int nutritionalValue = food.getNutritionalValue();

        food.consume(testCharacter);

        assertEquals(initialHunger + nutritionalValue, testCharacter.getHunger());
    }

    /**
     * Vérifie le type de Foods associé à un aliment.
     */
    @Test
    @DisplayName("Doit retourner le type de Foods correct")
    void testGetFoods() {
        food = new Food(Foods.LOBSTER);
        assertEquals(Foods.LOBSTER, food.getFoods());
        assertEquals(FoodType.MEATS, food.getFoods().getType());
    }

    /**
     * Vérifie la création d’aliments de différents types (viande, légume, liquide, épice).
     */
    @Test
    @DisplayName("Doit créer différents types d’aliments")
    void testDifferentFoodTypes() {
        assertEquals(FoodType.MEATS, new Food(Foods.BOAR).getFoods().getType());
        assertEquals(FoodType.VEGETABLES, new Food(Foods.CARROT).getFoods().getType());
        assertEquals(FoodType.LIQUIDS, new Food(Foods.WINE).getFoods().getType());
        assertEquals(FoodType.SPICES, new Food(Foods.SALT).getFoods().getType());
    }

    /**
     * Vérifie la gestion de la fraîcheur OKAY.
     */
    @Test
    @DisplayName("Doit gérer la fraîcheur OKAY")
    void testOkayFreshness() {
        food = new Food(Foods.STRAWBERRY, Freshness.OKAY);
        assertEquals(Freshness.OKAY, food.getFreshness());
        assertEquals("Strawberries (Quite Fresh)", food.toString());
    }

    /**
     * Vérifie que Food est bien une sous-classe de Consumable.
     */
    @Test
    @DisplayName("Doit vérifier que l’aliment est consommable")
    void testFoodIsConsumable() {
        food = new Food(Foods.MEAD);
        assertTrue(food instanceof Consumable);
    }

    /**
     * Vérifie la dégradation sur plusieurs cycles successifs.
     */
    @Test
    @DisplayName("Doit tester plusieurs cycles de dégradation")
    void testMultipleDegradationCycles() {
        food = new Food(Foods.HONEY);

        for (int i = 0; i < 5; i++) {
            food.degrade();
        }

        assertEquals(Freshness.ROTTEN, food.getFreshness());
    }

    /**
     * Vérifie que le type et le nom de l’aliment restent corrects après dégradation.
     */
    @Test
    @DisplayName("Doit maintenir le type de l’aliment après dégradation")
    void testFoodTypeMaintainedAfterDegradation() {
        food = new Food(Foods.BOAR);
        Foods originalFood = food.getFoods();

        food.degrade();
        food.degrade();

        assertEquals(originalFood, food.getFoods());
        assertEquals("Boar", food.getName());
    }
}
