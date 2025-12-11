package org.example.envahissementarmorique.model.character.interfaces;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

import java.util.List;

/**
 * Interface for characters capable of commanding and leading others.
 * Commanders can give orders and manage groups of characters.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public interface Commander {
    /**
     * Makes the commander lead a group of characters.
     *
     * @param characters the list of characters to lead
     */
    void lead(List<GameCharacter> characters);

    /**
     * Makes the commander give an order to a subordinate.
     *
     * @param subordinate the character receiving the order
     * @param order the order to give
     */
    void giveOrder(GameCharacter subordinate, String order);

    /**
     * Makes the commander organize a military formation.
     *
     * @param troops the troops to organize
     * @return true if the organization was successful
     */
    boolean organizeFormation(List<GameCharacter> troops);
}
