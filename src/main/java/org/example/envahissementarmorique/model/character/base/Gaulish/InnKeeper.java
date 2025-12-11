package org.example.envahissementarmorique.model.character.base.Gaulish;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

/**
 * Represents an InnKeeper character.
 * InnKeepers manage inns and provide food and shelter.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class InnKeeper extends GameCharacter {

    /**
     * Creates a new InnKeeper character.
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
    public InnKeeper(String name, String genre, String faction, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicpotion) {
        super(name, genre, faction, height, age, strength, endurance, health, hunger, belligerence, magicpotion);
    }
}
