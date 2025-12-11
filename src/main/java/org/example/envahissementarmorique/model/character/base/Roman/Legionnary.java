package org.example.envahissementarmorique.model.character.base.Roman;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

/**
 * Represents a Legionnaire (Roman soldier) character.
 * Legionnaires are basic Roman military units.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class Legionnary extends GameCharacter {

    /**
     * Creates a new Legionnaire character.
     *
     * @param name the character's name
     * @param genre the character's gender
     * @param faction the character's faction
     * @param height the character's height in meters
     * @param age the character's age in years
     * @param strength the character's strength level
     * @param endurance the character's endurance level
     * @param health the character's initial health points
     * @param hunger the character's initial hunger level
     * @param belligerence the character's belligerence level
     * @param magicpotion the character's initial magic potion level
     */
    public Legionnary(String name, String genre, String faction, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicpotion) {
        super(name, genre, faction, height, age, strength, endurance, health, hunger, belligerence, magicpotion);
    }
}
