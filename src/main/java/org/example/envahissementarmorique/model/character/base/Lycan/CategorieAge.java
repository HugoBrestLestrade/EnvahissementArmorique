package org.example.envahissementarmorique.model.character.base.Lycan;

/**
 * Représente la catégorie d'âge d'un lycanthrope.
 * <p>
 * Cet enum est utilisé pour déterminer le développement physique,
 * la force et le comportement social d'un lycan au sein d'une meute.
 * </p>
 *
 * <ul>
 *     <li>{@link #YOUNG} : lycanthrope jeune, en croissance et encore inexpérimenté.</li>
 *     <li>{@link #ADULT} : lycanthrope adulte, pleinement développé et généralement dominant.</li>
 *     <li>{@link #OLD} : lycanthrope âgé, dont la force et l'impétuosité peuvent diminuer.</li>
 * </ul>
 *
 * @author Envahissement Armorique
 * @version 1.0
 */
public enum CategorieAge {
    /** Lycanthrope jeune, en croissance et en apprentissage. */
    YOUNG,

    /** Lycanthrope adulte, pleinement développé et potentiellement dominant. */
    ADULT,

    /** Lycanthrope âgé, pouvant avoir une force et une influence réduites. */
    OLD
}
