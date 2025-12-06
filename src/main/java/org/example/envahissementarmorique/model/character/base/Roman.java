package org.example.envahissementarmorique.model.character.base;

public class Roman extends GameCharacter {
    public Roman(String name, String genre, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicpotion) {
        super(name, genre, "Roman", height, age, strength, endurance, health, hunger, belligerence, magicpotion);
        this.maxHealth = health;
    }
}
