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
 * Représente une simulation de guerre entre deux clans.
 * Cette simulation met en scène deux clans qui s'affrontent sur un champ de bataille.
 * <p>
 * Fonctionnalités :
 * <ul>
 *   <li>Configuration de deux clans avec leurs chefs et personnages</li>
 *   <li>Organisation de combats entre les clans</li>
 *   <li>Gestion de la faim et des potions magiques</li>
 *   <li>Affichage des résultats de la guerre</li>
 *   <li>Contrôle par les chefs de clan entre les tours</li>
 * </ul>
 * </p>
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class ClanWarSimulation {

    /**
     * Le nom de la simulation.
     */
    private String name;

    /**
     * Le nombre maximal de lieux dans cette simulation.
     */
    private int maxPlaces;

    /**
     * La liste des lieux dans la simulation.
     */
    private List<Place> places;

    /**
     * Le premier chef de clan.
     */
    private ClanLeader clanLeader1;

    /**
     * Le deuxième chef de clan.
     */
    private ClanLeader clanLeader2;

    /**
     * Le lieu du premier clan.
     */
    private Place clan1Place;

    /**
     * Le lieu du deuxième clan.
     */
    private Place clan2Place;

    /**
     * Le champ de bataille principal.
     */
    private Battlefield mainBattlefield;

    /**
     * Générateur aléatoire pour les événements.
     */
    private Random random;

    /**
     * Crée une nouvelle simulation de guerre entre clans.
     *
     * @param name le nom de la simulation
     * @param maxPlaces le nombre maximal de lieux
     */
    public ClanWarSimulation(String name, int maxPlaces) {
        this.name = name;
        this.maxPlaces = maxPlaces;
        this.places = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Obtient le nom de la simulation.
     *
     * @return le nom de la simulation
     */
    public String getName() {
        return name;
    }

    /**
     * Configure le premier clan.
     *
     * @param leader le chef du clan
     * @param clanPlace le lieu du clan
     */
    public void setClan1(ClanLeader leader, Place clanPlace) {
        this.clanLeader1 = leader;
        this.clan1Place = clanPlace;
        leader.setPlace(clanPlace);
        addPlace(clanPlace);
    }

    /**
     * Configure le deuxième clan.
     *
     * @param leader le chef du clan
     * @param clanPlace le lieu du clan
     */
    public void setClan2(ClanLeader leader, Place clanPlace) {
        this.clanLeader2 = leader;
        this.clan2Place = clanPlace;
        leader.setPlace(clanPlace);
        addPlace(clanPlace);
    }

    /**
     * Configure le champ de bataille principal.
     *
     * @param battlefield le champ de bataille
     */
    public void setBattlefield(Battlefield battlefield) {
        this.mainBattlefield = battlefield;
        addPlace(battlefield);
    }

    /**
     * Ajoute un lieu à la simulation.
     *
     * @param place le lieu à ajouter
     * @return true si le lieu a été ajouté avec succès
     */
    public boolean addPlace(Place place) {
        if (place == null) {
            System.out.println("Impossible d'ajouter un lieu null");
            return false;
        }

        if (places.size() >= maxPlaces) {
            System.out.println("La simulation est pleine ! Impossible d'ajouter plus de lieux.");
            return false;
        }

        if (!places.contains(place)) {
            places.add(place);
            System.out.println("Lieu '" + place.getName() + "' ajouté à la simulation.");
            return true;
        }
        return false;
    }

    /**
     * Ajoute un personnage au clan 1.
     *
     * @param character le personnage à ajouter
     */
    public void addCharacterToClan1(GameCharacter character) {
        if (clan1Place != null && character != null) {
            clan1Place.addCharacter(character);
        }
    }

    /**
     * Ajoute un personnage au clan 2.
     *
     * @param character le personnage à ajouter
     */
    public void addCharacterToClan2(GameCharacter character) {
        if (clan2Place != null && character != null) {
            clan2Place.addCharacter(character);
        }
    }

    /**
     * Affiche l'état actuel de la simulation.
     */
    public void displayStatus() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║  ÉTAT DE LA SIMULATION DE GUERRE ENTRE CLANS          ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println("Nom de la simulation : " + name);
        System.out.println("Nombre de lieux : " + places.size() + "/" + maxPlaces);

        if (clanLeader1 != null && clan1Place != null) {
            System.out.println("\n--- CLAN 1 ---");
            System.out.println("Chef : " + clanLeader1.getName());
            System.out.println("Lieu : " + clan1Place.getName());
            System.out.println("Nombre de guerriers : " + clan1Place.getNumberOfCharacters());
            displayClanWarriors(clan1Place);
        }

        if (clanLeader2 != null && clan2Place != null) {
            System.out.println("\n--- CLAN 2 ---");
            System.out.println("Chef : " + clanLeader2.getName());
            System.out.println("Lieu : " + clan2Place.getName());
            System.out.println("Nombre de guerriers : " + clan2Place.getNumberOfCharacters());
            displayClanWarriors(clan2Place);
        }

        if (mainBattlefield != null) {
            System.out.println("\n--- CHAMP DE BATAILLE ---");
            System.out.println("Nom : " + mainBattlefield.getName());
            System.out.println("Combattants présents : " + mainBattlefield.getNumberOfCharacters());
        }

        System.out.println("════════════════════════════════════════════════════════\n");
    }

    /**
     * Affiche les guerriers d'un clan.
     *
     * @param place le lieu du clan
     */
    private void displayClanWarriors(Place place) {
        List<GameCharacter> warriors = place.getCharacters();
        if (warriors.isEmpty()) {
            System.out.println("  (Aucun guerrier)");
        } else {
            for (GameCharacter warrior : warriors) {
                String status = warrior.isDead() ? " [MORT]" : " [Santé: " + warrior.getHealth() + ", Faim: " + warrior.getHunger() + "]";
                System.out.println("  • " + warrior.getName() + status);
            }
        }
    }

    /**
     * Transfert tous les guerriers vivants vers le champ de bataille.
     */
    public void deployWarriorsToBattlefield() {
        System.out.println("\n=== DÉPLOIEMENT DES GUERRIERS SUR LE CHAMP DE BATAILLE ===");

        if (mainBattlefield == null) {
            System.out.println("Erreur : Aucun champ de bataille configuré !");
            return;
        }

        // Déployer les guerriers du clan 1
        if (clan1Place != null) {
            List<GameCharacter> clan1Warriors = new ArrayList<>(clan1Place.getCharacters());
            for (GameCharacter warrior : clan1Warriors) {
                if (!warrior.isDead()) {
                    clan1Place.removeCharacter(warrior);
                    mainBattlefield.addCharacter(warrior);
                    System.out.println(warrior.getName() + " (Clan 1) déployé sur le champ de bataille");
                }
            }
        }

        // Déployer les guerriers du clan 2
        if (clan2Place != null) {
            List<GameCharacter> clan2Warriors = new ArrayList<>(clan2Place.getCharacters());
            for (GameCharacter warrior : clan2Warriors) {
                if (!warrior.isDead()) {
                    clan2Place.removeCharacter(warrior);
                    mainBattlefield.addCharacter(warrior);
                    System.out.println(warrior.getName() + " (Clan 2) déployé sur le champ de bataille");
                }
            }
        }

        System.out.println("Total de combattants sur le champ de bataille : " + mainBattlefield.getNumberOfCharacters());
    }

    /**
     * Organise un combat sur le champ de bataille.
     */
    public void conductBattle() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║              DÉBUT DU COMBAT                          ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        if (mainBattlefield == null) {
            System.out.println("Erreur : Aucun champ de bataille configuré !");
            return;
        }

        List<GameCharacter> fighters = mainBattlefield.getCharacters();

        if (fighters.size() < 2) {
            System.out.println("Pas assez de combattants pour organiser un combat.");
            return;
        }

        // Séparer les combattants par faction
        List<GameCharacter> faction1 = new ArrayList<>();
        List<GameCharacter> faction2 = new ArrayList<>();
        String firstFaction = null;

        for (GameCharacter fighter : fighters) {
            if (!fighter.isDead()) {
                if (firstFaction == null) {
                    firstFaction = fighter.getFaction();
                    faction1.add(fighter);
                } else if (fighter.getFaction().equals(firstFaction)) {
                    faction1.add(fighter);
                } else {
                    faction2.add(fighter);
                }
            }
        }

        System.out.println("\nFaction 1 (" + (firstFaction != null ? firstFaction : "Inconnu") + ") : " + faction1.size() + " combattants");
        System.out.println("Faction 2 : " + faction2.size() + " combattants");

        // Combat tour par tour
        while (!faction1.isEmpty() && !faction2.isEmpty()) {
            GameCharacter fighter1 = faction1.get(random.nextInt(faction1.size()));
            GameCharacter fighter2 = faction2.get(random.nextInt(faction2.size()));

            if (!fighter1.isDead() && !fighter2.isDead() && fighter1.isBelligerent() && fighter2.isBelligerent()) {
                // Store health before combat
                int fighter1HealthBefore = fighter1.getHealth();
                int fighter2HealthBefore = fighter2.getHealth();

                System.out.println("\n⚔ COMBAT: " + fighter1.getName() + " (" + fighter1.getFaction() + ") vs " +
                                 fighter2.getName() + " (" + fighter2.getFaction() + ")");
                System.out.println("  Before: " + fighter1.getName() + " [HP: " + fighter1HealthBefore + "] | " +
                                 fighter2.getName() + " [HP: " + fighter2HealthBefore + "]");

                fighter1.fight(fighter2);

                // Calculate and display damage
                int damageToFighter2 = fighter2HealthBefore - fighter2.getHealth();
                int damageToFighter1 = fighter1HealthBefore - fighter1.getHealth();

                System.out.println("  Damage: " + fighter1.getName() + " dealt " + damageToFighter2 + " damage | " +
                                 fighter2.getName() + " dealt " + damageToFighter1 + " damage");
                System.out.println("  After:  " + fighter1.getName() + " [HP: " + fighter1.getHealth() + "] | " +
                                 fighter2.getName() + " [HP: " + fighter2.getHealth() + "]");

                if (fighter2.isDead()) {
                    System.out.println("  💀 " + fighter2.getName() + " est tombé au combat !");
                    faction2.remove(fighter2);
                }
                if (fighter1.isDead()) {
                    System.out.println("  💀 " + fighter1.getName() + " est tombé au combat !");
                    faction1.remove(fighter1);
                }
            }

            // Retirer les morts des listes
            faction1.removeIf(GameCharacter::isDead);
            faction2.removeIf(GameCharacter::isDead);
        }

        // Nettoyer les morts du champ de bataille
        mainBattlefield.removeDeadCharacters();

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║              FIN DU COMBAT                            ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }

    /**
     * Ramène les survivants dans leur lieu d'origine.
     */
    public void returnSurvivors() {
        System.out.println("\n=== RETOUR DES SURVIVANTS ===");

        if (mainBattlefield == null) return;

        List<GameCharacter> survivors = new ArrayList<>(mainBattlefield.getCharacters());

        for (GameCharacter survivor : survivors) {
            if (!survivor.isDead()) {
                // Déterminer le clan d'origine par la faction
                Place originPlace = null;

                if (clan1Place != null && !clan1Place.getCharacters().isEmpty()) {
                    String clan1Faction = clan1Place.getCharacters().get(0).getFaction();
                    if (survivor.getFaction().equals(clan1Faction)) {
                        originPlace = clan1Place;
                    }
                }

                if (originPlace == null && clan2Place != null) {
                    originPlace = clan2Place;
                }

                if (originPlace != null) {
                    mainBattlefield.removeCharacter(survivor);
                    originPlace.addCharacter(survivor);
                    System.out.println(survivor.getName() + " retourne à " + originPlace.getName());
                }
            }
        }
    }

    /**
     * Modifie aléatoirement l'état des personnages.
     */
    public void randomlyModifyCharacters() {
        System.out.println("\n=== MODIFICATIONS ALÉATOIRES DES PERSONNAGES ===");

        for (Place place : places) {
            for (GameCharacter character : place.getCharacters()) {
                if (character.isDead()) continue;

                // Diminution de la faim
                if (random.nextDouble() < 0.4) {
                    int hungerDecrease = random.nextInt(15) + 5;
                    character.setHunger(Math.max(0, character.getHunger() - hungerDecrease));
                    System.out.println(character.getName() + " a faim (-" + hungerDecrease + " faim)");

                    // Si trop faible faim, perte de santé
                    if (character.getHunger() < 30) {
                        int healthLoss = 10;
                        character.setHealth(Math.max(0, character.getHealth() - healthLoss));
                        System.out.println("  " + character.getName() + " souffre de la faim (-" + healthLoss + " PV)");
                    }
                }

                // Diminution de la potion magique
                if (random.nextDouble() < 0.3 && character.getMagicpotion() > 0) {
                    int potionDecrease = random.nextInt(2) + 1;
                    character.setMagicpotion(Math.max(0, character.getMagicpotion() - potionDecrease));
                    System.out.println(character.getName() + " : effet de potion diminue (-" + potionDecrease + ")");
                }
            }
        }
    }

    /**
     * Fait apparaître de la nourriture dans les lieux (pas sur le champ de bataille).
     */
    public void spawnFood() {
        System.out.println("\n=== APPARITION DE NOURRITURE ===");

        for (Place place : places) {
            if (!(place instanceof Battlefield) && random.nextDouble() < 0.5) {
                Foods[] foodTypes = Foods.values();
                Foods randomFoodType = foodTypes[random.nextInt(foodTypes.length)];
                Food newFood = new Food(randomFoodType, Freshness.FRESH);

                place.addFood(newFood);
                System.out.println(randomFoodType.getLabel() + " frais apparu à " + place.getName());
            }
        }
    }

    /**
     * Dégrade la nourriture (frais → pas frais).
     */
    public void degradeFood() {
        System.out.println("\n=== DÉGRADATION DE LA NOURRITURE ===");

        for (Place place : places) {
            for (Food food : place.getFoods()) {
                Freshness oldFreshness = food.getFreshness();
                food.degrade();
                Freshness newFreshness = food.getFreshness();

                if (oldFreshness != newFreshness) {
                    System.out.println("Nourriture à " + place.getName() + " dégradée : " +
                                     oldFreshness.getLabel() + " → " + newFreshness.getLabel());
                }
            }
        }
    }

    /**
     * Donne le contrôle à un chef de clan.
     *
     * @param leader le chef de clan
     * @param scanner le scanner pour les entrées utilisateur
     * @param maxActions le nombre maximal d'actions
     */
    public void giveClanLeaderControl(ClanLeader leader, Scanner scanner, int maxActions) {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║  TOUR DU CHEF DE CLAN : " + leader.getName() + "                    ");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        if (leader.getPlace() == null) {
            System.out.println(leader.getName() + " n'a pas de lieu à gérer.");
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
            System.out.print("Choisissez une action : ");

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
                        System.out.println("✓ Équipe soignée !");
                    } else {
                        System.out.println("Aucun personnage à soigner.");
                    }
                    actionsPerformed++;
                    break;

                case 2:
                    if (!place.getCharacters().isEmpty() && !place.getFoods().isEmpty()) {
                        place.feedAll();
                        System.out.println("✓ Équipe nourrie !");
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
                    System.out.println(leader.getName() + " passe les actions restantes.");
                    return;

                default:
                    System.out.println("Choix invalide. Réessayez.");
            }
        }

        System.out.println("\n" + leader.getName() + " a utilisé toutes ses actions.");
    }

    /**
     * Affiche les résultats finaux de la guerre.
     */
    public void displayFinalResults() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║           RÉSULTATS FINAUX DE LA GUERRE               ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        int clan1Survivors = 0;
        int clan1Deaths = 0;
        int clan2Survivors = 0;
        int clan2Deaths = 0;

        // Compter les survivants et les morts du clan 1
        if (clan1Place != null) {
            for (GameCharacter character : clan1Place.getCharacters()) {
                if (character.isDead()) {
                    clan1Deaths++;
                } else {
                    clan1Survivors++;
                }
            }
        }

        // Compter les survivants et les morts du clan 2
        if (clan2Place != null) {
            for (GameCharacter character : clan2Place.getCharacters()) {
                if (character.isDead()) {
                    clan2Deaths++;
                } else {
                    clan2Survivors++;
                }
            }
        }

        System.out.println("\n--- CLAN 1 : " + (clanLeader1 != null ? clanLeader1.getName() : "Inconnu") + " ---");
        System.out.println("Survivants : " + clan1Survivors);
        System.out.println("Morts : " + clan1Deaths);
        displayClanWarriors(clan1Place);

        System.out.println("\n--- CLAN 2 : " + (clanLeader2 != null ? clanLeader2.getName() : "Inconnu") + " ---");
        System.out.println("Survivants : " + clan2Survivors);
        System.out.println("Morts : " + clan2Deaths);
        displayClanWarriors(clan2Place);

        // Déterminer le vainqueur
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        if (clan1Survivors > clan2Survivors) {
            System.out.println("║  🏆 VICTOIRE DU CLAN 1 : " + (clanLeader1 != null ? clanLeader1.getName() : "Inconnu") + " 🏆    ");
        } else if (clan2Survivors > clan1Survivors) {
            System.out.println("║  🏆 VICTOIRE DU CLAN 2 : " + (clanLeader2 != null ? clanLeader2.getName() : "Inconnu") + " 🏆    ");
        } else {
            System.out.println("║  ⚖ MATCH NUL ⚖                                       ");
        }
        System.out.println("╚════════════════════════════════════════════════════════╝\n");
    }

    /**
     * Lance la simulation de guerre.
     *
     * @param rounds le nombre de tours de combat
     * @param scanner le scanner pour les entrées utilisateur
     */
    public void runSimulation(int rounds, Scanner scanner) {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║    DÉBUT DE LA SIMULATION DE GUERRE ENTRE CLANS       ║");
        System.out.println("║    Simulation : " + name + "                           ");
        System.out.println("║    Nombre de tours : " + rounds + "                            ");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        // Validation
        if (clanLeader1 == null || clanLeader2 == null) {
            System.out.println("ERREUR : Les deux clans doivent être configurés !");
            return;
        }

        if (mainBattlefield == null) {
            System.out.println("ERREUR : Un champ de bataille doit être configuré !");
            return;
        }

        displayStatus();

        for (int round = 1; round <= rounds; round++) {
            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println("║        TOUR " + round + " / " + rounds + "                                    ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");

            // Déployer les guerriers sur le champ de bataille
            deployWarriorsToBattlefield();

            // Combat
            conductBattle();

            // Ramener les survivants
            returnSurvivors();

            // Modifications aléatoires
            randomlyModifyCharacters();

            // Apparition de nourriture
            spawnFood();

            // Dégradation de nourriture
            degradeFood();

            // Donner le contrôle aux chefs de clan
            if (clanLeader1 != null) {
                giveClanLeaderControl(clanLeader1, scanner, 2);
            }

            if (clanLeader2 != null) {
                giveClanLeaderControl(clanLeader2, scanner, 2);
            }

            System.out.println("\n--- Fin du tour " + round + " ---");

            if (round < rounds) {
                System.out.print("\nAppuyez sur Entrée pour continuer au tour suivant...");
                scanner.nextLine();
            }
        }

        // Afficher les résultats finaux
        displayFinalResults();

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║         SIMULATION TERMINÉE                            ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }

    /**
     * Obtient le nombre total de personnages dans la simulation.
     *
     * @return le nombre total de personnages
     */
    public int getTotalCharacters() {
        int total = 0;
        for (Place place : places) {
            total += place.getNumberOfCharacters();
        }
        return total;
    }

    /**
     * Obtient la liste des lieux.
     *
     * @return la liste des lieux
     */
    public List<Place> getPlaces() {
        return new ArrayList<>(places);
    }
}

