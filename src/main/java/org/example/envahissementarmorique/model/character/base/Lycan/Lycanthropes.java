package org.example.envahissementarmorique.model.character.base.Lycan;

import org.example.envahissementarmorique.model.PackAndAlpha.Pack;
import org.example.envahissementarmorique.model.Yell.Yell;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Représente un lycanthrope (loup-garou) dans la simulation.
 *
 * Un lycanthrope possède un sexe, une catégorie d'âge, une force, un facteur de domination
 * ainsi qu'un rang au sein d'une meute. Il peut hurler, vieillir, défier d'autres lycans,
 * tenter de se transformer en humain ou encore se séparer de sa meute.
 *
 * Chaque lycanthrope dispose notamment de :
 * <ul>
 *     <li>Un identifiant unique</li>
 *     <li>Un rang représenté par une lettre grecque (α à ω)</li>
 *     <li>Une éventuelle appartenance à une meute</li>
 *     <li>Un facteur d'impétuosité influençant son comportement</li>
 * </ul>
 *
 * Cette classe gère également les interactions intra-meute telles que les défis
 * de domination, la réaction aux hurlements, la transformation en humain ainsi
 * que le vieillissement progressif du personnage.
 *
 * @author Envahissement Armorique
 * @version 1.0
 */
public class Lycanthropes {

    private static final String[] GREEK_RANKS = {
            "α","β","γ","δ","ε","ζ","η","θ","ι","κ","λ","μ","ν","ξ","ο","π","ρ","σ","τ","υ","φ","χ","ψ","ω"
    };

    private static int idCounter = 1;

    private int id;
    private Sexe sexe;
    private CategorieAge ageCategory;
    private int strength;
    private int dominationFactor;
    private int rankIndex;
    private double impulsivenessFactor;
    private Pack pack;
    private boolean isHuman = false;
    private Random rnd = ThreadLocalRandom.current();

    /**
     * Construit un nouveau lycanthrope.
     *
     * @param sexe           le sexe du lycanthrope
     * @param cat            la catégorie d'âge
     * @param strength       la force initiale
     * @param impulsiveness  le facteur d'impétuosité
     * @param rankIndex      l'indice du rang
     */
    public Lycanthropes(Sexe sexe, CategorieAge cat, int strength, double impulsiveness, int rankIndex) {
        this.id = idCounter++;
        this.sexe = sexe;
        this.ageCategory = cat;
        this.strength = strength;
        this.impulsivenessFactor = Math.max(0.0, Math.min(1.0, impulsiveness));
        this.rankIndex = Math.max(0, Math.min(GREEK_RANKS.length - 1, rankIndex));
        this.dominationFactor = 0;
    }

    /** Retourne le rang grec ou "solitaire" */
    public String getRank() {
        if (pack == null) return "solitaire";
        return GREEK_RANKS[Math.min(rankIndex, GREEK_RANKS.length - 1)];
    }

    /** Identifiant formaté (ex : M#3) */
    public String getIdentifier() {
        return String.format("%s#%d", sexe == Sexe.MALE ? "M" : "F", id);
    }

    /** @return true si oméga */
    public boolean isOmega() { return pack != null && getRank().equals("ω"); }

    /** @return true si alpha */
    public boolean isAlpha() { return pack != null && getRank().equals("α"); }

    /** @return true si solitaire */
    public boolean isLone() { return pack == null && !isHuman; }

    /** @return true si humain */
    public boolean isHuman() { return isHuman; }

    public void setPack(Pack p) { this.pack = p; }
    public Pack getPack() { return pack; }

    public Sexe getSexe() { return sexe; }
    public CategorieAge getAgeCategory() { return ageCategory; }
    public int getStrength() { return strength; }
    public int getRankIndex() { return rankIndex; }
    public int getDominationFactor() { return dominationFactor; }

    public void setRankIndex(int idx) {
        this.rankIndex = Math.max(0, Math.min(GREEK_RANKS.length - 1, idx));
    }

    public void adjustDominationFactor(int delta) {
        this.dominationFactor += delta;
    }

    /**
     * Calcule le niveau global du lycanthrope.
     */
    public double computeLevel() {
        double ageScore = switch(ageCategory) {
            case YOUNG -> 0.8;
            case ADULT -> 1.0;
            case OLD -> 0.6;
        };

        double strengthScore = Math.log(1 + strength);
        double rankScore = pack != null ? 1.0 / (1 + rankIndex) : 0.5;
        double dominationScore = Math.tanh(dominationFactor / 10.0) + 1.0;

        return 2.0 * ageScore + 1.5 * strengthScore + 2.0 * rankScore + 1.0 * dominationScore;
    }

    /** Affiche les infos du lycan */
    public void display() {
        String status = isLone() ? "(solitaire)" : "";
        System.out.printf("%s | sexe=%s age=%s strength=%d rank=%s dom=%d imp=%.2f lvl=%.2f %s%n",
                getIdentifier(), sexe, ageCategory, strength, getRank(),
                dominationFactor, impulsivenessFactor, computeLevel(), status);
    }

    /** Hurle un message */
    public Yell yell(YellType type, String message) {
        Yell y = new Yell(this, type, message);
        y.display();
        return y;
    }

    /** Réagit à un hurlement */
    public void hearYell(Yell y) {
        if (isHuman || this == y.emitter) return;

        if (y.type == YellType.BELONGING && !y.isAlreadyRepeated()) {
            if (pack != null && pack == y.emitter.getPack()) {
                y.markRepeated();
                yell(YellType.BELONGING, "réponse à la meute");
            } else if (pack != null && pack != y.emitter.getPack()) {
                if (rnd.nextDouble() < 0.3) {
                    yell(YellType.BELONGING, "marquage de territoire");
                }
            }
        }
    }

    /** Tente une domination */
    public boolean tryDominate(Lycanthropes target) {
        if (this == target || this.isHuman || target.isHuman) return false;
        if (pack == null || target.getPack() != pack) return false;
        if (target.isAlpha() && target.sexe == Sexe.FEMALE) return false;

        double attackThreshold = this.strength * (1.0 + this.impulsivenessFactor);
        if (target.strength > attackThreshold) {
            target.yell(YellType.AGRESSIVITY, "repoussé l'agression");
            adjustDominationFactor(-1);
            return false;
        }

        double myLevel = computeLevel();
        double targetLevel = target.computeLevel();

        if (myLevel > targetLevel || target.isOmega()) {
            int oldRank = this.rankIndex;
            this.rankIndex = target.rankIndex;
            target.rankIndex = oldRank;

            adjustDominationFactor(2);
            target.adjustDominationFactor(-1);

            yell(YellType.DOMINATION, "domination réussie");
            target.yell(YellType.SOUMBISSION, "soumission");

            if (pack != null && target.isAlpha() && target.sexe == Sexe.MALE) {
                pack.rebuildAlphaCouple(this);
            }
            return true;
        } else {
            target.yell(YellType.AGRESSIVITY, "défense réussie");
            adjustDominationFactor(-2);
            target.adjustDominationFactor(1);
            return false;
        }
    }

    /** Vieillit le lycan */
    public void age() {
        if (ageCategory == CategorieAge.YOUNG) {
            ageCategory = CategorieAge.ADULT;
        } else if (ageCategory == CategorieAge.ADULT) {
            ageCategory = CategorieAge.OLD;
        }
        strength = Math.max(1, strength + rnd.nextInt(-2, 3));
    }

    /** Tente une transformation en humain */
    public boolean tryTransformToHuman() {
        double probability = Math.min(0.5, Math.max(0.01, computeLevel() / 20.0));

        if (rnd.nextDouble() < probability) {
            isHuman = true;
            boolean wasAlpha = isAlpha();
            Sexe alphaSex = this.sexe;

            if (pack != null) {
                Pack oldPack = pack;
                oldPack.removeLycanthrope(this);
                pack = null;

                if (wasAlpha) {
                    oldPack.manageAlphaRemoval(alphaSex);
                }
            }

            System.out.printf("%s s'est transformé en humain et quitte la simulation.%n", getIdentifier());
            return true;
        }
        return false;
    }

    /** Transformation directe (test uniquement) */
    public void forceTransformHumanForTest() {
        isHuman = true;
        if (pack != null) {
            Pack oldPack = pack;
            oldPack.removeLycanthrope(this);
            pack = null;
        }
    }

    /** Quitte volontairement la meute */
    public void leavePack() {
        if (pack != null) {
            pack.removeLycanthrope(this);
            pack = null;
            System.out.printf("%s devient solitaire.%n", getIdentifier());
        }
    }
}
