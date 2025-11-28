package org.example.envahissementarmorique.model.item;

/**
 * Defines the freshness state of a food item.
 * - Fresh
 * - Quite Fresh
 * - Rotten
 * @author Boudhib Mohamed-Amine
 * @version 1.0
 */

public enum Freshness {
    FRESH("Fresh"),
    OKAY("Quite Fresh"),
    ROTTEN("Rotten");

    private final String label;
    Freshness(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Advances the decay process of the food.
     * FRESH -> QUITE_FRESH -> ROTTEN -> ROTTEN
     * * @return The next state of freshness.
     */
    public Freshness degrade() {
        return switch(this) {
            case FRESH -> OKAY;
            case OKAY , ROTTEN -> ROTTEN;
        };
    }
}
