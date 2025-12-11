package org.example.envahissementarmorique.model.PackAndAlpha;

import org.example.envahissementarmorique.model.character.base.Lycan.CategorieAge;
import org.example.envahissementarmorique.model.character.base.Lycan.Lycanthropes;
import org.example.envahissementarmorique.model.character.base.Lycan.Sexe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Représente un couple alpha dans une meute.
 *
 * Un couple alpha est composé d'un mâle et d'une femelle. Il peut se reproduire
 * et générer des jeunes lycanthropes.
 *
 * Les attributs et méthodes sont en anglais.
 *
 * @author Envahissement
 * @version 1.0
 */
public class CoupleAlpha {

    /** Référence au lycanthrope mâle alpha */
    private Lycanthropes male;

    /** Référence à la lycanthrope femelle alpha */
    private Lycanthropes female;

    /**
     * Construit un couple alpha à partir d'un mâle et d'une femelle.
     *
     * @param male lycanthrope mâle
     * @param female lycanthrope femelle
     * @throws IllegalArgumentException si les sexes ne correspondent pas
     */
    public CoupleAlpha(Lycanthropes male, Lycanthropes female) {
        if (male.getSexe() != Sexe.MALE || female.getSexe() != Sexe.FEMALE) {
            throw new IllegalArgumentException("Sexes invalides pour un couple alpha");
        }
        this.male = male;
        this.female = female;
    }

    /** @return le mâle alpha du couple */
    public Lycanthropes getMale() { return male; }

    /** @return la femelle alpha du couple */
    public Lycanthropes getFemale() { return female; }

    /** Affiche le couple alpha */
    public void display() {
        System.out.printf("Couple alpha : %s & %s%n", male.getIdentifier(), female.getIdentifier());
    }

    /**
     * Fait se reproduire le couple alpha et génère une liste de jeunes lycanthropes.
     *
     * @return liste des jeunes nés du couple
     */
    public List<Lycanthropes> reproduce() {
        int size = ThreadLocalRandom.current().nextInt(1, 8);
        List<Lycanthropes> youngs = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Sexe sex = ThreadLocalRandom.current().nextBoolean() ? Sexe.MALE : Sexe.FEMALE;
            int avgStrength = (male.getStrength() + female.getStrength()) / 2;
            int youngStrength = Math.max(1, avgStrength + ThreadLocalRandom.current().nextInt(-3, 4));
            double impetuosity = 0.2 + ThreadLocalRandom.current().nextDouble() * 0.3;

            Lycanthropes young = new Lycanthropes(sex, CategorieAge.YOUNG, youngStrength, impetuosity, 2);
            youngs.add(young);
        }

        System.out.printf("Reproduction : %d jeune(s) né(s)%n", youngs.size());
        return youngs;
    }
}
