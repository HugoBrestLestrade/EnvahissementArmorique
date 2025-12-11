package org.example.envahissementarmorique.model.character.base.Lycan;

/**
 * Represents the age category of a lycanthrope.
 * <p>
 * This enum is used to determine the lycanthrope's physical development,
 * strength, and social behavior within a pack.
 * </p>
 *
 * <ul>
 *     <li>{@link #YOUNG} : young lycanthrope, still growing and less experienced.</li>
 *     <li>{@link #ADULT} : adult lycanthrope, fully developed and typically dominant.</li>
 *     <li>{@link #OLD} : old lycanthrope, whose strength and impetuosity may decrease.</li>
 * </ul>
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public enum CategorieAge {
    /** Young lycanthrope, still growing and gaining experience. */
    YOUNG,

    /** Adult lycanthrope, fully developed and potentially dominant. */
    ADULT,

    /** Old lycanthrope, may have reduced strength and influence. */
    OLD
}
