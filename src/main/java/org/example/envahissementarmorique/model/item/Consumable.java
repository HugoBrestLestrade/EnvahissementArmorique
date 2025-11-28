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
     * The name of the consumable (e.g., "Sanglier", "Potion Magique").
     */
    protected final String name;

    /**
     * Constructor for a consumable item.
     *
     * @param name The name of the item (cannot be null).
     */
    public Consumable(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Consumable name cannot be empty.");
        }
        this.name = name;
    }

    /**
     * Gets the name of the consumable.
     *
     * @return The name as a String.
     */
    public String getName() {
        return name;
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
        return this.name;
    }
}