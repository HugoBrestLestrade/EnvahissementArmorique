package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.ClanLeader;
import org.example.envahissementarmorique.model.character.interfaces.Fighter;
import org.example.envahissementarmorique.model.character.base.Roman;
import org.example.envahissementarmorique.model.character.base.FantasticCreature;
import org.example.envahissementarmorique.model.item.Food;

import java.util.ArrayList;
import java.util.List;

/**

 * Camp retranché romain - ne peut contenir que des combattants romains et des créatures fantastiques
 */
public class Camp extends Place {

    public Camp(String name, float area, ClanLeader chief) {
        super(name, area, chief);
    }

    @Override
    protected boolean canAddCharacter(GameCharacter c) {
        if (c instanceof FantasticCreature) return true;
        if (c instanceof Roman && c instanceof Fighter) return true;
        return false;
    }

    @Override
    public void display() {
        System.out.println("\n========================================");
        System.out.println("CAMP RETRANCHÉ ROMAIN : " + name);
        System.out.println("Superficie : " + area + " m²");

        if (chief != null) {
            System.out.println("Chef : " + chief.getName());
        } else {
            System.out.println("Chef : Aucun");
        }

        System.out.println("\nGarnison : " + characters.size() + " combattant(s)");
        if (!characters.isEmpty()) {
            System.out.println("Combattants présents :");
            int legionnaires = 0;
            int generaux = 0;
            int creatures = 0;

            for (GameCharacter c : characters) {
                String status = c.isDead() ? " [MORT]" : " [Santé: " + c.getHealth() + "]";
                System.out.println("  • " + c.toString() + status);

                if (c instanceof FantasticCreature) {
                    creatures++;
                } else if (c.getClass().getSimpleName().equals("General")) {
                    generaux++;
                } else if (c.getClass().getSimpleName().equals("Legionnaire")) {
                    legionnaires++;
                }
            }

            System.out.println("\nComposition de la garnison :");
            System.out.println("  - Légionnaires : " + legionnaires);
            System.out.println("  - Généraux : " + generaux);
            System.out.println("  - Créatures : " + creatures);
        } else {
            System.out.println("  (Camp abandonné)");
        }

        System.out.println("\nNombre d'aliments : " + foods.size());
        if (!foods.isEmpty()) {
            System.out.println("Provisions :");
            for (Food f : foods) {
                System.out.println("  • " + f.toString());
            }
        } else {
            System.out.println("  (Aucune provision)");
        }
        System.out.println("========================================\n");


    }

    public boolean isDefended() {
        int aliveFighters = 0;
        for (GameCharacter c : characters) {
            if (!c.isDead() && c instanceof org.example.envahissementarmorique.model.character.interfaces.Fighter) aliveFighters++;
        }
        return aliveFighters >= 3;
    }

    public List<GameCharacter> getLegionnaires() {
        List<GameCharacter> legionnaires = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (c.getClass().getSimpleName().equals("Legionnaire")) {
                legionnaires.add(c);
            }
        }
        return legionnaires;
    }

    public List<GameCharacter> getGenerals() {
        List<GameCharacter> generals = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (c.getClass().getSimpleName().equals("General")) {
                generals.add(c);
            }
        }
        return generals;
    }

    public void prepareForBattle() {
        System.out.println("\n" + name + " prépare ses troupes pour la bataille !");
        for (GameCharacter c : characters) {
            if (!c.isDead() && c instanceof Fighter) {
                System.out.println("  - " + c.getName() + " se prépare au combat");
            }
        }
    }

    public void trainTroops() {
        System.out.println("\nEntraînement au camp " + name);
        int trained = 0;
        for (GameCharacter c : characters) {
            if (!c.isDead() && c.getClass().getSimpleName().equals("Legionnaire")) {
                System.out.println("  - " + c.getName() + " s'entraîne");
                trained++;
            }
        }
        if (trained == 0) {
            System.out.println("  Aucun légionnaire disponible pour l'entraînement");
        } else {
            System.out.println("  " + trained + " légionnaire(s) ont été entraîné(s)");
        }
    }

    public void displayGarrisonStatus() {
        System.out.println("\nÉtat de la garnison - " + name);
        int total = 0;
        int alive = 0;
        int wounded = 0;


        for (GameCharacter c : characters) {
            if (c instanceof Roman) {
                total++;
                if (!c.isDead()) {
                    alive++;
                    if (c.getHealth() < 50) wounded++;
                }
            }
        }

        System.out.println("Total : " + total + " soldats");
        System.out.println("Vivants : " + alive + " soldats");
        System.out.println("Blessés : " + wounded + " soldats");
        System.out.println("Morts : " + (total - alive) + " soldats");

        if (isDefended()) {
            System.out.println("Statut : Camp bien défendu");
        } else {
            System.out.println("Statut : Camp vulnérable - renforts nécessaires");
        }

    }
}
