package org.example.envahissementarmorique.model.PackAndAlpha;


import org.example.envahissementarmorique.model.character.base.Lycan.CategorieAge;
import org.example.envahissementarmorique.model.character.base.Lycan.Lycanthropes;
import org.example.envahissementarmorique.model.character.base.Lycan.Sexe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CoupleAlpha {
    private Lycanthropes male;
    private Lycanthropes femelle;

    public CoupleAlpha(Lycanthropes m, Lycanthropes f) {
        if (m.getSexe() != Sexe.MALE || f.getSexe() != Sexe.FEMELLE) {
            throw new IllegalArgumentException("Sexes invalides pour couple α");
        }
        this.male = m;
        this.femelle = f;
    }

    public Lycanthropes getMale() { return male; }
    public Lycanthropes getFemelle() { return femelle; }

    public void afficher() {
        System.out.printf("Couple α : %s & %s%n",
                male.getIdentifiant(), femelle.getIdentifiant());
    }

    public List<Lycanthropes> reproduire() {
        int taille = ThreadLocalRandom.current().nextInt(1, 8);
        List<Lycanthropes> petits = new ArrayList<>();

        for (int i = 0; i < taille; i++) {
            Sexe sexe = ThreadLocalRandom.current().nextBoolean() ? Sexe.MALE : Sexe.FEMELLE;
            int forceMoyenne = (male.getForce() + femelle.getForce()) / 2;
            int forceJeune = Math.max(1, forceMoyenne + ThreadLocalRandom.current().nextInt(-3, 4));
            double impetuosite = 0.2 + ThreadLocalRandom.current().nextDouble() * 0.3;

            Lycanthropes jeune = new Lycanthropes(sexe, CategorieAge.JEUNE, forceJeune, impetuosite, 2);
            petits.add(jeune);
        }

        System.out.printf("Reproduction: %d jeune(s) né(s)%n", petits.size());
        return petits;
    }
}
