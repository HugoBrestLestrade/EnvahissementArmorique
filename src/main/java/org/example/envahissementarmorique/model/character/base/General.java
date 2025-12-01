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
        if (!this.getFaction().equals(ally.getFaction())) {
            System.out.println(ally.getName() + " et " + this.getName() + " ne font pas partie de la même faction !");
            return;
        }

        System.out.println(ally.getName() + " et " + this.getName() + " vont se battre !");
        //SYSTEME DE COMBAT par tours:

        int attaquerDamage = (int) Math.max(1,
                Math.round(((double)this.getStrength() - ally.getEndurance()) * 5)
        );
        int allyDamage = (int) Math.max(1,
                Math.round(((double)ally.getStrength() - this.getEndurance()) * 5)
        );
        if (attaquerDamage == 1) {
            attaquerDamage = (int) Math.max(1,
                    Math.round(((double)this.getStrength() - (ally.getEndurance()/2)) * 1.75)
            );
        }
        else if (allyDamage == 1) {
            allyDamage = (int) Math.max(1,
                    Math.round(((double)ally.getStrength() - (this.getEndurance()/2)) * 1.75)
            );
        }
        boolean tourAttaquant = true;

        if (!ally.stillAlive() || !this.stillAlive()) {
            System.out.println("Un des combattants est mort, le combat ne peut pas trop commencer");
            return;
        }

        while (ally.stillAlive() && this.stillAlive()) {
            if (tourAttaquant) {
                ally.takeDamage(attaquerDamage);
                System.out.println(ally.getName() + "fait " + allyDamage + " dégâts");
                ally.profile();
                this.profile();
                if (ally.stillAlive()) {
                    System.out.println("Tour de :" + ally.getName());
                }
                else {
                    System.out.println(ally.getName() + " est mort, la gagnant est : " + this.getName());
                    ally.setHealth(0);
                }

            }
            else {
                this.takeDamage(allyDamage);
                System.out.println(this.getName() + "fait " + attaquerDamage + " dégâts");
                ally.profile();
                this.profile();
                if (this.stillAlive()) {
                    System.out.println("Tour de :" + this.getName());
                }
                else {
                    System.out.println(this.getName() + " est mort, la gagnant est : " + ally.getName());
                    this.setHealth(0);
                }
            }
            tourAttaquant = !tourAttaquant;
        }

    }

    @Override
    public void combattre(Character ennemy, String location) {
        if (this.getFaction().equals(ennemy.getFaction())) {
            System.out.println(ennemy.getName() + " et " + this.getName() + " font partie de la même faction !");
            return;
        }
        if (ennemy.getStrength() < this.getStrength()) {
            System.out.println(this.getName() + " est plus fort que " + ennemy.getName());
        }
        else { //TODO : Ensure that both combatants lose life
            System.out.println(ennemy.getName() + " est plus fort que " + this.getName());
        }
    } // TODO
}
