package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.ClanLeader;
import org.example.envahissementarmorique.model.character.interfaces.Fighter;
import org.example.envahissementarmorique.model.character.base.Roman.Roman;
import org.example.envahissementarmorique.model.character.base.FantasticCreature;
import org.example.envahissementarmorique.model.item.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un camp retranché romain.
 *
 * <p>
 * Peut contenir uniquement des combattants romains et des créatures fantastiques.
 * Chaque camp possède un chef (optionnel).
 * </p>
 */
public final class Camp extends Place {

    /**
     * Constructeur d'un camp romain.
     *
     * @param name nom du camp
     * @param area superficie en mètres carrés
     * @param chief chef du camp
     */
    public Camp(String name, float area, ClanLeader chief) {
        super(name, area, chief);
    }

    /**
     * Vérifie si un personnage peut entrer dans le camp.
     *
     * @param c personnage à tester
     * @return true si le personnage est un combattant romain ou une créature fantastique
     */
    @Override
    protected boolean canAddCharacter(GameCharacter c) {
        if (c instanceof FantasticCreature) return true;
        if (c instanceof Roman && c instanceof Fighter) return true;
        return false;
    }

    /**
     * Affiche les informations détaillées du camp.
     */
    @Override
    public void display() {
        System.out.println("\n========================================");
        System.out.println("ROMAN FORTIFIED CAMP : " + name);
        System.out.println("Area : " + area + " m²");

        if (chief != null) {
            System.out.println("Chief : " + chief.getName());
        } else {
            System.out.println("Chief : None");
        }

        System.out.println("\nGarrison : " + characters.size() + " fighter(s)");
        if (!characters.isEmpty()) {
            System.out.println("Fighters present :");
            int legionnaires = 0;
            int generals = 0;
            int creatures = 0;

            for (GameCharacter c : characters) {
                String status = c.isDead() ? " [DEAD]" : " [Health: " + c.getHealth() + "]";
                System.out.println("  • " + c.toString() + status);

                if (c instanceof FantasticCreature) creatures++;
                else if (c.getClass().getSimpleName().equals("General")) generals++;
                else if (c.getClass().getSimpleName().equals("Legionnaire")) legionnaires++;
            }

            System.out.println("\nGarrison composition:");
            System.out.println("  - Legionnaires : " + legionnaires);
            System.out.println("  - Generals : " + generals);
            System.out.println("  - Creatures : " + creatures);
        } else {
            System.out.println("  (Camp abandoned)");
        }

        System.out.println("\nNumber of foods : " + foods.size());
        if (!foods.isEmpty()) {
            System.out.println("Supplies :");
            for (Food f : foods) {
                System.out.println("  • " + f.toString());
            }
        } else {
            System.out.println("  (No supplies)");
        }
        System.out.println("========================================\n");
    }

    /**
     * Vérifie si le camp est défendu.
     *
     * @return true si le camp compte au moins 3 combattants vivants
     */
    public boolean isDefended() {
        int aliveFighters = 0;
        for (GameCharacter c : characters) {
            if (!c.isDead() && c instanceof Fighter) aliveFighters++;
        }
        return aliveFighters >= 3;
    }

    /**
     * Renvoie la liste des légionnaires présents dans le camp.
     */
    public List<GameCharacter> getLegionnaires() {
        List<GameCharacter> legionnaires = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (c.getClass().getSimpleName().equals("Legionnaire")) {
                legionnaires.add(c);
            }
        }
        return legionnaires;
    }

    /**
     * Renvoie la liste des généraux présents dans le camp.
     */
    public List<GameCharacter> getGenerals() {
        List<GameCharacter> generals = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (c.getClass().getSimpleName().equals("General")) {
                generals.add(c);
            }
        }
        return generals;
    }

    /**
     * Prépare tous les combattants du camp pour la bataille.
     */
    public void prepareForBattle() {
        System.out.println("\n" + name + " is preparing troops for battle!");
        for (GameCharacter c : characters) {
            if (!c.isDead() && c instanceof Fighter) {
                System.out.println("  - " + c.getName() + " is preparing for battle");
            }
        }
    }

    /**
     * Entraîne les légionnaires présents dans le camp.
     */
    public void trainTroops() {
        System.out.println("\nTraining at camp " + name);
        int trained = 0;
        for (GameCharacter c : characters) {
            if (!c.isDead() && c.getClass().getSimpleName().equals("Legionnaire")) {
                System.out.println("  - " + c.getName() + " is training");
                trained++;
            }
        }
        if (trained == 0) {
            System.out.println("  No legionnaire available for training");
        } else {
            System.out.println("  " + trained + " legionnaire(s) trained");
        }
    }

    /**
     * Affiche l'état actuel de la garnison.
     */
    public void displayGarrisonStatus() {
        System.out.println("\nGarrison status - " + name);
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

        System.out.println("Total : " + total + " soldiers");
        System.out.println("Alive : " + alive + " soldiers");
        System.out.println("Wounded : " + wounded + " soldiers");
        System.out.println("Dead : " + (total - alive) + " soldiers");

        if (isDefended()) {
            System.out.println("Status : Well defended camp");
        } else {
            System.out.println("Status : Vulnerable camp - reinforcements needed");
        }
    }
}
