package org.example.envahissementarmorique.model.character.interfaces;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import java.util.List;

/**
 * Interface pour les personnages capables de commander et de diriger d'autres personnages.
 * <p>
 * Un commandant peut donner des ordres et gérer des groupes de personnages.
 * </p>
 *
 * @author Envahissement Armorique
 * @version 1.0
 */
public interface Commander {

    /**
     * Permet au commandant de diriger un groupe de personnages.
     *
     * @param characters la liste des personnages à diriger
     */
    void lead(List<GameCharacter> characters);

    /**
     * Permet au commandant de donner un ordre à un subordonné.
     *
     * @param subordinate le personnage recevant l'ordre
     * @param order l'ordre à donner
     */
    void giveOrder(GameCharacter subordinate, String order);

    /**
     * Permet au commandant d'organiser une formation militaire.
     *
     * @param troops les troupes à organiser
     * @return true si l'organisation a été réussie
     */
    boolean organizeFormation(List<GameCharacter> troops);
}
