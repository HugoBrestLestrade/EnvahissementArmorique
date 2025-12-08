package org.example.envahissementarmorique.model.character.base.Lycan;

import org.example.envahissementarmorique.model.PackAndAlpha.Pack;
import org.example.envahissementarmorique.model.Yell.Yell;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Lycanthropes  {

    private static final String[] RANGS = {"α","β","γ","δ","ε","ζ","η","θ","ι","κ","λ","μ","ν","ξ","ο","π","ρ","σ","τ","υ","φ","χ","ψ","ω"};
    private static int idCounter = 1;

    private  int id;
    private Sexe sexe;
    private CategorieAge categorie;
    private int force;
    private int facteurDomination;
    private int rangIndex;
    private double facteurImpetuosite;
    private Pack pack;
    private boolean estHumain = false;
    private Random rnd = ThreadLocalRandom.current();

    public Lycanthropes(Sexe sexe, CategorieAge cat, int force, double impetuosite, int rangIndex) {
        this.id = idCounter++;
        this.sexe = sexe;
        this.categorie = cat;
        this.force = force;
        this.facteurImpetuosite = Math.max(0.0, Math.min(1.0, impetuosite));
        this.rangIndex = Math.max(0, Math.min(RANGS.length - 1, rangIndex));
        this.facteurDomination = 0;
    }
    public String getRang() {
        if (pack == null) return "solitaire";
        return RANGS[Math.min(rangIndex, RANGS.length - 1)];
    }

    public String getIdentifiant() {
        return String.format("%s#%d", sexe == Sexe.MALE ? "M" : "F", id);
    }

    public boolean estOmega() { return pack != null && getRang().equals("ω"); }
    public boolean estAlpha() { return pack != null && getRang().equals("α"); }
    public boolean estSolitaire() { return pack == null && !estHumain; }
    public boolean estHumain() { return estHumain; }

    public void setMeute(Pack p) { this.pack = p; }
    public Pack getMeute() { return pack; }

    public Sexe getSexe() { return sexe; }
    public CategorieAge getCategorie() { return categorie; }
    public int getForce() { return force; }
    public int getRangIndex() { return rangIndex; }
    public int getFacteurDomination() { return facteurDomination; }

    public void setRangIndex(int idx) {
        this.rangIndex = Math.max(0, Math.min(RANGS.length - 1, idx));
    }
    public void ajusterFacteurDomination(int delta) {
        this.facteurDomination += delta;
    }

    /* -------- Calcul du niveau -------- */
    public double calculerNiveau() {
        double ageScore = switch(categorie) {
            case JEUNE -> 0.8;
            case ADULTE -> 1.0;
            case VIEUX -> 0.6;
        };
        double forceScore = Math.log(1 + force);
        double rangScore = pack != null ? 1.0 / (1 + rangIndex) : 0.5;
        double domScore = Math.tanh(facteurDomination / 10.0) + 1.0;
        return 2.0 * ageScore + 1.5 * forceScore + 2.0 * rangScore + 1.0 * domScore;
    }

    /* -------- Affichage -------- */
    public void afficher() {
        String statut = estSolitaire() ? "(solitaire)" : "";
        System.out.printf("%s | sexe=%s age=%s force=%d rang=%s fd=%d imp=%.2f niveau=%.2f %s%n",
                getIdentifiant(), sexe, categorie, force, getRang(),
                facteurDomination, facteurImpetuosite, calculerNiveau(), statut);
    }
    /* -------- Hurlements -------- */
    public Yell hurler(YellType type, String message) {
            Yell h = new Yell(this, type, message);
        h.afficher();
        return h;
    }

    public void entendreHurlement(Yell h) {
        if (estHumain || this == h.emetteur) return;

        if (h.type == YellType.APPARTENANCE && !h.estDejaRepete()) {
            if (pack != null && pack == h.emetteur.getMeute()) {
                h.marquerRepete();
                hurler(YellType.APPARTENANCE, "réponse à la meute");
            } else if (pack != null && pack != h.emetteur.getMeute()) {
                if (rnd.nextDouble() < 0.3) {
                    hurler(YellType.APPARTENANCE, "marquage de territoire");
                }
            }
        }
    }
    /* -------- Domination -------- */
    public boolean tenterDominer(Lycanthropes cible) {
        if (this == cible || this.estHumain || cible.estHumain) return false;
        if (pack == null || cible.getMeute() != pack) return false;
        if (cible.estAlpha() && cible.sexe == Sexe.FEMELLE) return false;

        double seuilForce = this.force * (1.0 + this.facteurImpetuosite);
        if (cible.force > seuilForce) {
            cible.hurler(YellType.AGRESSIVITE, "repoussé l'agression");
            this.ajusterFacteurDomination(-1);
            return false;
        }

        double niveauThis = this.calculerNiveau();
        double niveauCible = cible.calculerNiveau();

        if (niveauThis > niveauCible || cible.estOmega()) {
            int oldRang = this.rangIndex;
            this.rangIndex = cible.rangIndex;
            cible.rangIndex = oldRang;
            this.ajusterFacteurDomination(2);
            cible.ajusterFacteurDomination(-1);
            hurler(YellType.DOMINATION, "domination réussie");
            cible.hurler(YellType.SOUMISSION, "soumission");

            // Vérifier si le mâle α a été dominé
            if (pack != null && cible.estAlpha() && cible.sexe == Sexe.MALE) {
                pack.reconstituerCoupleAlpha(this);
            }
            return true;
        } else {
            cible.hurler(YellType.AGRESSIVITE, "défense réussie");
            this.ajusterFacteurDomination(-2);
            cible.ajusterFacteurDomination(1);
            return false;
        }
    }

    /* -------- Vieillissement -------- */
    public void vieillir() {
        if (categorie == CategorieAge.JEUNE) {
            categorie = CategorieAge.ADULTE;
        } else if (categorie == CategorieAge.ADULTE) {
            categorie = CategorieAge.VIEUX;
        }
        force = Math.max(1, force + rnd.nextInt(-2, 3));
    }

    /* -------- Transformation -------- */
    public boolean tenterSeTransformerEnHumain() {
        double probabilite = Math.min(0.5, Math.max(0.01, calculerNiveau() / 20.0));
        if (rnd.nextDouble() < probabilite) {
            estHumain = true;
            boolean etaitAlpha = estAlpha();
            Sexe sexeAlpha = this.sexe;

            if (pack != null) {
                pack.retirerLycanthrope(this);
                if (etaitAlpha) {
                    pack.gererDestitutionAlpha(sexeAlpha);
                }
            }

            System.out.printf("%s s'est transformé en humain et quitte la simulation.%n",
                    getIdentifiant());
            return true;
        }
        return false;
    }
    /* -------- Séparation -------- */
    public void seSeparer() {
        if (pack != null) {
            pack.retirerLycanthrope(this);
            pack = null;
            System.out.printf("%s devient solitaire.%n", getIdentifiant());
        }
    }

}
