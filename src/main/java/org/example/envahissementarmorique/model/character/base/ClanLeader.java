package org.example.envahissementarmorique.model.character.base;

import org.example.envahissementarmorique.model.character.base.Gaulish.Druid;
import org.example.envahissementarmorique.model.item.Potion;
import org.example.envahissementarmorique.model.place.Place;
import org.example.envahissementarmorique.model.item.Food;

import java.util.ArrayList;

/**
 * Représente un chef de clan dans le jeu.
 * <p>
 * Un ClanLeader peut gérer des personnages, examiner leur lieu,
 * nourrir et soigner l'équipe, transférer des personnages entre lieux
 * et demander aux druides de fabriquer des potions.
 * </p>
 *
 * @author Envahissement Armorique
 * @version 1.0
 */
public class ClanLeader {

    private String name;
    private String genre;
    private int age;
    private Place place;

    /**
     * Crée un nouveau chef de clan.
     *
     * @param name le nom du chef
     * @param genre le genre du chef
     * @param age l'âge du chef
     * @param place le lieu attribué au chef
     */
    public ClanLeader(String name, String genre, int age, Place place) {
        this.name = name;
        this.genre = genre;
        this.age = age;
        this.place = place;
    }

    // ------------------ Getters et Setters ------------------

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public Place getPlace() { return place; }
    public void setPlace(Place place) { this.place = place; }

    // ------------------ Actions ------------------

    /**
     * Examine le lieu actuel et affiche ses caractéristiques et ses personnages.
     */
    public void examinePlace() {
        if (this.place != null) {
            System.out.println(this.place.toString());
        } else {
            System.out.println(this.name + " n'a pas de lieu attribué.");
        }
    }

    /**
     * Crée un nouveau GameCharacter et l'ajoute au lieu du chef.
     *
     * @param name le nom du personnage
     * @param genre le genre du personnage
     * @param faction la faction du personnage
     * @param height la taille en mètres
     * @param age l'âge du personnage
     * @param strength la force du personnage
     * @param endurance l'endurance du personnage
     * @param health les points de vie initiaux
     * @param hunger le niveau de faim initial
     * @param belligerence le niveau de belliqueux
     * @param magicPotion le niveau de potion magique initial
     */
    public void createCharacter(String name, String genre, String faction, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicPotion){
        GameCharacter newGameCharacter = new GameCharacter(name, genre, faction, height, age, strength, endurance, health, hunger, belligerence, magicPotion);

        if (this.place != null) {
            this.place.addCharacter(newGameCharacter);
            System.out.println(name + " a rejoint " + this.place.getName());
        }
    }

    /**
     * Soigne une liste de GameCharacters d'un certain nombre de points de vie.
     *
     * @param gameCharacters la liste des personnages à soigner
     * @param hpHealed le nombre de points de vie à restaurer
     */
    public void healTeam(ArrayList<GameCharacter> gameCharacters, int hpHealed){
        for (GameCharacter gameCharacter : gameCharacters) {
            if (this.place.getCharacters().contains(gameCharacter)) {
                gameCharacter.ToHeal(hpHealed);
            }
        }
    }

    /**
     * Nourrit une liste de GameCharacters avec un aliment donné.
     *
     * @param gameCharacters la liste des personnages à nourrir
     * @param food l'objet Food utilisé pour nourrir
     */
    public void feedTeam(ArrayList<GameCharacter> gameCharacters, Food food){
        for (GameCharacter gameCharacter : gameCharacters) {
            gameCharacter.setHunger(gameCharacter.getHunger() + food.getNutritionalValue());
        }
    }

    /**
     * Demande à un Druide de fabriquer une potion sur le lieu du chef.
     *
     * @param druid le Druide qui fabriquera la potion
     */
    public void askToMakePotion(Druid druid){
        // Implémentation possible ultérieurement
    }

    /**
     * Fait boire une potion magique à l'équipe.
     *
     * @param potion la potion magique
     * @param gameCharacters les personnages qui boivent la potion
     */
    public void drinkMagicPotion(Potion potion, ArrayList<GameCharacter> gameCharacters){
        // Implémentation possible ultérieurement
    }

    /**
     * Transfère une liste de GameCharacters vers un autre lieu.
     * Le transfert n'est possible que si la destination est un champ de bataille ou un enclos.
     *
     * @param destination le lieu de destination
     * @param gameCharacters les personnages à transférer
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
