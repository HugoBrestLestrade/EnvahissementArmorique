package org.example.envahissementarmorique.model.Yell;

import org.example.envahissementarmorique.model.character.base.Lycan.Lycanthropes;
import org.example.envahissementarmorique.model.character.base.Lycan.YellType;

/**
 * Représente un hurlement (Yell) émis par un lycanthrope.
 *
 * Un hurlement possède :
 * <ul>
 *     <li>Un émetteur (Lycanthropes)</li>
 *     <li>Un type de hurlement (YellType)</li>
 *     <li>Un message associé</li>
 *     <li>Un indicateur pour savoir si le hurlement a déjà été répété</li>
 * </ul>
 */
public class Yell {

    /** Lycanthrope qui émet le hurlement */
    public Lycanthropes emitter;

    /** Type du hurlement */
    public YellType type;

    /** Message du hurlement */
    public String message;

    /** Indique si le hurlement a déjà été répété */
    private boolean alreadyRepeated = false;

    /**
     * Constructeur d'un hurlement.
     *
     * @param e émetteur du hurlement
     * @param t type du hurlement
     * @param m message du hurlement
     */
    public Yell(Lycanthropes e, YellType t, String m) {
        this.emitter = e;
        this.type = t;
        this.message = m;
    }

    /** Affiche le hurlement avec les informations de l'émetteur */
    public void display() {
        System.out.printf("[%s] %s hurle: %s (%s)%n",
                emitter.getIdentifier(), emitter.getRank(), type, message);
    }

    /** @return true si le hurlement a déjà été répété */
    public boolean isAlreadyRepeated() { return alreadyRepeated; }

    /** Marque le hurlement comme ayant été répété */
    public void markRepeated() { alreadyRepeated = true; }
}
