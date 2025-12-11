package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.ClanLeader;
import org.example.envahissementarmorique.model.character.base.FantasticCreature;

/**
 * Représente un village gaulois.
 * <p>
 * Ce lieu est réservé aux Gaulois et aux créatures fantastiques.
 * Un village peut contenir un chef de village et un stock de nourriture.
 */
public final class Village extends Place {

    /**
     * Crée un nouveau village gaulois.
     *
     * @param name  Nom du village
     * @param area  Superficie en m²
     * @param chief Chef du village (peut être null)
     */
    public Village(String name, float area, ClanLeader chief) {
        super(name, area, chief);
    }

    /**
     * Vérifie si un personnage peut entrer dans le village.
     * <p>
     * Seuls les Gaulois et les créatures fantastiques sont autorisés.
     *
     * @param c Personnage à tester
     * @return true si le personnage peut entrer, false sinon
     */
    @Override
    protected boolean canAddCharacter(GameCharacter c) {
        return c.getFaction().equals("Gaulois") || c instanceof FantasticCreature;
    }

    /**
     * Ajoute un personnage dans le village si autorisé.
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
            System.out.println(c.getName() + " n'est pas autorisé à entrer dans le village !");
            System.out.println("(Seuls les Gaulois et les créatures fantastiques sont acceptés)");
            return false;
        }

        characters.add(c);
        System.out.println(c.getName() + " entre dans le village " + name);
        return true;
    }

    /**
     * Affiche les informations détaillées du village.
     * <p>
     * Comprend le nom, la superficie, le chef, les habitants présents et la nourriture disponible.
     */
    @Override
    public void display() {
        System.out.println("\n========================================");
        System.out.println("VILLAGE : " + name);
        System.out.println("Superficie : " + area + " m²");

        if (chief != null) {
            System.out.println("Chef du village : " + chief.getName());
        } else {
            System.out.println("Chef du village : Aucun");
        }

        System.out.println("\nHabitants présents : " + characters.size());
        if (!characters.isEmpty()) {
            for (GameCharacter c : characters) {
                System.out.println("  • " + c.getName() +
                        " [Faction: " + c.getFaction() +
                        ", Santé: " + c.getHealth() + "]");
            }
        } else {
            System.out.println("  (Aucun habitant)");
        }

        System.out.println("\nNourriture disponible : " + foods.size());
        if (!foods.isEmpty()) {
            for (var f : foods) {
                System.out.println("  • " + f);
            }
        } else {
            System.out.println("  (Aucune nourriture)");
        }

        System.out.println("========================================\n");
    }
}
