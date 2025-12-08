package org.example.envahissementarmorique.model.character.base;

import org.example.envahissementarmorique.model.item.Potion;

public class Druid extends GameCharacter {

    private int potionPower = 0;
    public Druid(String name, String genre, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicpotion) {
        super(name, genre, "Druide", height, age, strength, endurance, health, hunger, belligerence, magicpotion);
        this.maxHealth = health;
    }

    public Potion concoctPotion() {
        potionPower += 10;
        System.out.println("Potion concoctée ! Niveau : " + potionPower);
        return new Potion(potionPower);
    }

    public int getPotionPower() {
        return potionPower;
    }
}
