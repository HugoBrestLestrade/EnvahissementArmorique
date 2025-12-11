package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.FantasticCreature;
import org.example.envahissementarmorique.model.character.base.Roman.Roman;
import org.example.envahissementarmorique.model.character.base.ClanLeader;

/**
 * Représente une ville romaine.
 * <p>
 * Ce lieu est réservé aux combattants romains et aux créatures fantastiques.
 * Une ville peut contenir un gouverneur (chef de clan) et des stocks de nourriture.
 */
public final class RomanCity extends Place {

    /**
     * Crée une nouvelle ville romaine.
     *
     * @param name  Nom de la ville
     * @param area  Superficie en m²
     * @param chief Gouverneur de la ville (peut être null)
     */
    public RomanCity(String name, float area, ClanLeader chief) {
        super(name, area, chief);
    }

    /**
     * Vérifie si un personnage peut entrer dans la ville.
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
     * Ajoute un personnage dans la ville si autorisé.
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
                    + " n'est pas autorisé à entrer dans la ville romaine !");
            System.out.println("   (Seuls les Romains et créatures fantastiques sont acceptés)");
            return false;
        }

        characters.add(c);
        System.out.println(c.getName() + " entre dans la ville " + name);
        return true;
    }

    /**
     * Affiche les informations détaillées de la ville romaine.
     * <p>
     * Comprend le nom, la superficie, le gouverneur, les habitants présents et le ravitaillement.
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
}
