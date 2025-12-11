package org.example.envahissementarmorique.model.character.base;

import org.example.envahissementarmorique.model.character.interfaces.Fighter;
import org.example.envahissementarmorique.model.item.Food;
import org.example.envahissementarmorique.model.place.Place;

/**
 * Represents a game character with various attributes and abilities.
 * This is the base class for all character types in the simulation.
 * <p>
 * A character has health, hunger, strength, endurance and can fight, eat, and drink potions.
 * Characters can belong to different factions (Gaulois, Roman, etc.).
 * </p>
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class GameCharacter implements Fighter {
    /**
     * The name of the character.
     */
    private String name;

    /**
     * The gender of the character.
     */
    private String genre;

    /**
     * The faction the character belongs to (Gaulois, Roman, etc.).
     */
    private String faction;

    /**
     * The height of the character in meters.
     */
    private double height;

    /**
     * The age of the character in years.
     */
    private int age;

    /**
     * The strength level of the character.
     */
    private int strength;

    /**
     * The endurance level of the character.
     */
    private int endurance;

    /**
     * The current health points of the character.
     */
    private int health;

    /**
     * The current hunger level of the character.
     */
    private int hunger;

    /**
     * The belligerence level of the character (affects combat).
     */
    private int belligerence;

    /**
     * The magic potion level of the character.
     */
    private int magicpotion;

    /**
     * The maximum health points the character can have.
     */
    protected int maxHealth;

    /**
     * Creates a new GameCharacter with the specified attributes.
     *
     * @param name The name of the character
     * @param genre The gender of the character
     * @param faction The faction the character belongs to
     * @param height The height of the character in meters
     * @param age The age of the character in years
     * @param strength The strength level
     * @param endurance The endurance level
     * @param health The initial health points
     * @param hunger The initial hunger level
     * @param belligerence The belligerence level
     * @param magicpotion The initial magic potion level
     */
    public GameCharacter(String name, String genre, String faction, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicpotion) {
        this.name = name;
        this.genre = genre;
        this.faction = faction;
        this.height = height;
        this.age = age;
        this.strength = strength;
        this.endurance = endurance;
        this.health = health;
        this.maxHealth = health;
        this.hunger = hunger;
        this.belligerence = belligerence;
        this.magicpotion = magicpotion;
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

    /**
     * Makes the character eat food.
     * Uses Food.consume() to apply proper effects.
     *
     * @param food The food to eat.
     */
    public void ToEat(Food food) {
        if (this.health <= 0) {
            System.out.println(name + " is dead and cannot eat.");
            return;
        }

        // Use Food.consume() method which handles rotten food logic
        food.consume(this);
    }

    /**
     * Makes the character drink a potion.
     * Uses Potion.drink() to apply potion effects.
     *
     * @param potion The potion to drink.
     */
    public void ToDrinkPotion(org.example.envahissementarmorique.model.item.Potion potion) {
        if (this.health <= 0) {
            System.out.println(name + " is dead and cannot drink potion.");
            return;
        }

        // Use Potion.drink() method which handles all potion effects
        potion.drink(this);
    }
//    public boolean canEat(Food food) {
//        if (this.health <= 0) return false;
//
//        if (this.faction.equals("Gaulois") && food.getName().equals("poisson") && !food.isFresh()) {
//            return false;
//        }
//
//        return true;
//    }
//    public void ToEat(Food food) {
//        if (!canEat(food)) {
//            System.out.println(name + " refuse de manger " + food.getName());
//            return;
//        }
//
//        this.health += food.getNutrition();
//
//        if (this.health > this.maxHealth) {
//            this.health = this.maxHealth;
//        }
//
//        if (!food.isFresh()) {
//            this.health -= 5;
//        }
//
//        System.out.println(name + " mange " + food.getName() + " et a maintenant " + this.health + " PV.");
//    }
    public boolean isDead() {
        return this.health <= 0;
    }

    public boolean isBelligerent() {
        return this.belligerence > 0;
    }

    public void fight(GameCharacter opponent) {
        if (opponent == null || opponent.isDead()) return;

        // Reduced damage calculation for better balance - characters won't die in first round
        int damage = Math.max(5, (this.strength / 4) + (this.belligerence / 20));
        opponent.takeDamage(damage);

        if (!opponent.isDead()) {
            int counterDamage = Math.max(5, (opponent.getStrength() / 4) + (opponent.getBelligerence() / 20));
            this.takeDamage(counterDamage);
        }
    }

    public Place getOriginPlace() {
        return null; // À implémenter selon votre logique
    }

    @Override
    public void takeDamage(int damage) {
        this.setHealth(this.getHealth() - damage);
    }

    public void battre(GameCharacter ally) {
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
                System.out.println();
                System.out.println(this.getName() + " attaque " + ally.getName() + " et lui fait " + attaquerDamage + " dégâts");
                ally.profile();
                this.profile();
                System.out.println();
                if (ally.stillAlive()) {
                    System.out.println("Tour de : " + ally.getName());
                }
                else {
                    System.out.println(ally.getName() + " est mort, la gagnant est : " + this.getName());
                    ally.setHealth(0);
                }

            }
            else {
                this.takeDamage(allyDamage);
                System.out.println();
                System.out.println(ally.getName() + " attaque " + this.getName() + " et lui fait " + allyDamage + " dégâts");
                ally.profile();
                this.profile();
                System.out.println();
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

    public void profile(){
        System.out.println("------------------");
        System.out.println("nom : " +  this.getName());
        System.out.println("vie : " + Math.max(0, this.getHealth()));
        System.out.println("endurance : " + this.getEndurance());
        System.out.println("------------------");
    }

    /**
     * Checks if the character is still alive.
     *
     * @return true if the character's health is greater than 0
     */
    public boolean stillAlive(){
        return this.getHealth() > 0;
    }
}
