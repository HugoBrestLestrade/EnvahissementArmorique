package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

import java.util.EnumSet;
import java.util.Set;

/**
 * Represents a magic potion in the simulation.
 * <p>
 * Recipe of the magic potion:
 * <ul>
 * <li>Mistletoe (Gui)</li>
 * <li>Carrots (Carottes)</li>
 * <li>Salt (Sel)</li>
 * <li>Fresh four-leaf clover (Trèfle à quatre feuilles frais)</li>
 * <li>Passably fresh fish (Poisson passablement frais)</li>
 * <li>Rock oil (Huile de roche)</li>
 * <li>Honey (Miel)</li>
 * <li>Mead (Hydromel)</li>
 * <li>Secret ingredient (Ingrédient secret)</li>
 * </ul>
 * </p>
 * <p>
 * Variants:
 * <ul>
 * <li>Adding lobster or strawberries is nourishing</li>
 * <li>Replacing rock oil with beet juice is also a valid variant</li>
 * <li>Adding two-headed unicorn milk grants the power of duplication</li>
 * <li>Adding Idéfix's hair grants metamorphosis power (becoming a lycanthrope)</li>
 * </ul>
 * </p>
 * <p>
 * Effects:
 * <ul>
 * <li>1 dose: Temporary superhuman strength and invincibility</li>
 * <li>1 cauldron (multiple doses): Permanent effects</li>
 * <li>2 cauldrons: Transforms into a granite statue</li>
 * </ul>
 * </p>
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
            Foods.FRESH_FISH,  // Passably fresh fish
            Foods.ROCK_OIL,
            Foods.HONEY,
            Foods.MEAD,
            Foods.SECRET_INGREDIENT
    );

    /**
     * Number of doses in a cauldron.
     */
    public static final int DOSES_PER_CAULDRON = 10;

    /**
     * Strength boost given by one dose of potion.
     */
    public static final int DOSE_STRENGTH_BOOST = 100;

    /**
     * Number of doses in this potion container.
     */
    private int doses;

    /**
     * Whether the potion contains two-headed unicorn milk (duplication power).
     */
    private boolean hasUnicornMilk;

    /**
     * Whether the potion contains Idéfix's hair (metamorphosis/lycanthrope power).
     */
    private boolean hasIdefixHair;

    /**
     * Whether the potion uses beet juice instead of rock oil (nutritious variant).
     */
    private boolean usesBeetJuiceVariant;

    /**
     * Whether the potion contains lobster or strawberries (nourishing variant).
     */
    private boolean hasNourishingIngredient;

    /**
     * Creates a new Potion with default single dose.
     * Potions use special Foods ingredients like SECRET_INGREDIENT.
     *
     * @param foods The type of potion ingredient.
     */
    public Potion(Foods foods) {
        super(foods);
        this.doses = 1;
        this.hasUnicornMilk = false;
        this.hasIdefixHair = false;
        this.usesBeetJuiceVariant = false;
        this.hasNourishingIngredient = false;
    }

    /**
     * Creates a new Potion with a specified number of doses.
     *
     * @param foods The type of potion ingredient.
     * @param doses The number of doses in this potion container.
     */
    public Potion(Foods foods, int doses) {
        super(foods);
        if (doses < 1) {
            throw new IllegalArgumentException("Doses must be at least 1.");
        }
        this.doses = doses;
        this.hasUnicornMilk = false;
        this.hasIdefixHair = false;
        this.usesBeetJuiceVariant = false;
        this.hasNourishingIngredient = false;
    }

    /**
     * Creates a full cauldron of potion.
     *
     * @return A new Potion with a full cauldron (DOSES_PER_CAULDRON doses).
     */
    public static Potion createCauldron() {
        return new Potion(Foods.SECRET_INGREDIENT, DOSES_PER_CAULDRON);
    }

    /**
     * Applies the effect of this potion on the specified character.
     * <p>
     * Effects based on doses consumed:
     * <ul>
     * <li>1 dose: Temporary superhuman strength (+100 strength) and invincibility</li>
     * <li>1 cauldron: Effects become permanent</li>
     * <li>2 cauldrons: Transforms into granite statue (health = 0, character is petrified)</li>
     * </ul>
     * Additional effects with special ingredients:
     * <ul>
     * <li>Two-headed unicorn milk: Grants duplication power</li>
     * <li>Idéfix's hair: Grants metamorphosis power (lycanthrope transformation)</li>
     * </ul>
     * </p>
     *
     * @param consumer The character consuming the potion.
     */
    @Override
    public void consume(GameCharacter consumer) {
        int currentPotionLevel = consumer.getMagicpotion();
        int newPotionLevel = currentPotionLevel + doses;

        // Check if character will be transformed into granite statue (2+ cauldrons)
        if (newPotionLevel >= 2 * DOSES_PER_CAULDRON) {
            // Transform into granite statue - character becomes petrified
            consumer.setHealth(0);
            consumer.setStrength(0);
            consumer.setEndurance(0);
            consumer.setMagicpotion(newPotionLevel);
            return;
        }

        // Apply strength boost
        int strengthBoost = doses * DOSE_STRENGTH_BOOST;
        consumer.setStrength(consumer.getStrength() + strengthBoost);

        // If consuming a full cauldron or more, effects are permanent (represented by high potion level)
        if (newPotionLevel >= DOSES_PER_CAULDRON) {
            // Permanent superhuman strength and invincibility
            consumer.setEndurance(consumer.getEndurance() + 1000); // Invincibility boost
        }

        // Apply nourishing effect if variant is used
        if (hasNourishingIngredient || usesBeetJuiceVariant) {
            int nutritionBoost = 20 * doses;
            consumer.setHunger(Math.min(100, consumer.getHunger() + nutritionBoost));
        }

        // Update magic potion level
        consumer.setMagicpotion(newPotionLevel);

        // Decrease doses after consumption
        this.doses = 0;
    }

    /**
     * Checks if the given set of ingredients forms a valid base recipe.
     *
     * @param ingredients The set of ingredients to check.
     * @return true if all base recipe ingredients are present.
     */
    public static boolean isValidRecipe(Set<Foods> ingredients) {
        return ingredients.containsAll(BASE_RECIPE);
    }

    /**
     * Checks if the given set of ingredients forms a valid variant recipe.
     * Valid variants include:
     * - Replacing rock oil with beet juice
     * - Adding lobster or strawberries for nourishment
     *
     * @param ingredients The set of ingredients to check.
     * @return true if the ingredients form a valid variant recipe.
     */
    public static boolean isValidVariantRecipe(Set<Foods> ingredients) {
        // Check for beet juice variant (rock oil replaced by beet juice)
        Set<Foods> beetJuiceVariant = EnumSet.copyOf(BASE_RECIPE);
        beetJuiceVariant.remove(Foods.ROCK_OIL);
        beetJuiceVariant.add(Foods.BEET_JUICE);

        if (ingredients.containsAll(beetJuiceVariant)) {
            return true;
        }

        // Check for base recipe with nourishing additions
        if (ingredients.containsAll(BASE_RECIPE)) {
            return ingredients.contains(Foods.LOBSTER) || ingredients.contains(Foods.STRAWBERRY);
        }

        return false;
    }

    // Getters and Setters for variant properties

    /**
     * Gets the number of doses remaining.
     *
     * @return The number of doses.
     */
    public int getDoses() {
        return doses;
    }

    /**
     * Sets the number of doses.
     *
     * @param doses The number of doses.
     */
    public void setDoses(int doses) {
        this.doses = doses;
    }

    /**
     * Checks if this potion contains two-headed unicorn milk.
     *
     * @return true if the potion has unicorn milk (duplication power).
     */
    public boolean hasUnicornMilk() {
        return hasUnicornMilk;
    }

    /**
     * Adds two-headed unicorn milk to the potion.
     * Grants the power of duplication.
     */
    public void addUnicornMilk() {
        this.hasUnicornMilk = true;
    }

    /**
     * Checks if this potion contains Idéfix's hair.
     *
     * @return true if the potion has Idéfix's hair (metamorphosis power).
     */
    public boolean hasIdefixHair() {
        return hasIdefixHair;
    }

    /**
     * Adds Idéfix's hair to the potion.
     * Grants the power of metamorphosis (becoming a lycanthrope).
     */
    public void addIdefixHair() {
        this.hasIdefixHair = true;
    }

    /**
     * Checks if this potion uses beet juice instead of rock oil.
     *
     * @return true if using the beet juice variant.
     */
    public boolean usesBeetJuiceVariant() {
        return usesBeetJuiceVariant;
    }

    /**
     * Sets the potion to use beet juice variant instead of rock oil.
     */
    public void setBeetJuiceVariant() {
        this.usesBeetJuiceVariant = true;
    }

    /**
     * Checks if this potion has nourishing ingredients (lobster or strawberries).
     *
     * @return true if the potion has nourishing ingredients.
     */
    public boolean hasNourishingIngredient() {
        return hasNourishingIngredient;
    }

    /**
     * Adds nourishing ingredients (lobster or strawberries) to the potion.
     */
    public void addNourishingIngredient() {
        this.hasNourishingIngredient = true;
    }

    /**
     * Checks if this potion grants duplication power.
     *
     * @return true if the consumer will gain duplication power.
     */
    public boolean grantsDuplicationPower() {
        return hasUnicornMilk;
    }

    /**
     * Checks if this potion grants metamorphosis power (lycanthrope).
     *
     * @return true if the consumer will gain metamorphosis power.
     */
    public boolean grantsMetamorphosisPower() {
        return hasIdefixHair;
    }

    /**
     * Gets the number of full cauldrons this potion represents.
     *
     * @return The number of cauldrons.
     */
    public int getCauldronCount() {
        return doses / DOSES_PER_CAULDRON;
    }

    /**
     * Checks if this is a full cauldron.
     *
     * @return true if doses equals or exceeds DOSES_PER_CAULDRON.
     */
    public boolean isFullCauldron() {
        return doses >= DOSES_PER_CAULDRON;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Potion: ");
        sb.append(foods.getLabel());
        sb.append(" (").append(doses).append(" dose(s))");

        if (hasUnicornMilk) {
            sb.append(" [+Duplication]");
        }
        if (hasIdefixHair) {
            sb.append(" [+Metamorphosis]");
        }
        if (usesBeetJuiceVariant) {
            sb.append(" [Beet Juice Variant]");
        }
        if (hasNourishingIngredient) {
            sb.append(" [Nourishing]");
        }

        return sb.toString();
    }
}
