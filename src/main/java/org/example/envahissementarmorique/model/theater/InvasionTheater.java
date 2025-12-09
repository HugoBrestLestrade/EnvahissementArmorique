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
 * Represents an invasion theater that manages places, characters, and clan leaders.
 * The theater simulates the temporal aspect of the invasion with regular intervals.
 * <p>
 * At each interval, the theater:
 * <ul>
 *   <li>Makes belligerent characters fight, especially on battlefields</li>
 *   <li>Randomly modifies character states (hunger, potion level, etc.)</li>
 *   <li>Makes food appear in non-battlefield places</li>
 *   <li>Changes fresh food to not fresh</li>
 *   <li>Gives control to clan leaders for place management</li>
 * </ul>
 * </p>
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class InvasionTheater {

    /**
     * The name of the theater.
     */
    private String name;

    /**
     * The maximum number of places allowed in the theater.
     */
    private int maxPlaces;

    /**
     * The list of existing places in the theater.
     */
    private List<Place> places;

    /**
     * The list of clan leaders in the theater.
     */
    private List<ClanLeader> clanLeaders;

    /**
     * Random number generator for simulation events.
     */
    private Random random;

    /**
     * Creates a new InvasionTheater.
     *
     * @param name the name of the theater
     * @param maxPlaces the maximum number of places allowed
     */
    public InvasionTheater(String name, int maxPlaces) {
        this.name = name;
        this.maxPlaces = maxPlaces;
        this.places = new ArrayList<>();
        this.clanLeaders = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Gets the name of the theater.
     *
     * @return the theater name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the theater.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the maximum number of places allowed.
     *
     * @return the maximum number of places
     */
    public int getMaxPlaces() {
        return maxPlaces;
    }

    /**
     * Sets the maximum number of places allowed.
     *
     * @param maxPlaces the new maximum
     */
    public void setMaxPlaces(int maxPlaces) {
        this.maxPlaces = maxPlaces;
    }

    /**
     * Gets the list of places in the theater.
     *
     * @return the list of places
     */
    public List<Place> getPlaces() {
        return new ArrayList<>(places);
    }

    /**
     * Gets the list of clan leaders in the theater.
     *
     * @return the list of clan leaders
     */
    public List<ClanLeader> getClanLeaders() {
        return new ArrayList<>(clanLeaders);
    }

    /**
     * Adds a place to the theater.
     *
     * @param place the place to add
     * @return true if the place was added successfully, false if theater is full
     */
    public boolean addPlace(Place place) {
        if (place == null) {
            System.out.println("Cannot add null place");
            return false;
        }

        if (places.size() >= maxPlaces) {
            System.out.println("Theater is full! Cannot add more places.");
            return false;
        }

        places.add(place);
        System.out.println("Place '" + place.getName() + "' added to theater.");
        return true;
    }

    /**
     * Removes a place from the theater.
     *
     * @param place the place to remove
     * @return true if the place was removed successfully
     */
    public boolean removePlace(Place place) {
        boolean removed = places.remove(place);
        if (removed) {
            System.out.println("Place '" + place.getName() + "' removed from theater.");
        }
        return removed;
    }

    /**
     * Adds a clan leader to the theater.
     *
     * @param leader the clan leader to add
     */
    public void addClanLeader(ClanLeader leader) {
        if (leader != null && !clanLeaders.contains(leader)) {
            clanLeaders.add(leader);
            System.out.println("Clan leader '" + leader.getName() + "' added to theater.");
        }
    }

    /**
     * Removes a clan leader from the theater.
     *
     * @param leader the clan leader to remove
     * @return true if the leader was removed successfully
     */
    public boolean removeClanLeader(ClanLeader leader) {
        boolean removed = clanLeaders.remove(leader);
        if (removed) {
            System.out.println("Clan leader '" + leader.getName() + "' removed from theater.");
        }
        return removed;
    }

    /**
     * Displays all places in the theater.
     */
    public void displayPlaces() {
        System.out.println("\n========================================");
        System.out.println("THEATER: " + name);
        System.out.println("========================================");
        System.out.println("Number of places: " + places.size() + "/" + maxPlaces);

        if (places.isEmpty()) {
            System.out.println("No places in this theater.");
        } else {
            for (int i = 0; i < places.size(); i++) {
                Place place = places.get(i);
                System.out.println((i + 1) + ". " + place.getName() +
                                 " (" + place.getClass().getSimpleName() + ") - " +
                                 place.getNumberOfCharacters() + " characters");
            }
        }
        System.out.println("========================================\n");
    }

    /**
     * Gets the total number of characters in the theater.
     *
     * @return the total number of characters across all places
     */
    public int getTotalCharacters() {
        int total = 0;
        for (Place place : places) {
            total += place.getNumberOfCharacters();
        }
        return total;
    }

    /**
     * Displays all characters in all places of the theater.
     */
    public void displayAllCharacters() {
        System.out.println("\n========================================");
        System.out.println("ALL CHARACTERS IN THEATER: " + name);
        System.out.println("Total: " + getTotalCharacters() + " characters");
        System.out.println("========================================");

        for (Place place : places) {
            System.out.println("\n[" + place.getName() + "]");
            if (place.getCharacters().isEmpty()) {
                System.out.println("  (No characters)");
            } else {
                for (GameCharacter character : place.getCharacters()) {
                    String status = character.isDead() ? " [DEAD]" : " [HP: " + character.getHealth() + "]";
                    System.out.println("  - " + character.getName() +
                                     " (" + character.getFaction() + ")" + status);
                }
            }
        }
        System.out.println("========================================\n");
    }

    /**
     * Makes belligerent characters fight on battlefields.
     * Characters return to their origin place if they survive.
     */
    public void conductBattles() {
        System.out.println("\n=== CONDUCTING BATTLES ===");

        for (Place place : places) {
            if (place instanceof Battlefield) {
                Battlefield battlefield = (Battlefield) place;
                List<GameCharacter> fighters = new ArrayList<>(battlefield.getCharacters());

                System.out.println("\nBattle at " + battlefield.getName() + ":");

                for (int i = 0; i < fighters.size() - 1; i++) {
                    GameCharacter fighter1 = fighters.get(i);
                    if (fighter1.isDead()) continue;

                    for (int j = i + 1; j < fighters.size(); j++) {
                        GameCharacter fighter2 = fighters.get(j);
                        if (fighter2.isDead()) continue;

                        if (!fighter1.getFaction().equals(fighter2.getFaction()) &&
                            fighter1.isBelligerent() && fighter2.isBelligerent()) {

                            System.out.println("  " + fighter1.getName() + " fights " + fighter2.getName());
                            fighter1.fight(fighter2);

                            if (fighter2.isDead()) {
                                System.out.println("    " + fighter2.getName() + " has fallen!");
                            }
                            if (fighter1.isDead()) {
                                System.out.println("    " + fighter1.getName() + " has fallen!");
                                break;
                            }
                        }
                    }
                }

                battlefield.removeDeadCharacters();
            }
        }
    }

    /**
     * Randomly modifies character states (hunger, potion level, etc.).
     */
    public void randomlyModifyCharacters() {
        System.out.println("\n=== RANDOM CHARACTER MODIFICATIONS ===");

        for (Place place : places) {
            for (GameCharacter character : place.getCharacters()) {
                if (character.isDead()) continue;

                if (random.nextDouble() < 0.3) {
                    int hungerDecrease = random.nextInt(20) + 10;
                    character.setHunger(Math.max(0, character.getHunger() - hungerDecrease));
                    System.out.println(character.getName() + " is getting hungry (-" + hungerDecrease + " hunger)");
                }

                if (random.nextDouble() < 0.2 && character.getMagicpotion() > 0) {
                    int potionDecrease = random.nextInt(3) + 1;
                    character.setMagicpotion(Math.max(0, character.getMagicpotion() - potionDecrease));
                    System.out.println(character.getName() + "'s potion effect is wearing off (-" + potionDecrease + ")");
                }

                if (random.nextDouble() < 0.1) {
                    int healthLoss = random.nextInt(10) + 5;
                    character.setHealth(Math.max(0, character.getHealth() - healthLoss));
                    System.out.println(character.getName() + " suffered minor injury (-" + healthLoss + " HP)");
                }
            }
        }
    }

    /**
     * Makes food appear randomly in non-battlefield places.
     */
    public void spawnFood() {
        System.out.println("\n=== FOOD SPAWNING ===");

        for (Place place : places) {
            if (!(place instanceof Battlefield) && random.nextDouble() < 0.4) {
                Foods[] foodTypes = Foods.values();
                Foods randomFoodType = foodTypes[random.nextInt(foodTypes.length)];
                Food newFood = new Food(randomFoodType, Freshness.FRESH);

                place.addFood(newFood);
                System.out.println("Fresh " + randomFoodType.getLabel() + " appeared at " + place.getName());
            }
        }
    }

    /**
     * Degrades fresh food to not fresh in all places.
     */
    public void degradeFood() {
        System.out.println("\n=== FOOD DEGRADATION ===");

        for (Place place : places) {
            for (Food food : place.getFoods()) {
                Freshness oldFreshness = food.getFreshness();
                food.degrade();
                Freshness newFreshness = food.getFreshness();

                if (oldFreshness != newFreshness) {
                    System.out.println("Food at " + place.getName() + " degraded: " +
                                     oldFreshness.getLabel() + " -> " + newFreshness.getLabel());
                }
            }
        }
    }

    /**
     * Gives control to a clan leader for place management.
     * The leader can perform a limited number of actions.
     *
     * @param leader the clan leader who gets control
     * @param scanner the scanner for user input
     * @param maxActions the maximum number of actions allowed
     */
    public void giveClanLeaderControl(ClanLeader leader, Scanner scanner, int maxActions) {
        System.out.println("\n========================================");
        System.out.println("CLAN LEADER TURN: " + leader.getName());
        System.out.println("========================================");

        if (leader.getPlace() == null) {
            System.out.println(leader.getName() + " has no place to manage.");
            return;
        }

        Place place = leader.getPlace();
        place.display();

        int actionsPerformed = 0;

        while (actionsPerformed < maxActions) {
            System.out.println("\n--- Action " + (actionsPerformed + 1) + "/" + maxActions + " ---");
            System.out.println("1. Heal team");
            System.out.println("2. Feed team");
            System.out.println("3. Examine place");
            System.out.println("4. Skip turn");
            System.out.print("Choose action: ");

            if (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (!place.getCharacters().isEmpty()) {
                        place.healAll(20);
                        System.out.println("Team healed!");
                    } else {
                        System.out.println("No characters to heal.");
                    }
                    actionsPerformed++;
                    break;

                case 2:
                    if (!place.getCharacters().isEmpty() && !place.getFoods().isEmpty()) {
                        place.feedAll();
                        System.out.println("Team fed!");
                    } else {
                        System.out.println("No characters or no food available.");
                    }
                    actionsPerformed++;
                    break;

                case 3:
                    leader.examinePlace();
                    actionsPerformed++;
                    break;

                case 4:
                    System.out.println(leader.getName() + " skips remaining actions.");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        System.out.println("\n" + leader.getName() + " has used all actions.");
    }

    /**
     * Runs the main simulation loop.
     * This is the main entry point for the temporal aspect of the theater.
     *
     * @param intervals the number of time intervals to simulate
     * @param scanner the scanner for user input
     */
    public void runSimulation(int intervals, Scanner scanner) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  INVASION THEATER SIMULATION START     ║");
        System.out.println("║  Theater: " + name + "                 ");
        System.out.println("║  Intervals: " + intervals + "                         ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        for (int interval = 1; interval <= intervals; interval++) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║        INTERVAL " + interval + " / " + intervals + "                 ║");
            System.out.println("╚════════════════════════════════════════╝");

            conductBattles();

            randomlyModifyCharacters();

            spawnFood();

            degradeFood();

            for (ClanLeader leader : clanLeaders) {
                giveClanLeaderControl(leader, scanner, 2);
            }

            System.out.println("\n--- End of Interval " + interval + " ---");
            System.out.println("Total characters: " + getTotalCharacters());

            if (interval < intervals) {
                System.out.print("\nPress Enter to continue to next interval...");
                scanner.nextLine();
            }
        }

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    SIMULATION COMPLETE                 ║");
        System.out.println("╚════════════════════════════════════════╝");

        displayAllCharacters();
    }
}
