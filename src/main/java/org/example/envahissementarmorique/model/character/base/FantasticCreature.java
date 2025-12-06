package org.example.envahissementarmorique.model.character.base;

public class FantasticCreature extends GameCharacter {
    public FantasticCreature(String name, String genre, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicpotion) {
        super(name, genre, "Créature", height, age, strength, endurance, health, hunger, belligerence, magicpotion);
        this.maxHealth = health;
    }
}