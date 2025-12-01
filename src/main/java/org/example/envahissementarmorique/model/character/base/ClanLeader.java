package org.example.envahissementarmorique.model.character.base;


import org.example.envahissementarmorique.model.item.Potion;
import org.example.envahissementarmorique.model.place.Place;

import java.util.ArrayList;

public class ClanLeader {

    private String name;
    private String genre;
    private int age;
    private Place place; // Le chef est rattaché à un lieu [cite: 81]

    public ClanLeader(String name, String genre, int age, Place place) {
        this.name = name;
        this.genre = genre;
        this.age = age;
        this.place = place;
    }

    public void examinePlace() {
        // Le chef examine son lieu (affiche caractéristiques + listes) [cite: 86]
        if (this.place != null) {
            System.out.println(this.place.toString());
        } else {
            System.out.println(this.name + " n'a pas de lieu attribué.");
        }
    }

    public void createCharacter(String name, String genre, String faction, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicPotion){
        // Création du personnage
        GameCharacter newGameCharacter = new GameCharacter(name, genre, faction, height, age, strength, endurance, health, hunger, belligerence, magicPotion);

        // IMPORTANT : Il faut ajouter ce nouveau personnage au lieu du chef [cite: 87]
        if (this.place != null) {
            this.place.addCharacter(newGameCharacter);
            System.out.println(name + " a rejoint " + this.place.getName());
        }
    }

    public void healTeam(ArrayList<GameCharacter> gameCharacters, int hpHealed){
        // Soigner les personnages [cite: 88]
        for (GameCharacter gameCharacter : gameCharacters) {
            // On vérifie que le personnage est bien dans le lieu du chef (sécurité optionnelle mais logique)
            if (this.place.getCharacters().contains(gameCharacter)) {
                gameCharacter.setHealth(gameCharacter.getHealth() + hpHealed);
                // Idéalement, borner à 100 ou maxHealth ici
            }
        }
    }

    public void feedTeam(ArrayList<GameCharacter> gameCharacters, Foods food){
        for (GameCharacter gameCharacter : gameCharacters) {
            gameCharacter.setHunger(gameCharacter.getHunger() + food.getNutrition());

            // Logique supplémentaire suggérée : vérifier si la nourriture est "pas fraiche"
            // "Manger du poisson pas frais est mauvais pour la santé" [cite: 46]
        }
    }

    public void askToMakePotion(Druid druid){
//        if (this.place.getCharacters().contains(druid)) {
//            druid.createPotion(this.place);
//        } else {
//            System.out.println("Ce Druid n'est pas dans le lieu !");
//        }
    }

    public void drinkMagicPotion(Potion potion, ArrayList<GameCharacter> gameCharacters){
//        for (GameCharacter gameCharacter : gameCharacters) {
//            gameCharacter.drinkPotion(potion);
//        }
    }

    public void transferCharacter(Place destination, ArrayList<GameCharacter> gameCharacters){
        boolean isBattlefield = destination.getName().toLowerCase().contains("bataille");
        boolean isEnclosure = destination.getName().toLowerCase().contains("enclos");

        if (isBattlefield || isEnclosure) {
            for (GameCharacter gameCharacter : gameCharacters) {
                // Retirer du lieu actuel
                this.place.removeCharacter(gameCharacter);
                // Ajouter au nouveau lieu
                destination.addCharacter(gameCharacter);
                System.out.println(gameCharacter.getName() + " transféré vers " + destination.getName());
            }
        } else {
            System.out.println("Transfert impossible : Destination invalide (doit être Champ de bataille ou Enclos).");
        }
    }
}