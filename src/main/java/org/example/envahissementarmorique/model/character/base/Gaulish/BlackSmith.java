package org.example.envahissementarmorique.model.character.base.Gaulish;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

/**
 * Représente un forgeron gaulois.
 * <p>
 * Les forgerons sont des artisans spécialisés dans la fabrication
 * d’armes, d’outils et d’équipements. Ils jouent un rôle essentiel
 * dans la société gauloise, en fournissant des objets utiles au combat
 * comme à la vie quotidienne.
 * </p>
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class BlackSmith extends GameCharacter {

    /**
     * Crée un nouveau personnage de type Forgeron.
     *
     * @param name          le nom du personnage
     * @param genre         le genre du personnage
     * @param faction       la faction à laquelle il appartient
     * @param height        la taille du personnage en mètres
     * @param age           l'âge du personnage en années
     * @param strength      le niveau de force du personnage
     * @param endurance     le niveau d’endurance du personnage
     * @param health        les points de vie initiaux
     * @param hunger        le niveau de faim initial
     * @param belligerence  le niveau de belligerence du personnage
     * @param magicpotion   la quantité initiale de potion magique
     */
    public BlackSmith(String name, String genre, String faction, double height, int age, int strength, int endurance, int health, int hunger, int belligerence, int magicpotion) {
        super(name, genre, faction, height, age, strength, endurance, health, hunger, belligerence, magicpotion);
    }
}
