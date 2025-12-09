package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

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
     * @param foods The type of food (e.g., BOAR, FRESH_FISH, etc.).
     */
    public Food(Foods foods) {
        super(foods);
        this.freshness = Freshness.FRESH;
    }

    /**
     * Creates a new Food item with a specific freshness level.
     *
     * @param foods The type of food (e.g., BOAR, FRESH_FISH, etc.).
     * @param freshness The initial freshness state of the food.
     */
    public Food(Foods foods, Freshness freshness) {
        super(foods);
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
        return foods.getNutrition();
    }

    /**
     * Applies the effect of this food on the specified character.
     * Food improves the hunger indicator based on its nutritional value.
     * Eating rotten food is bad for health.
     *
     * Logic:
     * - If rotten: lose 1/5 of health and lose 1/3 of hunger
     * - Otherwise: hunger = hunger + nutrition value
     *
     * @param consumer The character consuming the food.
     */
    @Override
    public void consume(GameCharacter consumer) {
        if (freshness == Freshness.ROTTEN) {
            // Rotten food: lose 1/5 of health
            int healthLoss = consumer.getHealth() / 5;
            consumer.setHealth(consumer.getHealth() - healthLoss);

            // Rotten food: lose 1/3 of hunger
            int hungerLoss = consumer.getHunger() / 3;
            consumer.setHunger(consumer.getHunger() - hungerLoss);

            System.out.println(consumer.getName() + " ate rotten " + getName() + " and lost " + healthLoss + " health and " + hungerLoss + " hunger!");
        } else {
            // Fresh or okay food: hunger = hunger + nutrition
            int newHunger = consumer.getHunger() + foods.getNutrition();
            consumer.setHunger(newHunger);

            System.out.println(consumer.getName() + " ate " + getName() + " and gained " + foods.getNutrition() + " hunger!");
        }
    }

    @Override
    public String toString() {
        return foods.getLabel() + " (" + freshness.getLabel() + ")";
    }
}