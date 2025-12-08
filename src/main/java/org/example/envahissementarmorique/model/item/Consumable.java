package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

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
    protected final Foods foods;

    /**
     * Constructor for a consumable item.
     *
     * @param foods The type of food (cannot be null).
     */
    public Consumable(Foods foods) {
        if (foods == null) {
            throw new IllegalArgumentException("Foods cannot be null.");
        }
        this.foods = foods;
    }

    /**
     * Gets the type of the consumable.
     *
     * @return The Foods.
     */
    public Foods getFoods() {
        return foods;
    }

    /**
     * Gets the name of the consumable.
     *
     * @return The name as a String.
     */
    public String getName() {
        return foods.getLabel();
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
    public abstract void consume(GameCharacter consumer);

    @Override
    public String toString() {
        return foods.getLabel();
    }
}