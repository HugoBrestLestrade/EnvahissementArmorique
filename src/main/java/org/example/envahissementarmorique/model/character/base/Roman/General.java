package org.example.envahissementarmorique.model.character.base.Roman;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.interfaces.TypeCombat;

/**
 * Represents a General (Roman military commander) character.
 * Generals are high-ranking Roman officers who lead armies.
 * They have special combat abilities.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class General extends GameCharacter implements TypeCombat {

    /**
     * Creates a new General character.
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
    public General(String name, String genre, String faction, double height, int age,
                   int strength, int endurance, int health, int hunger,
                   int belligerence, int magicpotion) {
        super(name, genre, faction, height, age, strength, endurance,
                health, hunger, belligerence, magicpotion);
    }

    @Override
    public void battre(GameCharacter ally) {
        if (!this.getName().equals(ally.getFaction())) {
            System.out.println(ally.getName() + " et " + this.getName() + " ne font pas partie de la même faction !");
            return;
        }
        if (ally.getStrength() < this.getStrength()) { //TODO : Ensure that both combatants lose life
            System.out.println(ally.getName() + " est plus fort que " + this.getName());
        }
        else { //TODO : Ensure that both combatants lose life
            System.out.println(this.getName() + " est plus fort que " + ally.getName());
        }
    }

    @Override
    public void combattre(GameCharacter ennemy, String location) {
        if (this.getFaction().equals(ennemy.getFaction())) {
            System.out.println(ennemy.getName() + " et " + this.getName() + " font partie de la même faction !");
            return;
        }
        if (ennemy.getStrength() < this.getStrength()) { //TODO : Ensure that both combatants lose life
            System.out.println(this.getName() + " est plus fort que " + ennemy.getName());
        }
        else { //TODO : Ensure that both combatants lose life
            System.out.println(ennemy.getName() + " est plus fort que " + this.getName());
        }
    }
}
