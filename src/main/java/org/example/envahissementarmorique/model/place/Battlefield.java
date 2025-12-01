package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.FantasticCreature;
import org.example.envahissementarmorique.model.character.base.Roman;
import org.example.envahissementarmorique.model.character.base.Gaulois;
import org.example.envahissementarmorique.model.item.Food;

import java.util.ArrayList;
import java.util.List;

/**

 * Champ de bataille - peut contenir tous les types de personnages
 * N'a pas de chef de clan
 */
public class Battlefield extends Place {

    public Battlefield(String name, float area) {
        super(name, area, null); // Pas de chef pour un champ de bataille
    }

    @Override
    protected boolean canAddCharacter(GameCharacter c) {
        return true; // Tous les personnages peuvent entrer sur un champ de bataille
    }

    @Override
    public void display() {
        System.out.println("\n========================================");
        System.out.println("CHAMP DE BATAILLE : " + name);
        System.out.println("Superficie : " + area + " m²");
        System.out.println("Chef : Aucun (zone de combat)");

        System.out.println("\nNombre de combattants : " + characters.size());
        if (!characters.isEmpty()) {
            System.out.println("Combattants présents :");
            for (GameCharacter c : characters) {
                System.out.println("  • " + c.toString() +
                        (c.isDead() ? " [MORT]" : " [Santé: " + c.getHealth() + "]"));
            }
        } else {
            System.out.println("  (Aucun combattant)");
        }

        System.out.println("\nNombre d'aliments : " + foods.size());
        if (!foods.isEmpty()) {
            System.out.println("Aliments présents :");
            for (Food f : foods) {
                System.out.println("  • " + f.toString());
            }
        } else {
            System.out.println("  (Aucun aliment)");
        }
        System.out.println("========================================\n");

    }

    public void organizeBattle() {
        System.out.println("\nCOMBAT SUR " + name.toUpperCase());

        if (characters.size() < 2) {
            System.out.println("Pas assez de combattants pour organiser un combat.");
            return;
        }

        List<GameCharacter> gaulois = getGauloisCharacters();
        List<GameCharacter> romans = getRomanCharacters();
        List<GameCharacter> creatures = getFantasticCreatures();

        battleBetweenCamps(gaulois, romans);

        if (!creatures.isEmpty()) {
            battleWithCreatures(creatures, gaulois);
            battleWithCreatures(creatures, romans);
        }

        System.out.println("Fin du combat sur " + name);

    }

    private void battleBetweenCamps(List<GameCharacter> camp1, List<GameCharacter> camp2) {
        int i = 0, j = 0;
        while (i < camp1.size() && j < camp2.size()) {
            GameCharacter c1 = camp1.get(i);
            GameCharacter c2 = camp2.get(j);

            if (!c1.isDead() && !c2.isDead() && c1.isBelligerent() && c2.isBelligerent()) {
                System.out.println("\n" + c1.getName() + " affronte " + c2.getName());
                c1.fight(c2);
            }

            if (c1.isDead()) i++;
            if (c2.isDead()) j++;
            if (!c1.isDead() && !c2.isDead()) {
                i++; j++;
            }
        }

    }

    private void battleWithCreatures(List<GameCharacter> creatures, List<GameCharacter> others) {
        for (GameCharacter creature : creatures) {
            if (creature.isDead() || !creature.isBelligerent()) continue;

            for (GameCharacter other : others) {
                if (other.isDead() || !other.isBelligerent()) continue;

                System.out.println("\n" + creature.getName() + " attaque " + other.getName());
                creature.fight(other);

                if (creature.isDead()) break;
            }
        }

    }

    private List<GameCharacter> getGauloisCharacters() {
        List<GameCharacter> result = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (c instanceof Gaulois) result.add(c);
        }
        return result;
    }

    private List<GameCharacter> getRomanCharacters() {
        List<GameCharacter> result = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (c instanceof Roman) result.add(c);
        }
        return result;
    }

    private List<GameCharacter> getFantasticCreatures() {
        List<GameCharacter> result = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (c instanceof FantasticCreature) result.add(c);
        }
        return result;
    }

    public void sendBackSurvivors(List<Place> allPlaces) {
        System.out.println("\nRenvoi des survivants dans leur lieu d'origine...");

        List<GameCharacter> toRemove = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (!c.isDead()) {
                Place originPlace = c.getOriginPlace();
                if (originPlace != null && originPlace != this) {
                    if (originPlace.addCharacter(c)) {
                        toRemove.add(c);
                        System.out.println(c.getName() + " retourne à " + originPlace.getName());
                    }
                }
            }
        }

        characters.removeAll(toRemove);
        removeDeadCharacters();

    }

    public int countDeaths() {
        int count = 0;
        for (GameCharacter c : characters) {
            if (c.isDead()) count++;
        }
        return count;
    }
}
