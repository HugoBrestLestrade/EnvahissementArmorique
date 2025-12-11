package org.example.envahissementarmorique.model.character.base;

/**
 * Représente un personnage de type Créature Fantastique.
 * <p>
 * Les créatures fantastiques sont des êtres magiques possédant des capacités spéciales.
 * Elles appartiennent à la faction "Créature".
 * </p>
 *
 * @author Envahissement Armorique
 * @version 1.0
 */
public class FantasticCreature extends GameCharacter {

    /**
     * Crée un nouveau personnage de type Créature Fantastique.
     * La faction est automatiquement définie sur "Créature".
     *
     * @param name le nom du personnage
     * @param genre le genre du personnage
     * @param height la taille du personnage en mètres
     * @param age l'âge du personnage en années
     * @param strength le niveau de force du personnage
     * @param endurance le niveau d'endurance du personnage
     * @param health les points de vie initiaux
     * @param hunger le niveau de faim initial
     * @param belligerence le niveau de belliqueux
     * @param magicpotion le niveau initial de potion magique
     */
    public FantasticCreature(String name, String genre, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicpotion) {
        super(name, genre, "Créature", height, age, strength, endurance, health, hunger, belligerence, magicpotion);
        this.maxHealth = health;
    }
}
