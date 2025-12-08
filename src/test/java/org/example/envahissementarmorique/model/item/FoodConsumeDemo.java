package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.Character;

/**
 * Classe de démonstration pour tester la méthode consume de Food.
 */
public class FoodConsumeDemo {
    public static void main(String[] args) {
        // Créer un personnage de test
        Character hero = new Character(
            "Astérix", "Male", "Gaul", 1.65, 35,
            80, 70, 100, 20, 60, 0
        );

        System.out.println("=== État initial du personnage ===");
        System.out.println("Nom: " + hero.getName());
        System.out.println("Santé: " + hero.getHealth());
        System.out.println("Faim: " + hero.getHunger());

        // Test 1: Consommer de la nourriture fraîche
        System.out.println("\n=== Test 1: Consommer du sanglier frais ===");
        Food freshBoar = new Food(Foods.BOAR, Freshness.FRESH);
        System.out.println("Nourriture: " + freshBoar);
        System.out.println("Valeur nutritionnelle: " + freshBoar.getNutritionalValue());

        freshBoar.consume(hero);

        System.out.println("Après consommation:");
        System.out.println("Santé: " + hero.getHealth() + " (pas de changement attendu)");
        System.out.println("Faim: " + hero.getHunger() + " (devrait être 100 + 50 = 150)");

        // Réinitialiser le personnage
        hero.setHealth(100);
        hero.setHunger(100);

        // Test 2: Consommer de la nourriture pourrie
        System.out.println("\n=== Test 2: Consommer du poisson pourri ===");
        Food rottenFish = new Food(Foods.FRESH_FISH, Freshness.ROTTEN);
        System.out.println("Nourriture: " + rottenFish);
        System.out.println("État avant:");
        System.out.println("  Santé: " + hero.getHealth());
        System.out.println("  Faim: " + hero.getHunger());

        rottenFish.consume(hero);

        System.out.println("Après consommation:");
        System.out.println("  Santé: " + hero.getHealth() + " (devrait être 100 - 20 = 80)");
        System.out.println("  Faim: " + hero.getHunger() + " (devrait être 100 - 33 = 67)");

        // Test 3: Consommer de la nourriture "Quite Fresh"
        hero.setHealth(100);
        hero.setHunger(50);

        System.out.println("\n=== Test 3: Consommer des fraises 'Quite Fresh' ===");
        Food okayStrawberry = new Food(Foods.STRAWBERRY, Freshness.OKAY);
        System.out.println("Nourriture: " + okayStrawberry);
        System.out.println("Valeur nutritionnelle: " + okayStrawberry.getNutritionalValue());
        System.out.println("État avant:");
        System.out.println("  Faim: " + hero.getHunger());

        okayStrawberry.consume(hero);

        System.out.println("Après consommation:");
        System.out.println("  Faim: " + hero.getHunger() + " (devrait être 50 + 5 = 55)");

        // Test 4: Test de dégradation
        System.out.println("\n=== Test 4: Dégradation de la nourriture ===");
        Food mead = new Food(Foods.MEAD);
        System.out.println("Hydromel créé: " + mead);

        mead.degrade();
        System.out.println("Après 1 dégradation: " + mead);

        mead.degrade();
        System.out.println("Après 2 dégradations: " + mead);

        mead.degrade();
        System.out.println("Après 3 dégradations: " + mead + " (reste pourri)");
    }
}
