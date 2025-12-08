package org.example.envahissementarmorique.model.place;


import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.Roman;
import org.example.envahissementarmorique.model.character.base.FantasticCreature;
import org.example.envahissementarmorique.model.character.base.ClanLeader;

/**
 * Camp retranché romain
 * Ne peut contenir que des combattants romains et des créatures fantastiques
 */
public class RomanCamp extends Place {

    public RomanCamp(String name, float area, ClanLeader chief) {
        super(name, area, chief);
    }

    /**
     * Seuls les Romains et les créatures fantastiques sont autorisés
     */
    @Override
    protected boolean canAddCharacter(GameCharacter c) {
        return (c instanceof Roman || c instanceof FantasticCreature);
    }

    /**
     * Affichage personnalisé
     */
    @Override
    public void display() {
        System.out.println("\n========================================");
        System.out.println("CAMP ROMAIN : " + name);
        System.out.println("Superficie : " + area + " m²");

        if (chief != null) {
            System.out.println("Commandant : " + chief.getName());
        } else {
            System.out.println("Commandant : Aucun");
        }

        System.out.println("\nSoldats présents : " + characters.size());
        if (!characters.isEmpty()) {
            for (GameCharacter c : characters) {
                System.out.println("  • " + c.toString() +
                        " [Santé: " + c.getHealth() + "]");
            }
        } else {
            System.out.println("  (Aucun soldat dans le camp)");
        }

        System.out.println("\nRavitaillement : " + foods.size());
        if (!foods.isEmpty()) {
            for (var food : foods) {
                System.out.println("  • " + food);
            }
        } else {
            System.out.println("  (Aucune ration)");
        }

        System.out.println("========================================\n");
    }

    /**
     * Tentative d'ajout d'un personnage dans le camp
     */
    @Override
    public boolean addCharacter(GameCharacter c) {
        if (c == null) {
            System.out.println("Erreur : personnage null");
            return false;
        }

        if (!canAddCharacter(c)) {
            System.out.println(c.getName()
                    + " ne peut pas entrer dans le camp romain !");
            System.out.println("   (Seuls les Romains et créatures fantastiques sont acceptés)");
            return false;
        }

        characters.add(c);
        System.out.println(c.getName() + " entre dans le camp " + name);
        return true;
    }
}
