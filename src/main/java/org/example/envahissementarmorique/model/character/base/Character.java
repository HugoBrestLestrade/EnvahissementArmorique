package org.example.envahissementarmorique.model.character.base;

public class Character {
    private String name;
    private String genre;
    private String faction;
    private double height;
    private int age;
    private int strength;
    private int endurance;
    private int health;
    private int hunger;
    private int belligerence;
    private int magicpotion;

    public Character(String name, String genre, String faction, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicpotion) {
        this.name = name;
        this.genre = genre;
        this.faction = faction;
        this.height = height;
        this.age = age;
        this.strength = strength;
        this.endurance = endurance;
        this.health = health;
        this.hunger = 100;
        this.belligerence = belligerence;
        this.magicpotion = 0;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public double getHeight() {
        return height;
    }

    public int getAge() {
        return age;
    }

    public int getStrength() {
        return strength;
    }

    public int getEndurance() {
        return endurance;
    }

    public int getHealth() {
        return health;
    }

    public int getHunger() {
        return hunger;
    }

    public int getBelligerence() {
        return belligerence;
    }

    public int getMagicpotion() {
        return magicpotion;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public void setBelligerence(int belligerence) {
        this.belligerence = belligerence;
    }

    public void setMagicpotion(int magicpotion) {
        this.magicpotion = magicpotion;
    }
}
