package org.example.envahissementarmorique.model.PackAndAlpha;

import org.example.envahissementarmorique.model.character.base.Lycan.Lycanthropes;
import org.example.envahissementarmorique.model.character.base.Lycan.Sexe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Pack {

    private String nom;
    private CoupleAlpha coupleAlpha;
    private List<Lycanthropes> membres = new ArrayList<>();

    public Pack(String nom, Lycanthropes maleAlpha, Lycanthropes femelleAlpha) {
        this.nom = nom;
        this.coupleAlpha = new CoupleAlpha(maleAlpha, femelleAlpha);

        ajouterLycanthrope(maleAlpha);
        ajouterLycanthrope(femelleAlpha);
    }

    public String getNom() {
        return nom;
    }

    public CoupleAlpha getCoupleAlpha() {
        return coupleAlpha;
    }

    public List<Lycanthropes> getMembres() {
        return membres;
    }

    /* ------------------- AJOUT / RETRAIT ------------------- */

    public void ajouterLycanthrope(Lycanthropes l) {
        if (!membres.contains(l)) {
            membres.add(l);
            l.setMeute(this);
        }
    }

    public void retirerLycanthrope(Lycanthropes l) {
        membres.remove(l);
    }

    /* ------------------- GESTION ALPHA ------------------- */

    /**
     * Appelé quand un mâle alpha est dominé.
     * Le nouvel alpha est celui qui a dominé le précédent.
     */
    public void reconstituerCoupleAlpha(Lycanthropes nouveauMaleAlpha) {

        if (coupleAlpha == null) return;

        Lycanthropes femelle = coupleAlpha.getFemelle();

        coupleAlpha = new CoupleAlpha(nouveauMaleAlpha, femelle);

        System.out.printf(
                ">>> Nouveau couple alpha dans le pack %s : %s (M) + %s (F)%n",
                nom,
                nouveauMaleAlpha.getIdentifiant(),
                femelle.getIdentifiant()
        );
    }

    /**
     * Appelé quand un alpha devient humain.
     * Il faut sélectionner un nouveau mâle ou une nouvelle femelle.
     */
    public void gererDestitutionAlpha(Sexe sexeDestitue) {

        System.out.printf("⚠ Alpha %s destitué dans le pack %s%n",
                sexeDestitue == Sexe.MALE ? "mâle" : "femelle",
                nom);

        // On cherche un remplaçant parmi les membres les plus puissants
        Lycanthropes nouveau = membres.stream()
                .filter(l -> l.getSexe() == sexeDestitue)
                .filter(l -> !l.estHumain())
                .sorted(Comparator.comparingDouble(Lycanthropes::calculerNiveau).reversed())
                .findFirst()
                .orElse(null);

        if (nouveau == null) {
            System.out.println("→ Aucun remplaçant possible !");
            return;
        }

        if (sexeDestitue == Sexe.MALE) {
            coupleAlpha = new CoupleAlpha(nouveau, coupleAlpha.getFemelle());
        } else {
            coupleAlpha = new CoupleAlpha(coupleAlpha.getMale(), nouveau);
        }

        System.out.printf(
                ">>> Nouveau %s alpha : %s%n",
                sexeDestitue == Sexe.MALE ? "mâle" : "femelle",
                nouveau.getIdentifiant()
        );
    }

    /* ------------------- AFFICHAGE ------------------- */

    public void afficher() {
        System.out.println("=== Pack " + nom + " ===");
        coupleAlpha.afficher();
        System.out.println("Membres :");
        for (Lycanthropes l : membres) {
            System.out.println(" - " + l.getIdentifiant() + " (rang " + l.getRang() + ")");
        }
    }
}
