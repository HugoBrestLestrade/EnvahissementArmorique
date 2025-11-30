package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.Character;

/**
 * Represents an item that can be consumed by a character in the simulation.
 * <p>
 * This class is sealed to strictly control the hierarchy: only Food and Potion
 * are allowed types of consumables, matching the TD3 specifications.
 * </p>
 */
public abstract sealed class Consumable permits Food, Potion {

    /**
     * The type of the food (e.g., BOAR, FRESH_FISH, WINE, etc.).
     */
    protected final FoodType foodType;

    /**
     * Constructor for a consumable item.
     *
     * @param foodType The type of food (cannot be null).
     */
    public Consumable(FoodType foodType) {
        if (foodType == null) {
            throw new IllegalArgumentException("FoodType cannot be null.");
        }
        this.foodType = foodType;
    }

    /**
     * Gets the type of the consumable.
     *
     * @return The FoodType.
     */
    public FoodType getFoodType() {
        return foodType;
    }

    /**
     * Gets the name of the consumable.
     *
     * @return The name as a String.
     */
    public String getName() {
        return foodType.getLabel();
    }

    /**
     * Applies the effect of this consumable on the specified character.
     * <p>
     * According to TD3:
     * <ul>
     * <li>Food improves the hunger indicator.</li>
     * <li>Potion increases the magic potion level.</li>
     * </ul>
     * </p>
     *
     * @param consumer The character consuming the item.
     */
    public abstract void consume(Character consumer);

    @Override
    public String toString() {
        return foodType.getLabel();
    }
}