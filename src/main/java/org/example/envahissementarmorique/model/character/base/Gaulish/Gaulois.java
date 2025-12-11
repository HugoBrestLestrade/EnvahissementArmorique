package org.example.envahissementarmorique.model.character.base.Gaulish;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

/**
 * Représente un personnage de type Gaulois.
 * Les Gaulois sont le peuple de Gaule résistant à l'invasion romaine.
 * Ils appartiennent automatiquement à la faction "Gaulois".
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class Gaulois extends GameCharacter {

    /**
     * Crée un nouveau personnage Gaulois.
     * La faction est automatiquement définie sur "Gaulois".
     *
     * @param name          le nom du personnage
     * @param genre         le genre du personnage
     * @param height        la taille du personnage en mètres
     * @param age           l’âge du personnage en années
     * @param strength      le niveau de force du personnage
     * @param endurance     le niveau d’endurance du personnage
     * @param health        les points de vie initiaux
     * @param hunger        le niveau de faim initial
     * @param belligerence  le niveau de belligerence du personnage
     * @param magicpotion   le niveau initial de potion magique
     */
    public Gaulois(String name, String genre, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicpotion) {
        super(name, genre, "Gaulois", height, age, strength, endurance, health, hunger, belligerence, magicpotion);
        this.maxHealth = health;
    }
}
