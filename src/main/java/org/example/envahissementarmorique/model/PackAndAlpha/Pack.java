package org.example.envahissementarmorique.model.PackAndAlpha;

import org.example.envahissementarmorique.model.character.base.Lycan.Lycanthropes;
import org.example.envahissementarmorique.model.character.base.Lycan.Sexe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Représente une meute de lycanthropes avec un couple alpha et des membres.
 *
 * Une meute dispose :
 * <ul>
 *     <li>D'un nom</li>
 *     <li>D'un couple alpha (male et female)</li>
 *     <li>De membres lycanthropes</li>
 * </ul>
 *
 * Cette classe permet :
 * <ul>
 *     <li>Ajouter ou retirer un membre</li>
 *     <li>Gérer le couple alpha lors d'une domination ou transformation en humain</li>
 *     <li>Afficher les informations de la meute</li>
 * </ul>
 */
public class Pack {

    /** Nom de la meute */
    private String name;

    /** Couple alpha (male + female) */
    private CoupleAlpha alphaCouple;

    /** Liste des membres de la meute */
    private List<Lycanthropes> members = new ArrayList<>();

    /**
     * Crée une nouvelle meute avec un couple alpha initial.
     *
     * @param name          nom de la meute
     * @param maleAlpha     lycanthrope mâle alpha
     * @param femaleAlpha   lycanthrope femelle alpha
     */
    public Pack(String name, Lycanthropes maleAlpha, Lycanthropes femaleAlpha) {
        this.name = name;
        this.alphaCouple = new CoupleAlpha(maleAlpha, femaleAlpha);

        addLycanthrope(maleAlpha);
        addLycanthrope(femaleAlpha);
    }

    /** @return le nom de la meute */
    public String getName() { return name; }

    /** @return le couple alpha */
    public CoupleAlpha getAlphaCouple() { return alphaCouple; }

    /** @return la liste des membres */
    public List<Lycanthropes> getMembers() { return members; }

    /* ------------------- AJOUT / RETRAIT ------------------- */

    /**
     * Ajoute un lycanthrope à la meute.
     *
     * @param l lycanthrope à ajouter
     */
    public void addLycanthrope(Lycanthropes l) {
        if (!members.contains(l)) {
            members.add(l);
            l.setPack(this);
        }
    }

    /**
     * Retire un lycanthrope de la meute.
     *
     * @param l lycanthrope à retirer
     */
    public void removeLycanthrope(Lycanthropes l) {
        members.remove(l);
    }

    /* ------------------- GESTION ALPHA ------------------- */

    /**
     * Reconstitue le couple alpha lorsqu'un mâle alpha est dominé.
     *
     * @param newMaleAlpha le nouveau mâle alpha
     */
    public void rebuildAlphaCouple(Lycanthropes newMaleAlpha) {
        if (alphaCouple == null) return;

        Lycanthropes female = alphaCouple.getFemale();
        alphaCouple = new CoupleAlpha(newMaleAlpha, female);

        System.out.printf(
                ">>> Nouveau couple alpha dans le pack %s : %s (M) + %s (F)%n",
                name, newMaleAlpha.getIdentifier(), female.getIdentifier()
        );
    }

    /**
     * Gère la destitution d'un alpha devenu humain.
     * Sélectionne un nouveau mâle ou une nouvelle femelle alpha.
     *
     * @param removedSexe le sexe de l'alpha destitué
     */
    public void manageAlphaRemoval(Sexe removedSexe) {
        System.out.printf("⚠ Alpha %s destitué dans le pack %s%n",
                removedSexe == Sexe.MALE ? "male" : "female", name);

        Lycanthropes newAlpha = members.stream()
                .filter(l -> l.getSexe() == removedSexe)
                .filter(l -> !l.isHuman())
                .sorted(Comparator.comparingDouble(Lycanthropes::computeLevel).reversed())
                .findFirst()
                .orElse(null);

        if (newAlpha == null) {
            System.out.println("→ Aucun remplaçant possible !");
            return;
        }

        if (removedSexe == Sexe.MALE) {
            alphaCouple = new CoupleAlpha(newAlpha, alphaCouple.getFemale());
        } else {
            alphaCouple = new CoupleAlpha(alphaCouple.getMale(), newAlpha);
        }

        System.out.printf(
                ">>> Nouveau %s alpha : %s%n",
                removedSexe == Sexe.MALE ? "male" : "female", newAlpha.getIdentifier()
        );
    }

    /* ------------------- AFFICHAGE ------------------- */

    /** Affiche les informations du pack et de ses membres */
    public void display() {
        System.out.println("=== Pack " + name + " ===");
        alphaCouple.display();
        System.out.println("Members :");
        for (Lycanthropes l : members) {
            System.out.println(" - " + l.getIdentifier() + " (rank " + l.getRank() + ")");
        }
    }
}
