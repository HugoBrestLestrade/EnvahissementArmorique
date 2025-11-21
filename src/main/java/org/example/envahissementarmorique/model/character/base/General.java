package org.example.envahissementarmorique.model.character.base;

import org.example.envahissementarmorique.model.character.interfaces.TypeCombat;

import java.util.Objects;

public class General extends Character implements TypeCombat {

    public General(String name, String genre, String faction, double height, int age,
                   int strength, int endurance, int health, int hunger,
                   int belligerence, int magicpotion) {
        super(name, genre, faction, height, age, strength, endurance,
                health, hunger, belligerence, magicpotion);
    }

    @Override
    public void battre(Character ally) {
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
    public void combattre(Character ennemy, String location) {
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
