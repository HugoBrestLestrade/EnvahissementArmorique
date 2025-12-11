package org.example.envahissementarmorique.model.item;

/**
 * Représente l'état de fraîcheur d'un aliment.
 * <p>
 * Les aliments peuvent se trouver dans l'un des états suivants :
 * <ul>
 *   <li>{@link #FRESH} : frais, récemment préparé ou cueilli</li>
 *   <li>{@link #OKAY} : assez frais, encore consommable mais moins optimal</li>
 *   <li>{@link #ROTTEN} : pourri, ne devrait pas être consommé</li>
 * </ul>
 * </p>
 * <p>
 * Chaque état possède un libellé pour affichage dans l'interface ou l'inventaire.
 * </p>
 * <p>
 * Le processus de dégradation suit la logique :
 * <code>FRESH -> OKAY -> ROTTEN -> ROTTEN</code>
 * </p>
 *
 * @author Boudhib
 * @version 1.0
 */
public enum Freshness {
    /** Aliment frais, optimal pour la consommation. */
    FRESH("Frais"),

    /** Aliment assez frais, encore consommable mais moins optimal. */
    OKAY("Assez frais"),

    /** Aliment pourri, dangereux ou inefficace à consommer. */
    ROTTEN("Pourri");

    private final String label;

    /**
     * Constructeur pour définir le libellé de l'état de fraîcheur.
     *
     * @param label Le nom affiché de l'état de fraîcheur.
     */
    Freshness(String label) {
        this.label = label;
    }

    /**
     * Retourne le libellé de l'état de fraîcheur.
     *
     * @return Le nom de l'état sous forme de chaîne de caractères.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Fait évoluer l'état de fraîcheur vers le suivant.
     * <p>
     * La dégradation suit l'ordre : FRESH -> OKAY -> ROTTEN -> ROTTEN
     * </p>
     *
     * @return Le nouvel état de fraîcheur après dégradation.
     */
    public Freshness degrade() {
        return switch(this) {
            case FRESH -> OKAY;
            case OKAY, ROTTEN -> ROTTEN;
        };
    }
}
