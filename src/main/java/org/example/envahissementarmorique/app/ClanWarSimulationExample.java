package org.example.envahissementarmorique.app;

import org.example.envahissementarmorique.model.character.base.ClanLeader;
import org.example.envahissementarmorique.model.character.base.Gaulish.Gaulois;
import org.example.envahissementarmorique.model.character.base.Roman.Roman;
import org.example.envahissementarmorique.model.place.Battlefield;
import org.example.envahissementarmorique.model.place.GaulishVillage;
import org.example.envahissementarmorique.model.place.RomanCamp;
import org.example.envahissementarmorique.model.theater.ClanWarSimulation;

import java.util.Scanner;

/**
 * Application de démonstration pour la simulation de guerre entre clans.
 * Cette classe configure et lance une simulation exemple avec deux clans qui s'affrontent.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class ClanWarSimulationExample {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║  SIMULATION DE GUERRE ENTRE CLANS                     ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        // Créer la simulation
        ClanWarSimulation simulation = new ClanWarSimulation("Guerre d'Armorique", 10);

        // Configuration du Clan 1 : Les Gaulois
        System.out.println("=== Configuration du Clan Gaulois ===");
        GaulishVillage villageGaulois = new GaulishVillage("Village d'Astérix", 5000, null);
        ClanLeader chefGaulois = new ClanLeader("Abraracourcix", "M", 50, villageGaulois);
        villageGaulois.setChief(chefGaulois);
        simulation.setClan1(chefGaulois, villageGaulois);

        // Ajouter des guerriers gaulois
        System.out.println("\n--- Création des guerriers gaulois ---");
        Gaulois asterix = new Gaulois("Astérix", "M", 1.60, 35, 85, 90, 200, 100, 80, 0);
        Gaulois obelix = new Gaulois("Obélix", "M", 1.85, 35, 95, 100, 240, 100, 75, 0);
        Gaulois abraracourcix = new Gaulois("Abraracourcix", "M", 1.75, 50, 80, 85, 200, 100, 70, 0);
        Gaulois assurancetourix = new Gaulois("Assurancetourix", "M", 1.70, 30, 60, 70, 180, 100, 50, 0);

        simulation.addCharacterToClan1(asterix);
        simulation.addCharacterToClan1(obelix);
        simulation.addCharacterToClan1(abraracourcix);
        simulation.addCharacterToClan1(assurancetourix);

        System.out.println("✓ 4 guerriers gaulois créés");

        // Configuration du Clan 2 : Les Romains
        System.out.println("\n=== Configuration du Clan Romain ===");
        RomanCamp campRomain = new RomanCamp("Camp Babaorum", 8000, null);
        ClanLeader chefRomain = new ClanLeader("Caius Bonus", "M", 45, campRomain);
        campRomain.setChief(chefRomain);
        simulation.setClan2(chefRomain, campRomain);

        // Ajouter des guerriers romains
        System.out.println("\n--- Création des guerriers romains ---");
        Roman centurion = new Roman("Centurion Marcus", "M", 1.78, 40, 75, 80, 190, 100, 85, 0);
        Roman legionnaire1 = new Roman("Légionnaire Lucius", "M", 1.75, 28, 70, 75, 180, 100, 80, 0);
        Roman legionnaire2 = new Roman("Légionnaire Brutus", "M", 1.76, 30, 72, 78, 184, 100, 82, 0);
        Roman legionnaire3 = new Roman("Légionnaire Claudius", "M", 1.74, 26, 68, 74, 176, 100, 78, 0);
        Roman legionnaire4 = new Roman("Légionnaire Quintus", "M", 1.77, 29, 71, 76, 182, 100, 81, 0);

        simulation.addCharacterToClan2(centurion);
        simulation.addCharacterToClan2(legionnaire1);
        simulation.addCharacterToClan2(legionnaire2);
        simulation.addCharacterToClan2(legionnaire3);
        simulation.addCharacterToClan2(legionnaire4);

        System.out.println("✓ 5 guerriers romains créés");

        // Configuration du champ de bataille
        System.out.println("\n=== Configuration du champ de bataille ===");
        Battlefield champDeBataille = new Battlefield("Plaine d'Armorique", 10000);
        simulation.setBattlefield(champDeBataille);
        System.out.println("✓ Champ de bataille créé");

        // Demander le nombre de tours
        System.out.print("\nCombien de tours de combat voulez-vous simuler ? (1-20) : ");
        int rounds = 5; // Valeur par défaut

        if (scanner.hasNextInt()) {
            rounds = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne

            if (rounds < 1) rounds = 1;
            if (rounds > 20) rounds = 20;
        } else {
            scanner.nextLine(); // Consommer l'entrée invalide
            System.out.println("Entrée invalide. Utilisation de la valeur par défaut : 5 tours");
        }

        System.out.println("\n✓ Configuration terminée ! Lancement de la simulation...");
        System.out.print("\nAppuyez sur Entrée pour commencer...");
        scanner.nextLine();

        // Lancer la simulation
        simulation.runSimulation(rounds, scanner);

        scanner.close();
        System.out.println("\nMerci d'avoir utilisé la simulation de guerre entre clans !");
    }
}

