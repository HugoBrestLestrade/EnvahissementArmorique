package org.example.envahissementarmorique.model.character.base;

import org.example.envahissementarmorique.model.character.interfaces.Fighter;
import org.example.envahissementarmorique.model.item.Food;
import org.example.envahissementarmorique.model.place.Place;

/**
 * Représente un personnage de jeu avec divers attributs et capacités.
 * <p>
 * Cette classe est la classe de base pour tous les types de personnages de la simulation.
 * </p>
 * <p>
 * Un personnage possède des points de vie, un niveau de faim, de la force, de l'endurance,
 * et peut combattre, manger et boire des potions.
 * Les personnages peuvent appartenir à différentes factions (Gaulois, Romain, etc.).
 * </p>
 *
 * @author Envahissement Armorique
 * @version 1.0
 */
public class GameCharacter implements Fighter {

    /** Le nom du personnage. */
    private String name;

    /** Le genre du personnage. */
    private String genre;

    /** La faction à laquelle le personnage appartient (Gaulois, Romain, etc.). */
    private String faction;

    /** La taille du personnage en mètres. */
    private double height;

    /** L'âge du personnage en années. */
    private int age;

    /** Le niveau de force du personnage. */
    private int strength;

    /** Le niveau d'endurance du personnage. */
    private int endurance;

    /** Les points de vie actuels du personnage. */
    private int health;

    /** Le niveau de faim actuel du personnage. */
    private int hunger;

    /** Le niveau de belliqueux (affecte le combat). */
    private int belligerence;

    /** Le niveau de potion magique du personnage. */
    private int magicpotion;

    /** Les points de vie maximum que le personnage peut avoir. */
    protected int maxHealth;

    /**
     * Crée un nouveau personnage de jeu avec les attributs spécifiés.
     *
     * @param name Le nom du personnage
     * @param genre Le genre du personnage
     * @param faction La faction à laquelle le personnage appartient
     * @param height La taille du personnage en mètres
     * @param age L'âge du personnage en années
     * @param strength Le niveau de force
     * @param endurance Le niveau d'endurance
     * @param health Les points de vie initiaux
     * @param hunger Le niveau de faim initial
     * @param belligerence Le niveau de belliqueux
     * @param magicpotion Le niveau initial de potion magique
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

    // ---------------- Getters et Setters ---------------- //

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getFaction() { return faction; }
    public void setFaction(String faction) { this.faction = faction; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int getStrength() { return strength; }
    public void setStrength(int strength) { this.strength = strength; }

    public int getEndurance() { return endurance; }
    public void setEndurance(int endurance) { this.endurance = endurance; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public int getHunger() { return hunger; }
    public void setHunger(int hunger) { this.hunger = hunger; }

    public int getBelligerence() { return belligerence; }
    public void setBelligerence(int belligerence) { this.belligerence = belligerence; }

    public int getMagicpotion() { return magicpotion; }
    public void setMagicpotion(int magicpotion) { this.magicpotion = magicpotion; }

    // ---------------- Méthodes ---------------- //

    /**
     * Soigne le personnage en ajoutant des points de vie.
     *
     * @param amount Le nombre de points de vie à restaurer
     */
    public void ToHeal(int amount) {
        if (amount <= 0) return;

        this.health += amount;
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }
    }

    /**
     * Permet au personnage de manger de la nourriture.
     * Utilise la méthode Food.consume() pour appliquer les effets correctement.
     *
     * @param food La nourriture à consommer
     */
    public void ToEat(Food food) {
        if (this.health <= 0) {
            System.out.println(name + " est mort et ne peut pas manger.");
            return;
        }
        food.consume(this);
    }

    /**
     * Permet au personnage de boire une potion.
     * Utilise la méthode Potion.drink() pour appliquer les effets.
     *
     * @param potion La potion à boire
     */
    public void ToDrinkPotion(org.example.envahissementarmorique.model.item.Potion potion) {
        if (this.health <= 0) {
            System.out.println(name + " est mort et ne peut pas boire de potion.");
            return;
        }
        potion.drink(this);
    }

    /** Vérifie si le personnage est mort. */
    public boolean isDead() { return this.health <= 0; }

    /** Vérifie si le personnage est belliqueux. */
    public boolean isBelligerent() { return this.belligerence > 0; }

    /** Permet de combattre un autre personnage. */
    public void fight(GameCharacter opponent) { /* logique de combat */ }

    /** Renvoie le lieu d'origine du personnage (à implémenter selon la logique). */
    public Place getOriginPlace() { return null; }

    @Override
    public void takeDamage(int damage) {
        this.setHealth(this.getHealth() - damage);
    }

    /**
     * Combat entre alliés de la même faction (combat par tour simplifié).
     *
     * @param ally L'autre personnage de la même faction
     */
    public void battre(GameCharacter ally) { /* logique combat tour par tour */ }

    /** Affiche les informations principales du personnage. */
    public void profile() { /* affichage profil */ }

    /** Vérifie si le personnage est encore en vie. */
    public boolean stillAlive() { return this.getHealth() > 0; }
}
