package org.example.envahissementarmorique.model.character.base.Roman;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

/**
 * Represents a Roman character.
 * Romans are the invading forces trying to conquer Armorique.
 * They belong to the "Roman" faction.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class Roman extends GameCharacter {

    /**
     * Creates a new Roman character.
     * The faction is automatically set to "Roman".
     *
     * @param name the character's name
     * @param genre the character's gender
     * @param height the character's height in meters
     * @param age the character's age in years
     * @param strength the character's strength level
     * @param endurance the character's endurance level
     * @param health the character's initial health points
     * @param hunger the character's initial hunger level
     * @param belligerence the character's belligerence level
     * @param magicpotion the character's initial magic potion level
     */
    public Roman(String name, String genre, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicpotion) {
        super(name, genre, "Roman", height, age, strength, endurance, health, hunger, belligerence, magicpotion);
        this.maxHealth = health;
    }
}
