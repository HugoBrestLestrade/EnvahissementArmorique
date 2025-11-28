package org.example.envahissementarmorique.model.item;

/**
 * List of the different foods with their label and nutrition
 * label: Label of the food
 * nutrition: Level of nutrition of the food
 */
public enum FoodType {
    // Meats & Fish
    BOAR("Boar", 50),
    FRESH_FISH("Fresh Fish", 20), // "Poisson passablement frais"
    ROTTEN_FISH("Rotten Fish", 5), // "Poisson pas frais"  - Mauvais pour la santé [cite: 46]
    LOBSTER("Lobster", 30), // Nourrissant [cite: 49]

    // Vegetables & Plants
    CARROT("Carrots", 5),
    STRAWBERRY("Strawberries", 5), // Nourrissant [cite: 49]
    MISTLETOE("Mistletoe", 0),
    FRESH_FOUR_LEAF_CLOVER("Fresh four-leaf clover", 2), // "Trèfle à quatre feuilles frais"
    ROTTEN_FOUR_LEAF_CLOVER("Rotten four-leaf clover", 0), // "Trèfle à quatre feuilles pas frais"

    // Drinks, Sweets & Liquids
    HONEY("Honey", 15),
    WINE("Wine", 10),
    MEAD("Mead", 15),
    ROCK_OIL("Rock oil", 0), // Ingrédient
    BEET_JUICE("Beet juice", 15), // Substitut nourrissant à l'huile de roche [cite: 49]
    TWO_HEADED_UNICORN_MILK("Two-headed unicorn milk", 10), // Octroie le pouvoir de dédoublement

    // Spices & Special Ingredients
    SALT("Salt", 1),
    IDEFIX_HAIR("Idéfix's hair", 0), // Octroie le pouvoir de métamorphosis
    SECRET_INGREDIENT("Secret ingredient", 0); // Requis pour la potion

    private final String label;
    private final int nutrition;

    FoodType(String label, int nutrition) {
        this.label = label;
        this.nutrition = nutrition;
    }

    public String getLabel() {
        return label;
    }

    public int getNutrition() {
        return nutrition;
    }
}