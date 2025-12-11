package org.example.envahissementarmorique.model.character.base.Gaulish;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

/**
 * Représente un personnage de Marchand.
 * Les Marchands commercent des biens et gèrent les activités liées au commerce
 * dans les villages gaulois.
 *
 * Ils jouent un rôle essentiel dans l’économie locale grâce à l’échange de produits,
 * la négociation et la gestion des stocks.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class Merchant extends GameCharacter {

    /**
     * Crée un nouveau personnage Marchand.
     *
     * @param name          le nom du personnage
     * @param genre         le genre du personnage
     * @param faction       la faction du personnage
     * @param height        la taille du personnage en mètres
     * @param age           l’âge du personnage en années
     * @param strength      le niveau de force du personnage
     * @param endurance     le niveau d’endurance du personnage
     * @param health        les points de vie initiaux
     * @param hunger        le niveau de faim initial
     * @param belligerence  le niveau de belligerence du personnage
     * @param magicpotion   le niveau initial de potion magique
     */
    public Merchant(String name, String genre, String faction, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicpotion) {
        super(name, genre, faction, height, age, strength, endurance, health, hunger, belligerence, magicpotion);
    }

}
