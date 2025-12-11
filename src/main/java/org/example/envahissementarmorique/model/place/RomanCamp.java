package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.Roman.Roman;
import org.example.envahissementarmorique.model.character.base.FantasticCreature;
import org.example.envahissementarmorique.model.character.base.ClanLeader;

/**
 * Camp retranché romain.
 * <p>
 * Ce lieu est réservé aux combattants romains et aux créatures fantastiques.
 * Il peut contenir un chef de clan (commandant) et diverses rations de nourriture.
 */
public final class RomanCamp extends Place {

    /**
     * Crée un nouveau camp romain.
     *
     * @param name  Nom du camp
     * @param area  Superficie en m²
     * @param chief Commandant du camp (peut être null)
     */
    public RomanCamp(String name, float area, ClanLeader chief) {
        super(name, area, chief);
    }

    /**
     * Vérifie si un personnage peut entrer dans le camp.
     * <p>
     * Seuls les Romains et les créatures fantastiques sont autorisés.
     *
     * @param c Personnage à tester
     * @return true si le personnage peut entrer, false sinon
     */
    @Override
    protected boolean canAddCharacter(GameCharacter c) {
        return (c instanceof Roman || c instanceof FantasticCreature);
    }

    /**
     * Ajoute un personnage dans le camp si autorisé.
     *
     * @param c Personnage à ajouter
     * @return true si le personnage a été ajouté, false sinon
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

    /**
     * Affiche les informations détaillées du camp romain.
     * <p>
     * Comprend le nom, la superficie, le commandant, les soldats présents et le ravitaillement.
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
}
