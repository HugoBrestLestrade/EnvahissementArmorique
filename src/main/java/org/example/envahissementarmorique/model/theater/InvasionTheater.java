package org.example.envahissementarmorique.model.theater;

import org.example.envahissementarmorique.model.character.base.ClanLeader;
import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.item.Food;
import org.example.envahissementarmorique.model.item.Foods;
import org.example.envahissementarmorique.model.item.Freshness;
import org.example.envahissementarmorique.model.place.Battlefield;
import org.example.envahissementarmorique.model.place.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Représente un théâtre d'invasion qui gère des lieux, des personnages et des chefs de clan.
 * <p>
 * Le théâtre simule l'aspect temporel de l'invasion avec des intervalles réguliers.
 * À chaque intervalle :
 * <ul>
 *   <li>Les personnages belliqueux se battent, surtout sur les champs de bataille</li>
 *   <li>Les états des personnages (faim, potion, santé, etc.) sont modifiés aléatoirement</li>
 *   <li>De la nourriture apparaît aléatoirement dans les lieux non-bataille</li>
 *   <li>La nourriture fraîche se dégrade progressivement</li>
 *   <li>Les chefs de clan peuvent gérer les lieux qui leur sont assignés</li>
 * </ul>
 * </p>
 * <p>
 * Cette classe fournit des méthodes pour exécuter la simulation complète,
 * ajouter ou retirer des lieux et des chefs de clan, afficher l'état actuel du théâtre,
 * et simuler des combats et événements aléatoires.
 * </p>
 *
 * @author Envahissement Armorique
 * @version 1.0
 */
public class InvasionTheater {

    /** Le nom du théâtre. */
    private String name;

    /** Le nombre maximum de lieux autorisés dans le théâtre. */
    private int maxPlaces;

    /** La liste des lieux présents dans le théâtre. */
    private List<Place> places;

    /** La liste des chefs de clan présents dans le théâtre. */
    private List<ClanLeader> clanLeaders;

    /** Générateur aléatoire pour les événements de la simulation. */
    private Random random;

    /**
     * Crée un nouveau théâtre d'invasion.
     *
     * @param name le nom du théâtre
     * @param maxPlaces le nombre maximum de lieux autorisés
     */
    public InvasionTheater(String name, int maxPlaces) {
        this.name = name;
        this.maxPlaces = maxPlaces;
        this.places = new ArrayList<>();
        this.clanLeaders = new ArrayList<>();
        this.random = new Random();
    }

    /** @return le nom du théâtre */
    public String getName() {
        return name;
    }

    /** @param name le nouveau nom du théâtre */
    public void setName(String name) {
        this.name = name;
    }

    /** @return le nombre maximum de lieux autorisés */
    public int getMaxPlaces() {
        return maxPlaces;
    }

    /** @param maxPlaces le nouveau nombre maximum de lieux */
    public void setMaxPlaces(int maxPlaces) {
        this.maxPlaces = maxPlaces;
    }

    /** @return une copie de la liste des lieux */
    public List<Place> getPlaces() {
        return new ArrayList<>(places);
    }

    /** @return une copie de la liste des chefs de clan */
    public List<ClanLeader> getClanLeaders() {
        return new ArrayList<>(clanLeaders);
    }

    /**
     * Ajoute un lieu au théâtre.
     *
     * @param place le lieu à ajouter
     * @return true si le lieu a été ajouté avec succès, false si le théâtre est plein ou lieu null
     */
    public boolean addPlace(Place place) {
        if (place == null) {
            System.out.println("Impossible d'ajouter un lieu null");
            return false;
        }

        if (places.size() >= maxPlaces) {
            System.out.println("Le théâtre est plein ! Impossible d'ajouter un lieu.");
            return false;
        }

        places.add(place);
        System.out.println("Lieu '" + place.getName() + "' ajouté au théâtre.");
        return true;
    }

    /**
     * Supprime un lieu du théâtre.
     *
     * @param place le lieu à supprimer
     * @return true si le lieu a été supprimé avec succès
     */
    public boolean removePlace(Place place) {
        boolean removed = places.remove(place);
        if (removed) {
            System.out.println("Lieu '" + place.getName() + "' supprimé du théâtre.");
        }
        return removed;
    }

    /**
     * Ajoute un chef de clan au théâtre.
     *
     * @param leader le chef de clan à ajouter
     */
    public void addClanLeader(ClanLeader leader) {
        if (leader != null && !clanLeaders.contains(leader)) {
            clanLeaders.add(leader);
            System.out.println("Chef de clan '" + leader.getName() + "' ajouté au théâtre.");
        }
    }

    /**
     * Supprime un chef de clan du théâtre.
     *
     * @param leader le chef de clan à supprimer
     * @return true si le chef a été supprimé avec succès
     */
    public boolean removeClanLeader(ClanLeader leader) {
        boolean removed = clanLeaders.remove(leader);
        if (removed) {
            System.out.println("Chef de clan '" + leader.getName() + "' supprimé du théâtre.");
        }
        return removed;
    }

    /**
     * Affiche tous les lieux présents dans le théâtre.
     */
    public void displayPlaces() {
        System.out.println("\n========================================");
        System.out.println("THÉÂTRE : " + name);
        System.out.println("========================================");
        System.out.println("Nombre de lieux : " + places.size() + "/" + maxPlaces);

        if (places.isEmpty()) {
            System.out.println("Aucun lieu dans ce théâtre.");
        } else {
            for (int i = 0; i < places.size(); i++) {
                Place place = places.get(i);
                System.out.println((i + 1) + ". " + place.getName() +
                        " (" + place.getClass().getSimpleName() + ") - " +
                        place.getNumberOfCharacters() + " personnages");
            }
        }
        System.out.println("========================================\n");
    }

    /**
     * @return le nombre total de personnages présents dans tous les lieux du théâtre
     */
    public int getTotalCharacters() {
        int total = 0;
        for (Place place : places) {
            total += place.getNumberOfCharacters();
        }
        return total;
    }

    /**
     * Affiche tous les personnages présents dans tous les lieux du théâtre.
     */
    public void displayAllCharacters() {
        System.out.println("\n========================================");
        System.out.println("TOUS LES PERSONNAGES DU THÉÂTRE : " + name);
        System.out.println("Total : " + getTotalCharacters() + " personnages");
        System.out.println("========================================");

        for (Place place : places) {
            System.out.println("\n[" + place.getName() + "]");
            if (place.getCharacters().isEmpty()) {
                System.out.println("  (Aucun personnage)");
            } else {
                for (GameCharacter character : place.getCharacters()) {
                    String status = character.isDead() ? " [MORT]" : " [PV: " + character.getHealth() + "]";
                    System.out.println("  - " + character.getName() +
                            " (" + character.getFaction() + ")" + status);
                }
            }
        }
        System.out.println("========================================\n");
    }

    /**
     * Fait combattre les personnages belliqueux sur les champs de bataille.
     * Les personnages survivants retournent à leur lieu d'origine.
     *
     * @return liste des résultats des combats
     */
    public List<CombatResult> conductBattles() {
        List<CombatResult> results = new ArrayList<>();
        System.out.println("\n=== COMBATS ===");

        for (Place place : places) {
            if (place instanceof Battlefield) {
                Battlefield battlefield = (Battlefield) place;
                List<GameCharacter> fighters = new ArrayList<>(battlefield.getCharacters());

                System.out.println("\nBataille à " + battlefield.getName() + " :");

                for (int i = 0; i < fighters.size() - 1; i++) {
                    GameCharacter fighter1 = fighters.get(i);
                    if (fighter1.isDead()) continue;

                    for (int j = i + 1; j < fighters.size(); j++) {
                        GameCharacter fighter2 = fighters.get(j);
                        if (fighter2.isDead()) continue;

                        if (!fighter1.getFaction().equals(fighter2.getFaction()) &&
                                fighter1.isBelligerent() && fighter2.isBelligerent()) {

                            int fighter1HealthBefore = fighter1.getHealth();
                            int fighter2HealthBefore = fighter2.getHealth();

                            System.out.println("\n  ⚔️ COMBAT : " + fighter1.getName() + " (" + fighter1.getFaction() + ") vs " +
                                    fighter2.getName() + " (" + fighter2.getFaction() + ")");
                            System.out.println("     Avant : " + fighter1.getName() + " [PV: " + fighter1HealthBefore + "] | " +
                                    fighter2.getName() + " [PV: " + fighter2HealthBefore + "]");

                            fighter1.fight(fighter2);

                            int damageToFighter2 = fighter2HealthBefore - fighter2.getHealth();
                            int damageToFighter1 = fighter1HealthBefore - fighter1.getHealth();

                            System.out.println("     Dégâts : " + fighter1.getName() + " inflige " + damageToFighter2 + " | " +
                                    fighter2.getName() + " inflige " + damageToFighter1);
                            System.out.println("     Après : " + fighter1.getName() + " [PV: " + fighter1.getHealth() + "] | " +
                                    fighter2.getName() + " [PV: " + fighter2.getHealth() + "]");

                            CombatResult result = new CombatResult(
                                    fighter1.getName(), fighter2.getName(),
                                    fighter1.getFaction(), fighter2.getFaction(),
                                    damageToFighter2, damageToFighter1,
                                    fighter1.getHealth(), fighter2.getHealth(),
                                    fighter1.isDead(), fighter2.isDead(),
                                    battlefield.getName()
                            );
                            results.add(result);

                            if (fighter2.isDead()) System.out.println("     💀 " + fighter2.getName() + " est tombé !");
                            if (fighter1.isDead()) {
                                System.out.println("     💀 " + fighter1.getName() + " est tombé !");
                                break;
                            }
                        }
                    }
                }

                battlefield.removeDeadCharacters();
            }
        }

        return results;
    }

    /**
     * Modifie aléatoirement les états des personnages (faim, potions, santé, etc.).
     *
     * @return liste des messages indiquant les changements d'état
     */
    public List<String> randomlyModifyCharacters() {
        List<String> messages = new ArrayList<>();
        System.out.println("\n=== MODIFICATIONS ALÉATOIRES DES PERSONNAGES ===");

        for (Place place : places) {
            for (GameCharacter character : place.getCharacters()) {
                if (character.isDead()) continue;

                if (random.nextDouble() < 0.3) {
                    int hungerDecrease = random.nextInt(20) + 10;
                    character.setHunger(Math.max(0, character.getHunger() - hungerDecrease));
                    String message = character.getName() + " a faim (-" + hungerDecrease + ")";
                    System.out.println(message);
                    messages.add(message);
                }

                if (random.nextDouble() < 0.2 && character.getMagicpotion() > 0) {
                    int potionDecrease = random.nextInt(3) + 1;
                    character.setMagicpotion(Math.max(0, character.getMagicpotion() - potionDecrease));
                    String message = character.getName() + " perd de l'effet de sa potion (-" + potionDecrease + ")";
                    System.out.println(message);
                    messages.add(message);
                }

                if (random.nextDouble() < 0.1) {
                    int healthLoss = random.nextInt(10) + 5;
                    character.setHealth(Math.max(0, character.getHealth() - healthLoss));
                    String message = character.getName() + " subit une blessure mineure (-" + healthLoss + " PV)";
                    System.out.println(message);
                    messages.add(message);
                }
            }
        }

        return messages;
    }

    /**
     * Fait apparaître de la nourriture aléatoirement dans les lieux non-bataille.
     *
     * @return liste des messages indiquant la nourriture apparue
     */
    public List<String> spawnFood() {
        List<String> messages = new ArrayList<>();
        System.out.println("\n=== APPARITION DE NOURRITURE ===");

        for (Place place : places) {
            if (!(place instanceof Battlefield) && random.nextDouble() < 0.4) {
                Foods[] foodTypes = Foods.values();
                Foods randomFoodType = foodTypes[random.nextInt(foodTypes.length)];
                Food newFood = new Food(randomFoodType, Freshness.FRESH);

                place.addFood(newFood);
                String message = "Nourriture fraîche (" + randomFoodType.getLabel() + ") apparue à " + place.getName();
                System.out.println(message);
                messages.add(message);
            }
        }

        return messages;
    }

    /**
     * Dégrade les aliments frais en tous les lieux.
     *
     * @return liste des messages de dégradation de la nourriture
     */
    public List<String> degradeFood() {
        List<String> messages = new ArrayList<>();
        System.out.println("\n=== DÉGRADATION DE LA NOURRITURE ===");

        for (Place place : places) {
            for (Food food : place.getFoods()) {
                Freshness oldFreshness = food.getFreshness();
                food.degrade();
                Freshness newFreshness = food.getFreshness();

                if (oldFreshness != newFreshness) {
                    String message = "Nourriture à " + place.getName() + " dégradée : " +
                            oldFreshness.getLabel() + " -> " + newFreshness.getLabel();
                    System.out.println(message);
                    messages.add(message);
                }
            }
        }

        return messages;
    }

    /**
     * Donne le contrôle d'un lieu à un chef de clan pour effectuer des actions limitées.
     *
     * @param leader le chef de clan
     * @param scanner scanner pour la saisie utilisateur
     * @param maxActions nombre maximum d'actions autorisées
     */
    public void giveClanLeaderControl(ClanLeader leader, Scanner scanner, int maxActions) {
        System.out.println("\n========================================");
        System.out.println("TOUR DU CHEF DE CLAN : " + leader.getName());
        System.out.println("========================================");

        if (leader.getPlace() == null) {
            System.out.println(leader.getName() + " n'a aucun lieu à gérer.");
            return;
        }

        Place place = leader.getPlace();
        place.display();

        int actionsPerformed = 0;

        while (actionsPerformed < maxActions) {
            System.out.println("\n--- Action " + (actionsPerformed + 1) + "/" + maxActions + " ---");
            System.out.println("1. Soigner l'équipe");
            System.out.println("2. Nourrir l'équipe");
            System.out.println("3. Examiner le lieu");
            System.out.println("4. Passer le tour");
            System.out.print("Choisir une action : ");

            if (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (!place.getCharacters().isEmpty()) {
                        place.healAll(20);
                        System.out.println("Équipe soignée !");
                    } else {
                        System.out.println("Aucun personnage à soigner.");
                    }
                    actionsPerformed++;
                    break;

                case 2:
                    if (!place.getCharacters().isEmpty() && !place.getFoods().isEmpty()) {
                        place.feedAll();
                        System.out.println("Équipe nourrie !");
                    } else {
                        System.out.println("Pas de personnages ou pas de nourriture disponible.");
                    }
                    actionsPerformed++;
                    break;

                case 3:
                    leader.examinePlace();
                    actionsPerformed++;
                    break;

                case 4:
                    System.out.println(leader.getName() + " passe le reste des actions.");
                    return;

                default:
                    System.out.println("Choix invalide. Réessayez.");
            }
        }

        System.out.println("\n" + leader.getName() + " a utilisé toutes ses actions.");
    }

    /**
     * Lance la boucle principale de simulation.
     *
     * @param intervals le nombre d'intervalles de temps à simuler
     * @param scanner scanner pour la saisie utilisateur
     */
    public void runSimulation(int intervals, Scanner scanner) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  SIMULATION DU THÉÂTRE D'INVASION     ║");
        System.out.println("║  Théâtre : " + name + "                 ");
        System.out.println("║  Intervalles : " + intervals + "                         ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        for (int interval = 1; interval <= intervals; interval++) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║        INTERVALLE " + interval + " / " + intervals + "                 ║");
            System.out.println("╚════════════════════════════════════════╝");

            conductBattles();
            randomlyModifyCharacters();
            spawnFood();
            degradeFood();

            for (ClanLeader leader : clanLeaders) {
                giveClanLeaderControl(leader, scanner, 2);
            }

            System.out.println("\n--- Fin de l'intervalle " + interval + " ---");
            System.out.println("Nombre total de personnages : " + getTotalCharacters());

            if (interval < intervals) {
                System.out.print("\nAppuyez sur Entrée pour passer au prochain intervalle...");
                scanner.nextLine();
            }
        }

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    SIMULATION TERMINÉE                 ║");
        System.out.println("╚════════════════════════════════════════");
    }
}
