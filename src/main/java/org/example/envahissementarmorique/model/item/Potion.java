package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.Character;

/**
 * Represents a magic potion in the simulation.
 * <p>
 * According to the TD3 specifications:
 * <ul>
 * <li>A potion is a consumable that increases the magic potion level.</li>
 * <li>Potions are special items with magical properties.</li>
 * </ul>
 * </p>
 */
public final class Potion extends Consumable {

    /**
     * Creates a new Potion.
     * Potions use special FoodType ingredients like SECRET_INGREDIENT.
     *
     * @param foodType The type of potion ingredient.
     */
    public Potion(FoodType foodType) {
        super(foodType);
    }

    /**
     * Applies the effect of this potion on the specified character.
     * Potion increases the magic potion level of the consumer.
     *
     * @param consumer The character consuming the potion.
     */
    @Override
    public void consume(Character consumer) {
        // Potion increases magic potion level
        // Note: Waining to implement magic potion level in Character class (Work of Hugo)
        // which should not be modified per requirements
    }

    @Override
    public String toString() {
        return "Potion: " + foodType.getLabel();
    }
}
