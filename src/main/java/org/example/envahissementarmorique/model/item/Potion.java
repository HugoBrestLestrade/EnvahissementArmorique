package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

import java.util.EnumSet;
import java.util.Set;

/**
 * Represents a magic potion in the simulation.
 * <p>
 * Recipe: gui (mistletoe), carottes, sel, trèfle à quatre feuilles frais,
 * poisson passablement frais, huile de roche, miel, hydromel, ingrédient secret.
 * <p>
 * Variants:
 * - Adding lobster or strawberries is nourishing
 * - Replacing rock oil with beet juice is also valid
 * - Adding two-headed unicorn milk grants duplication power
 * - Adding Idéfix's hair grants metamorphosis power
 * <p>
 * Effects:
 * - 1 dose: Temporary superhuman strength and invincibility
 * - 1 cauldron (marmite): Effects become permanent
 * - 2 cauldrons: Transforms into granite statue
 */
public final class Potion extends Consumable {

    /**
     * Base recipe ingredients for the magic potion.
     */
    public static final Set<Foods> BASE_RECIPE = EnumSet.of(
            Foods.MISTLETOE,
            Foods.CARROT,
            Foods.SALT,
            Foods.FRESH_FOUR_LEAF_CLOVER,
            Foods.FRESH_FISH,
            Foods.ROCK_OIL,
            Foods.HONEY,
            Foods.MEAD,
            Foods.SECRET_INGREDIENT
    );

    /**
     * Number of doses in a cauldron (marmite).
     */
    public static final int DOSES_PER_CAULDRON = 10;

    /**
     * Strength boost given by one dose of potion.
     */
    public static final int DOSE_STRENGTH_BOOST = 100;

    /**
     * Number of doses in this potion.
     */
    private int doses;

    /**
     * List of ingredients in this potion.
     */
    private java.util.List<Foods> ingredients;

    /**
     * Creates a new Potion with 1 dose.
     *
     * @param foods The type of potion ingredient.
     */
    public Potion(Foods foods) {
        super(foods);
        this.doses = 1;
        this.ingredients = new java.util.ArrayList<>();
        this.ingredients.add(foods);
    }

    /**
     * Creates a new Potion with specified doses.
     *
     * @param foods The type of potion ingredient.
     * @param doses The number of doses.
     */
    public Potion(Foods foods, int doses) {
        super(foods);
        this.doses = doses;
        this.ingredients = new java.util.ArrayList<>();
        this.ingredients.add(foods);
    }

    /**
     * Gets the number of doses.
     *
     * @return The number of doses.
     */
    public int getDoses() {
        return doses;
    }

    /**
     * Adds an ingredient to the potion.
     *
     * @param ingredient The ingredient to add.
     */
    public void addIngredient(Foods ingredient) {
        if (ingredient != null && !ingredients.contains(ingredient)) {
            ingredients.add(ingredient);
        }
    }

    /**
     * Gets the list of ingredients in this potion.
     *
     * @return The list of ingredients.
     */
    public java.util.List<Foods> getIngredients() {
        return new java.util.ArrayList<>(ingredients);
    }

    /**
     * Makes a character drink this potion.
     * This is an alias for consume() to match UML specification.
     *
     * @param character The character drinking the potion.
     */
    public void drink(GameCharacter character) {
        consume(character);
    }

    /**
     * Applies the effect of this potion on the specified character.
     * <p>
     * Effects:
     * - 1 dose: +100 strength, temporary invincibility
     * - 1 cauldron (10+ doses): Effects become permanent
     * - 2 cauldrons (20+ doses): Transforms into granite statue (health = 0)
     *
     * @param consumer The character consuming the potion.
     */
    @Override
    public void consume(GameCharacter consumer) {
        int currentPotionLevel = consumer.getMagicpotion();
        int newPotionLevel = currentPotionLevel + doses;

        // Check if character will be transformed into granite statue (2+ cauldrons = 20+ doses)
        if (newPotionLevel >= 2 * DOSES_PER_CAULDRON) {
            consumer.setHealth(0);
            consumer.setStrength(0);
            consumer.setEndurance(0);
            consumer.setMagicpotion(newPotionLevel);
            System.out.println(consumer.getName() + " has been transformed into a granite statue!");
            return;
        }

        // Apply strength boost
        int strengthBoost = doses * DOSE_STRENGTH_BOOST;
        consumer.setStrength(consumer.getStrength() + strengthBoost);

        // If consuming a full cauldron or more (10+ doses), effects become permanent
        if (newPotionLevel >= DOSES_PER_CAULDRON) {
            consumer.setEndurance(consumer.getEndurance() + 1000);
            System.out.println(consumer.getName() + " gains permanent superhuman strength and invincibility!");
        } else {
            System.out.println(consumer.getName() + " gains temporary superhuman strength!");
        }

        // Update magic potion level
        consumer.setMagicpotion(newPotionLevel);
    }

    @Override
    public String toString() {
        return "Potion: " + foods.getLabel() + " (" + doses + " doses)";
    }
}
