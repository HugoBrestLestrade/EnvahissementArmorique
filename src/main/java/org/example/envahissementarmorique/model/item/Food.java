package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.Character;

/**
 * Represents a food item in the simulation.
 * <p>
 * According to the TD3 specifications:
 * <ul>
 * <li>Food improves the hunger indicator.</li>
 * <li>Food can be fresh or not fresh. Eating not fresh food (like fish) impacts health.</li>
 * <li>Food freshness changes over time.</li>
 * </ul>
 * </p>
 */
public final class Food extends Consumable {

    private Freshness freshness;

    /**
     * Creates a new Food item.
     * By default, food appears fresh when created.
     *
     * @param foodType The type of food (e.g., BOAR, FRESH_FISH, etc.).
     */
    public Food(FoodType foodType) {
        super(foodType);
        this.freshness = Freshness.FRESH;
    }

    /**
     * Creates a new Food item with a specific freshness level.
     *
     * @param foodType The type of food (e.g., BOAR, FRESH_FISH, etc.).
     * @param freshness The initial freshness state of the food.
     */
    public Food(FoodType foodType, Freshness freshness) {
        super(foodType);
        this.freshness = freshness;
    }

    /**
     * Gets the current freshness of the food.
     *
     * @return The freshness state.
     */
    public Freshness getFreshness() {
        return freshness;
    }

    /**
     * Degrades the freshness of the food over time.
     * FRESH -> OKAY -> ROTTEN -> ROTTEN
     */
    public void degrade() {
        this.freshness = this.freshness.degrade();
    }

    /**
     * Gets the nutritional value of this food based on its type.
     *
     * @return The nutritional value.
     */
    public int getNutritionalValue() {
        return foodType.getNutrition();
    }

    /**
     * Applies the effect of this food on the specified character.
     * Food improves the hunger indicator based on its nutritional value.
     * Eating rotten fish is bad for health.
     *
     * @param consumer The character consuming the food.
     */
    @Override
    public void consume(Character consumer) {
        // Food improves hunger based on nutritional value
        // Note: The actual implementation depends on Character class methods (Wainting Hugo's Work)
        // which should not be modified per requirements
        
        // Eating rotten fish or rotten food affects health negatively
        if (freshness == Freshness.ROTTEN && 
            (foodType == FoodType.ROTTEN_FISH || foodType == FoodType.FRESH_FISH)) {
            // Bad for health - implementation depends on Character class
        }
    }

    @Override
    public String toString() {
        return foodType.getLabel() + " (" + freshness.getLabel() + ")";
    }
}