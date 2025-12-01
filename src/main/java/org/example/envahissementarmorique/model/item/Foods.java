package org.example.envahissementarmorique.model.item;

/**
 * List of the different foods with their label, nutrition, and category.
 * label: Label of the food
 * nutrition: Level of nutrition of the food
 * type: The category of the food (e.g., MEATS, VEGETABLES)
 */
public enum Foods {
    // Meats & Fish
    BOAR("Boar", 50, FoodType.MEATS),
    FRESH_FISH("Fresh Fish", 20, FoodType.MEATS),
    ROTTEN_FISH("Rotten Fish", 5, FoodType.MEATS),
    LOBSTER("Lobster", 30, FoodType.MEATS),

    // Vegetables & Plants
    CARROT("Carrots", 5, FoodType.VEGETABLES),
    STRAWBERRY("Strawberries", 5, FoodType.VEGETABLES),
    MISTLETOE("Mistletoe", 0, FoodType.VEGETABLES),
    FRESH_FOUR_LEAF_CLOVER("Fresh four-leaf clover", 2, FoodType.VEGETABLES),
    ROTTEN_FOUR_LEAF_CLOVER("Rotten four-leaf clover", 0, FoodType.VEGETABLES),

    // Drinks, Sweets & Liquids
    HONEY("Honey", 15, FoodType.LIQUIDS),
    WINE("Wine", 10, FoodType.LIQUIDS),
    MEAD("Mead", 15, FoodType.LIQUIDS),
    ROCK_OIL("Rock oil", 0, FoodType.LIQUIDS),
    BEET_JUICE("Beet juice", 15, FoodType.LIQUIDS),
    TWO_HEADED_UNICORN_MILK("Two-headed unicorn milk", 10, FoodType.LIQUIDS),

    // Spices & Special Ingredients
    SALT("Salt", 1, FoodType.SPICES),
    IDEFIX_HAIR("Idéfix's hair", 0, FoodType.SPICES),
    SECRET_INGREDIENT("Secret ingredient", 0, FoodType.SECRET_INGREDIENTS);

    private final String label;
    private final int nutrition;
    private final FoodType type;

    /**
     * Constructor to define the properties of a food item.
     *
     * @param label     The display name of the food (e.g., "Boar").
     * @param nutrition The nutritional value of the food.
     * This value determines how much the hunger indicator
     * of a character improves when consuming this food.
     * @param type      The category the food belongs to.
     */
    Foods(String label, int nutrition, FoodType type) {
        this.label = label;
        this.nutrition = nutrition;
        this.type = type;
    }

    /**
     * Retrieves the label of the food.
     * Useful for displaying the characteristics of a location or an inventory.
     *
     * @return The name of the food as a String.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Retrieves the nutritional value of the food.
     *
     * @return An integer representing the nutritional intake.
     * A higher value satisfies the character more.
     */
    public int getNutrition() {
        return nutrition;
    }

    /**
     * Retrieves the category (FoodType) of the food.
     * Useful for applying dietary rules (e.g., not eating vegetables twice in a row).
     *
     * @return The category of the food.
     */
    public FoodType getType() {
        return type;
    }
}