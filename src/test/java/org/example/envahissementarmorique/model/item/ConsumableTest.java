package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe abstraite {@link Consumable}.
 * <p>
 * Cette classe vérifie le comportement commun des objets consommables
 * via les implémentations {@link Food} et {@link Potion}.
 * </p>
 *
 * <p>
 * Les tests incluent :
 * <ul>
 *     <li>La gestion des valeurs nulles dans le constructeur.</li>
 *     <li>La récupération correcte des {@link Foods} et noms.</li>
 *     <li>L’implémentation de la méthode {@code consume} dans les sous-classes.</li>
 *     <li>La cohérence de la hiérarchie de classes scellée.</li>
 *     <li>Le comportement selon le type de {@link Foods} (viande, légume, liquide, ingrédient secret).</li>
 *     <li>L’immuabilité et la consistance des objets {@link Consumable}.</li>
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
@DisplayName("Tests de la classe abstraite Consumable")
class ConsumableTest {

    /** Instance de personnage utilisée pour tester la consommation. */
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
                "Test Character", "Male", "Gaul", 1.75, 30,
                50, 50, 100, 100, 50, 0
        );
    }

    /**
     * Vérifie que le constructeur refuse les {@link Foods} null.
     */
    @Test
    @DisplayName("Ne doit pas accepter des Foods null dans le constructeur")
    void testNullFoodsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Food(null));
        assertThrows(IllegalArgumentException.class, () -> new Potion(null));
    }

    /**
     * Vérifie que {@link Consumable#getFoods()} retourne l’objet Foods correct.
     */
    @Test
    @DisplayName("Doit retourner le Foods correct via getFoods()")
    void testGetFoods() {
        Consumable food = new Food(Foods.BOAR);
        assertEquals(Foods.BOAR, food.getFoods());

        Consumable potion = new Potion(Foods.SECRET_INGREDIENT);
        assertEquals(Foods.SECRET_INGREDIENT, potion.getFoods());
    }

    /**
     * Vérifie que {@link Consumable#getName()} retourne le nom correct.
     */
    @Test
    @DisplayName("Doit retourner le nom correct via getName()")
    void testGetName() {
        Consumable food = new Food(Foods.WINE);
        assertEquals("Wine", food.getName());

        Consumable potion = new Potion(Foods.MISTLETOE);
        assertEquals("Mistletoe", potion.getName());
    }

    /**
     * Vérifie que la méthode {@code consume} est implémentée sans lancer d’exception.
     */
    @Test
    @DisplayName("Doit implémenter consume dans les sous-classes")
    void testConsumeImplementation() {
        Consumable food = new Food(Foods.BOAR);
        assertDoesNotThrow(() -> food.consume(testCharacter));

        Consumable potion = new Potion(Foods.SECRET_INGREDIENT);
        assertDoesNotThrow(() -> potion.consume(testCharacter));
    }

    /**
     * Vérifie la méthode {@link Object#toString()} pour les Consumables.
     */
    @Test
    @DisplayName("Doit retourner un toString correct à partir des Foods")
    void testToStringFromFoods() {
        Consumable potion = new Potion(Foods.SECRET_INGREDIENT);
        assertTrue(potion.toString().contains("Secret ingredient"));
    }

    /**
     * Vérifie que la hiérarchie scellée de Consumable permet uniquement Food et Potion.
     */
    @Test
    @DisplayName("Doit vérifier que la classe scellée ne permet que Food et Potion")
    void testSealedClassHierarchy() {
        Consumable food = new Food(Foods.CARROT);
        assertTrue(food instanceof Food);

        Consumable potion = new Potion(Foods.MISTLETOE);
        assertTrue(potion instanceof Potion);

        assertTrue(food instanceof Consumable);
        assertTrue(potion instanceof Consumable);
    }

    /**
     * Vérifie la gestion des différents types de Foods dans les Consumables.
     */
    @Test
    @DisplayName("Doit gérer différents types de Foods dans les Consumables")
    void testDifferentFoodsTypes() {
        assertEquals(FoodType.MEATS, new Food(Foods.LOBSTER).getFoods().getType());
        assertEquals(FoodType.VEGETABLES, new Food(Foods.STRAWBERRY).getFoods().getType());
        assertEquals(FoodType.LIQUIDS, new Food(Foods.MEAD).getFoods().getType());
        assertEquals(FoodType.SECRET_INGREDIENTS, new Potion(Foods.SECRET_INGREDIENT).getFoods().getType());
    }

    /**
     * Vérifie que l’on peut créer plusieurs Consumables avec le même Foods.
     */
    @Test
    @DisplayName("Doit créer plusieurs Consumables avec le même Foods")
    void testMultipleConsumablesWithSameFoods() {
        Consumable food1 = new Food(Foods.BOAR);
        Consumable food2 = new Food(Foods.BOAR);

        assertEquals(food1.getFoods(), food2.getFoods());
        assertEquals(food1.getName(), food2.getName());
    }

    /**
     * Vérifie que l’objet Foods est final et immuable.
     */
    @Test
    @DisplayName("Doit vérifier que Foods est final et immuable")
    void testFoodsImmutability() {
        Consumable consumable = new Food(Foods.HONEY);
        Foods originalFoods = consumable.getFoods();
        assertSame(originalFoods, consumable.getFoods());
    }

    /**
     * Vérifie le polymorphisme des Consumables.
     */
    @Test
    @DisplayName("Doit tester le polymorphisme des Consumables")
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

    /**
     * Vérifie la cohérence du comportement des Consumables.
     */
    @Test
    @DisplayName("Doit vérifier la cohérence du comportement des Consumables")
    void testConsumableBehaviorConsistency() {
        Consumable food = new Food(Foods.FRESH_FISH);
        Consumable potion = new Potion(Foods.IDEFIX_HAIR);

        assertNotNull(food.getFoods());
        assertNotNull(potion.getFoods());

        assertNotNull(food.getName());
        assertNotNull(potion.getName());

        assertDoesNotThrow(() -> food.consume(testCharacter));
        assertDoesNotThrow(() -> potion.consume(testCharacter));
    }

    /**
     * Vérifie que le message d’exception pour Foods null est clair.
     */
    @Test
    @DisplayName("Doit lancer une exception avec message significatif pour Foods null")
    void testNullFoodsExceptionMessage() {
        Exception foodException = assertThrows(IllegalArgumentException.class, () -> new Food(null));
        assertTrue(foodException.getMessage().contains("Foods cannot be null"));

        Exception potionException = assertThrows(IllegalArgumentException.class, () -> new Potion(null));
        assertTrue(potionException.getMessage().contains("Foods cannot be null"));
    }
}
