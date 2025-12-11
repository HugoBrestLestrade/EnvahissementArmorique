package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.Gaulish.Gaulois;
import org.example.envahissementarmorique.model.character.base.Roman.Roman;
import org.example.envahissementarmorique.model.character.base.FantasticCreature;
import org.example.envahissementarmorique.model.item.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un champ de bataille.
 *
 * <p>
 * Peut contenir tous les types de personnages.
 * Il n'y a pas de chef de clan assigné.
 * </p>
 */
public final class Battlefield extends Place {

    /**
     * Constructeur d'un champ de bataille.
     *
     * @param name nom du champ de bataille
     * @param area superficie en mètres carrés
     */
    public Battlefield(String name, float area) {
        super(name, area, null); // Pas de chef pour un champ de bataille
    }

    /**
     * Vérifie si un personnage peut entrer dans ce lieu.
     *
     * @param c personnage à tester
     * @return true si le personnage peut entrer, false sinon
     */
    @Override
    protected boolean canAddCharacter(GameCharacter c) {
        return true; // Tous les personnages peuvent entrer sur un champ de bataille
    }

    /**
     * Affiche les informations du champ de bataille.
     */
    @Override
    public void display() {
        System.out.println("\n========================================");
        System.out.println("BATTLEFIELD : " + name);
        System.out.println("Area : " + area + " m²");
        System.out.println("Chief : None (combat zone)");

        System.out.println("\nNumber of fighters : " + characters.size());
        if (!characters.isEmpty()) {
            System.out.println("Fighters present :");
            for (GameCharacter c : characters) {
                System.out.println("  • " + c.toString() +
                        (c.isDead() ? " [DEAD]" : " [Health: " + c.getHealth() + "]"));
            }
        } else {
            System.out.println("  (No fighters)");
        }

        System.out.println("\nNumber of foods : " + foods.size());
        if (!foods.isEmpty()) {
            System.out.println("Foods present :");
            for (Food f : foods) {
                System.out.println("  • " + f.toString());
            }
        } else {
            System.out.println("  (No food)");
        }
        System.out.println("========================================\n");
    }

    /**
     * Organise un combat entre les personnages présents sur le champ de bataille.
     */
    public void organizeBattle() {
        System.out.println("\nBATTLE ON " + name.toUpperCase());

        if (characters.size() < 2) {
            System.out.println("Not enough fighters to organize a battle.");
            return;
        }

        List<GameCharacter> gauls = getGaulishCharacters();
        List<GameCharacter> romans = getRomanCharacters();
        List<GameCharacter> creatures = getFantasticCreatures();

        battleBetweenCamps(gauls, romans);

        if (!creatures.isEmpty()) {
            battleWithCreatures(creatures, gauls);
            battleWithCreatures(creatures, romans);
        }

        System.out.println("End of battle on " + name);
    }

    /**
     * Combat entre deux groupes de personnages.
     */
    private void battleBetweenCamps(List<GameCharacter> camp1, List<GameCharacter> camp2) {
        int i = 0, j = 0;
        while (i < camp1.size() && j < camp2.size()) {
            GameCharacter c1 = camp1.get(i);
            GameCharacter c2 = camp2.get(j);

            if (!c1.isDead() && !c2.isDead() && c1.isBelligerent() && c2.isBelligerent()) {
                System.out.println("\n" + c1.getName() + " fights " + c2.getName());
                c1.fight(c2);
            }

            if (c1.isDead()) i++;
            if (c2.isDead()) j++;
            if (!c1.isDead() && !c2.isDead()) {
                i++; j++;
            }
        }
    }

    /**
     * Combat entre des créatures fantastiques et un autre groupe.
     */
    private void battleWithCreatures(List<GameCharacter> creatures, List<GameCharacter> others) {
        for (GameCharacter creature : creatures) {
            if (creature.isDead() || !creature.isBelligerent()) continue;

            for (GameCharacter other : others) {
                if (other.isDead() || !other.isBelligerent()) continue;

                System.out.println("\n" + creature.getName() + " attacks " + other.getName());
                creature.fight(other);

                if (creature.isDead()) break;
            }
        }
    }

    /** @return la liste des personnages gaulois présents */
    private List<GameCharacter> getGaulishCharacters() {
        List<GameCharacter> result = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (c instanceof Gaulois) result.add(c);
        }
        return result;
    }

    /** @return la liste des personnages romains présents */
    private List<GameCharacter> getRomanCharacters() {
        List<GameCharacter> result = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (c instanceof Roman) result.add(c);
        }
        return result;
    }

    /** @return la liste des créatures fantastiques présentes */
    private List<GameCharacter> getFantasticCreatures() {
        List<GameCharacter> result = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (c instanceof FantasticCreature) result.add(c);
        }
        return result;
    }

    /**
     * Renvoie les survivants dans leur lieu d'origine.
     *
     * @param allPlaces liste de tous les lieux
     */
    public void sendBackSurvivors(List<Place> allPlaces) {
        System.out.println("\nSending back survivors to their original places...");

        List<GameCharacter> toRemove = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (!c.isDead()) {
                Place originPlace = c.getOriginPlace();
                if (originPlace != null && originPlace != this) {
                    if (originPlace.addCharacter(c)) {
                        toRemove.add(c);
                        System.out.println(c.getName() + " returns to " + originPlace.getName());
                    }
                }
            }
        }

        characters.removeAll(toRemove);
        removeDeadCharacters();
    }

    /**
     * Compte le nombre de morts dans le champ de bataille.
     *
     * @return nombre de personnages morts
     */
    public int countDeaths() {
        int count = 0;
        for (GameCharacter c : characters) {
            if (c.isDead()) count++;
        }
        return count;
    }
}
