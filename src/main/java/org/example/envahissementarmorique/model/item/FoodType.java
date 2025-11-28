package org.example.envahissementarmorique.model.item;

/**
 * Enumeration representing the different categories of food available in the simulation.
 * This classification helps manage consumption rules (e.g., specific diets or rules for vegetables).
 */
public enum FoodType {

    LIQUIDS("Liquids"),
    MEATS("Meats"),
    VEGETABLES("Vegetables"),
    SPICES("Spices"),
    SECRET_INGREDIENTS("Secret Ingredients");

    private final String label;

    /**
     * Constructor for the food category.
     *
     * @param label The display name of the category.
     */
    FoodType(String label) {
        this.label = label;
    }

    /**
     * Retrieves the label of the category.
     *
     * @return The name of the category as a String.
     */
    public String getLabel() {
        return label;
    }
}