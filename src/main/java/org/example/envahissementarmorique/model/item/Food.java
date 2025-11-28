package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.Character;

/**
 * Represents a food item in the simulation.
 * <p>
 * According to the TD3 specifications:
 * <ul>
 * <li>Food improves the hunger indicator[cite: 128].</li>
 * <li>Food can be fresh or not fresh. Eating not fresh food (like fish) impacts health.</li>
 * <li>Food freshness changes over time.</li>
 * </ul>
 * </p>
 */
public final class Food extends Consumable {

    private final int nutritionalValue;
    private boolean isFresh;

    /**
     * Creates a new Food item.
     * By default, food appears fresh when created[cite: 204].
     *
     * @param name The name of the food (e.g., "Sanglier", "Poisson").
     * @param nutritionalValue The amount of hunger this food removes.
     */
    public Food(String name, int nutritionalValue) {
        super(name);
        this.nutritionalValue = nutritionalValue;
        this.isFresh = true;
    }


}