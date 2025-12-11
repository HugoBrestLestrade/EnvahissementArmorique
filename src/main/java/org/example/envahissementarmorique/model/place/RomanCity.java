package org.example.envahissementarmorique.model.place;


import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.FantasticCreature;
import org.example.envahissementarmorique.model.character.base.Roman.Roman;
import org.example.envahissementarmorique.model.character.base.ClanLeader;

/**
 * Ville romaine
 * Ne peut contenir que des combattants romains et des créatures fantastiques
 */
public final class RomanCity extends Place {

    public RomanCity(String name, float area, ClanLeader chief) {
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
        System.out.println("VILLE ROMAINE : " + name);
        System.out.println("Superficie : " + area + " m²");

        if (chief != null) {
            System.out.println("Gouverneur : " + chief.getName());
        } else {
            System.out.println("Gouverneur : Aucun");
        }

        System.out.println("\nHabitants présents : " + characters.size());
        if (!characters.isEmpty()) {
            for (GameCharacter c : characters) {
                System.out.println("  • " + c.toString() +
                        " [Santé: " + c.getHealth() + "]");
            }
        } else {
            System.out.println("  (Aucun habitant)");
        }

        System.out.println("\nRavitaillement : " + foods.size());
        if (!foods.isEmpty()) {
            for (var food : foods) {
                System.out.println("  • " + food);
            }
        } else {
            System.out.println("  (Aucun stock)");
        }

        System.out.println("========================================\n");
    }

    /**
     * Tentative d'ajout d'un personnage dans la ville
     */
    @Override
    public boolean addCharacter(GameCharacter c) {
        if (c == null) {
            System.out.println("Erreur : personnage null");
            return false;
        }

        if (!canAddCharacter(c)) {
            System.out.println(c.getName()
                    + " n'est pas autorisé à entrer dans la ville romaine !");
            System.out.println("   (Seuls les Romains et créatures fantastiques sont acceptés)");
            return false;
        }

        characters.add(c);
        System.out.println(c.getName() + " entre dans la ville " + name);
        return true;
    }
}
