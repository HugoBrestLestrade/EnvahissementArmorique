package org.example.envahissementarmorique.model.character.base;

import org.example.envahissementarmorique.model.character.interfaces.Fighter;
import org.example.envahissementarmorique.model.item.Food;
import org.example.envahissementarmorique.model.item.Potion;
import org.example.envahissementarmorique.model.place.Place;

public class GameCharacter implements Fighter {
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
    protected int maxHealth;


    public GameCharacter(String name, String genre, String faction, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicpotion) {
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

    public String getFaction() {
        return faction;
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

    public void setFaction(String faction) {
        this.faction = faction;
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

    //methods
    public void ToHeal(int amount) {
        if (amount <= 0) return;

        this.health += amount;
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }


    }
    public boolean canEat(Food food) {
        if (this.health <= 0) return false;

        if (this.faction.equals("Gaulois") && food.getName().equals("poisson") && !food.isFresh()) {
            return false;
        }

        return true;
    }
    public void ToEat(Food food) {
        if (!canEat(food)) {
            System.out.println(name + " refuse de manger " + food.getName());
            return;
        }

        this.health += food.getNutrition();

        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }

        if (!food.isFresh()) {
            this.health -= 5;
        }

        System.out.println(name + " mange " + food.getName() + " et a maintenant " + this.health + " PV.");
    }
    public boolean isDead() {
        return this.health <= 0;
    }

    public boolean isBelligerent() {
        return this.belligerence > 0;
    }

    public void fight(GameCharacter opponent) {
        if (opponent == null || opponent.isDead()) return;

        int damage = this.strength + (this.belligerence / 10);
        opponent.takeDamage(damage);

        if (!opponent.isDead()) {
            int counterDamage = opponent.getStrength() + (opponent.getBelligerence() / 10);
            this.takeDamage(counterDamage);
        }
    }
    public void drinkMagicPotion(Potion potion) {
        // version totalement minimaliste (compilation OK)
        System.out.println("Potion bue.");
    }
    public void heal(int amount) {
    }
    public boolean needsFood() {
        return false;
    }
    public void eat(Food food) {
        if (food == null) return;
        this.hunger = Math.max(0, this.hunger - food.getNutrition());
    }
    public int getMagicPotionLevel() {
        return this.magicpotion;
    }





    public Place getOriginPlace() {
        return null; // À implémenter selon votre logique
    }

    @Override
    public void takeDamage(int damage) {
        this.setHealth(this.getHealth() - damage);
    }
}
