package org.example.envahissementarmorique.model.character.base;

import org.example.envahissementarmorique.model.character.base.Gaulish.Druid;
import org.example.envahissementarmorique.model.item.Potion;
import org.example.envahissementarmorique.model.place.Place;
import org.example.envahissementarmorique.model.item.Food;

import java.util.ArrayList;

/**
 * Represents a clan leader in the game.
 * <p>
 * A ClanLeader can manage characters, examine their place, feed and heal the team,
 * transfer characters between places, and request druids to make potions.
 * </p>
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class ClanLeader {

    private String name;
    private String genre;
    private int age;
    private Place place;

    /**
     * Creates a new ClanLeader.
     *
     * @param name the leader's name
     * @param genre the leader's gender
     * @param age the leader's age
     * @param place the Place assigned to the leader
     */
    public ClanLeader(String name, String genre, int age, Place place) {
        this.name = name;
        this.genre = genre;
        this.age = age;
        this.place = place;
    }

    // ------------------ Getters and Setters ------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    // ------------------ Actions ------------------

    /**
     * Examines the current place and prints its characteristics and characters.
     */
    public void examinePlace() {
        if (this.place != null) {
            System.out.println(this.place.toString());
        } else {
            System.out.println(this.name + " n'a pas de lieu attribué.");
        }
    }

    /**
     * Creates a new GameCharacter and adds it to the leader's place.
     *
     * @param name the character's name
     * @param genre the character's gender
     * @param faction the character's faction
     * @param height the character's height in meters
     * @param age the character's age
     * @param strength the character's strength
     * @param endurance the character's endurance
     * @param health the character's initial health points
     * @param hunger the character's initial hunger level
     * @param belligerence the character's belligerence level
     * @param magicPotion the character's initial magic potion level
     */
    public void createCharacter(String name, String genre, String faction, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicPotion){
        GameCharacter newGameCharacter = new GameCharacter(name, genre, faction, height, age, strength, endurance, health, hunger, belligerence, magicPotion);

        if (this.place != null) {
            this.place.addCharacter(newGameCharacter);
            System.out.println(name + " a rejoint " + this.place.getName());
        }
    }

    /**
     * Heals a list of GameCharacters by a given HP amount.
     *
     * @param gameCharacters the characters to heal
     * @param hpHealed the amount of health points to restore
     */
    public void healTeam(ArrayList<GameCharacter> gameCharacters, int hpHealed){
        for (GameCharacter gameCharacter : gameCharacters) {
            if (this.place.getCharacters().contains(gameCharacter)) {
                gameCharacter.ToHeal(hpHealed);
            }
        }
    }

    /**
     * Feeds a list of GameCharacters with a given Food item.
     *
     * @param gameCharacters the characters to feed
     * @param food the Food item
     */
    public void feedTeam(ArrayList<GameCharacter> gameCharacters, Food food){
        for (GameCharacter gameCharacter : gameCharacters) {
            gameCharacter.setHunger(gameCharacter.getHunger() + food.getNutritionalValue());
        }
    }

    /**
     * Requests a Druid to create a potion at the leader's place.
     *
     * @param druid the Druid to make the potion
     */
    public void askToMakePotion(Druid druid){
        // Implementation can be added later
    }

    /**
     * Makes the team drink a magic potion.
     *
     * @param potion the Potion to drink
     * @param gameCharacters the characters who drink it
     */
    public void drinkMagicPotion(Potion potion, ArrayList<GameCharacter> gameCharacters){
        // Implementation can be added later
    }

    /**
     * Transfers a list of GameCharacters to another Place.
     * Only works if the destination is a battlefield or an enclosure.
     *
     * @param destination the destination Place
     * @param gameCharacters the characters to transfer
     */
    public void transferCharacter(Place destination, ArrayList<GameCharacter> gameCharacters){
        boolean isBattlefield = destination.getName().toLowerCase().contains("bataille");
        boolean isEnclosure = destination.getName().toLowerCase().contains("enclos");

        if (isBattlefield || isEnclosure) {
            for (GameCharacter gameCharacter : gameCharacters) {
                this.place.removeCharacter(gameCharacter);
                destination.addCharacter(gameCharacter);
                System.out.println(gameCharacter.getName() + " transféré vers " + destination.getName());
            }
        } else {
            System.out.println("Transfert impossible : Destination invalide (doit être Champ de bataille ou Enclos).");
        }
    }
}
