package org.example.envahissementarmorique.model.character.interfaces;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

/**
 * Interface définissant les actions de combat d'un personnage.
 */
public interface TypeCombat {

    /**
     * Permet de battre un allié (membre du même clan).
     *
     * @param ally le personnage allié à battre
     */
    void battre(GameCharacter ally);

    /**
     * Permet de combattre un ennemi à un endroit donné.
     *
     * @param enemy    le personnage ennemi à combattre
     * @param location le lieu du combat
     */
    void combattre(GameCharacter enemy, String location);
}
