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
     * @param consumer The character consuming the food.
     */
    @Override
    public void consume(Character consumer) {
        if (freshness == Freshness.ROTTEN) {

            int healthLoss = consumer.getHealth() / 5;
            consumer.setHealth(consumer.getHealth() - healthLoss);

            int hungerLoss = consumer.getHunger() / 3;
            consumer.setHunger(consumer.getHunger() - hungerLoss);
        } else if (consumer.getHunger() < 100) {
            int newHunger = consumer.getHunger() + foods.getNutrition();
            consumer.setHunger(newHunger);
        } else {
            System.out.println(consumer.getName() + " is already full and cannot eat more !");
        }

    }

    @Override
    public String toString() {
        return foods.getLabel() + " (" + freshness.getLabel() + ")";
    }
}