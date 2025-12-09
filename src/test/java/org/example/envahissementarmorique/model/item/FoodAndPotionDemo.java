package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

/**
 * Demonstration class for Food and Potion consumption.
 * Tests the consume() methods according to specifications.
 */
public class FoodAndPotionDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMO: Food and Potion Consumption ===\n");

        // Create a test character
        GameCharacter asterix = new GameCharacter(
                "Astérix", "M", "Gaulois",
                1.65, 35, 50, 30, 100, 100, 70, 0
        );

        System.out.println("Initial state:");
        displayCharacterStats(asterix);
        System.out.println();

        // === TEST 1: Eating fresh food ===
        System.out.println("=== TEST 1: Eating fresh boar ===");
        Food boar = new Food(Foods.BOAR, Freshness.FRESH);
        System.out.println("Eating: " + boar);
        boar.consume(asterix);
        displayCharacterStats(asterix);
        System.out.println();

        // === TEST 2: Eating rotten food ===
        System.out.println("=== TEST 2: Eating rotten fish ===");
        Food rottenFish = new Food(Foods.FRESH_FISH, Freshness.ROTTEN);
        System.out.println("Eating: " + rottenFish);
        int healthBefore = asterix.getHealth();
        int hungerBefore = asterix.getHunger();
        rottenFish.consume(asterix);
        System.out.println("Health lost: " + (healthBefore - asterix.getHealth()) + " (should be ~1/5)");
        System.out.println("Hunger lost: " + (hungerBefore - asterix.getHunger()) + " (should be ~1/3)");
        displayCharacterStats(asterix);
        System.out.println();

        // === TEST 3: Drinking 1 dose of potion using drink() ===
        System.out.println("=== TEST 3: Drinking 1 dose of potion with drink() ===");
        Potion potion1Dose = new Potion(Foods.SECRET_INGREDIENT, 1);
        System.out.println("Drinking: " + potion1Dose);
        System.out.println("Doses: " + potion1Dose.getDoses());
        int strengthBefore = asterix.getStrength();
        potion1Dose.drink(asterix);
        System.out.println("Strength gained: " + (asterix.getStrength() - strengthBefore) + " (expected: 100)");
        displayCharacterStats(asterix);
        System.out.println();

        // === TEST 4: Drinking 1 cauldron (10 doses) using ToDrinkPotion() ===
        System.out.println("=== TEST 4: Drinking 1 cauldron with ToDrinkPotion() ===");
        GameCharacter obelix = new GameCharacter(
                "Obélix", "M", "Gaulois",
                1.85, 35, 80, 50, 120, 100, 60, 0
        );
        Potion cauldron = new Potion(Foods.SECRET_INGREDIENT, 10);
        System.out.println("Drinking: " + cauldron);
        System.out.println("Doses: " + cauldron.getDoses());
        obelix.ToDrinkPotion(cauldron);
        displayCharacterStats(obelix);
        System.out.println();

        // === TEST 5: Drinking 2 cauldrons (20 doses) - transforms to statue ===
        System.out.println("=== TEST 5: Drinking 2 cauldrons (20 doses) - Statue transformation ===");
        GameCharacter romain = new GameCharacter(
                "Roman", "M", "Roman",
                1.75, 30, 40, 35, 100, 100, 50, 0
        );
        Potion twoLesCauldrons = new Potion(Foods.SECRET_INGREDIENT, 20);
        System.out.println("Drinking: " + twoLesCauldrons);
        System.out.println("Doses: " + twoLesCauldrons.getDoses());
        romain.ToDrinkPotion(twoLesCauldrons);
        displayCharacterStats(romain);
        System.out.println();

        // === TEST 6: Food freshness degradation ===
        System.out.println("=== TEST 6: Food freshness degradation ===");
        Food freshFish = new Food(Foods.FRESH_FISH, Freshness.FRESH);
        System.out.println("Initial: " + freshFish);
        freshFish.degrade();
        System.out.println("After 1 degrade: " + freshFish);
        freshFish.degrade();
        System.out.println("After 2 degrades: " + freshFish);
        freshFish.degrade();
        System.out.println("After 3 degrades: " + freshFish);
        System.out.println();

        System.out.println("=== END OF DEMO ===");
    }

    private static void displayCharacterStats(GameCharacter character) {
        System.out.println("  Name: " + character.getName());
        System.out.println("  Health: " + character.getHealth());
        System.out.println("  Hunger: " + character.getHunger());
        System.out.println("  Strength: " + character.getStrength());
        System.out.println("  Endurance: " + character.getEndurance());
        System.out.println("  Magic Potion Level: " + character.getMagicpotion());
    }
}

