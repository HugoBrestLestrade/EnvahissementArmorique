package org.example.envahissementarmorique.model.theater;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente les résultats d'un tour complet de simulation.
 * <p>
 * Contient tous les résultats de combats, les événements d'apparition de nourriture
 * et les changements d'état des personnages pour ce tour.
 * </p>
 *
 * @author
 * Envahissement Armorique Team
 * @version 1.0
 */
public class RoundResult {

    /** Numéro du tour. */
    private final int roundNumber;

    /** Liste de tous les résultats de combats de ce tour. */
    private final List<CombatResult> combatResults;

    /** Liste des messages de changement d'état des personnages. */
    private final List<String> stateChangeMessages;

    /** Liste des messages d'apparition de nourriture. */
    private final List<String> foodSpawningMessages;

    /** Liste des messages de dégradation de nourriture. */
    private final List<String> foodDegradationMessages;

    /** Nombre de personnages décédés lors de ce tour. */
    private int casualties;

    /** Nombre d'objets alimentaires apparus lors de ce tour. */
    private int foodSpawned;

    /**
     * Crée un nouveau résultat de tour.
     *
     * @param roundNumber numéro du tour
     */
    public RoundResult(int roundNumber) {
        this.roundNumber = roundNumber;
        this.combatResults = new ArrayList<>();
        this.stateChangeMessages = new ArrayList<>();
        this.foodSpawningMessages = new ArrayList<>();
        this.foodDegradationMessages = new ArrayList<>();
        this.casualties = 0;
        this.foodSpawned = 0;
    }

    // ------------------ Getters ------------------

    /** @return le numéro du tour */
    public int getRoundNumber() {
        return roundNumber;
    }

    /** @return la liste des résultats de combats */
    public List<CombatResult> getCombatResults() {
        return new ArrayList<>(combatResults);
    }

    /** @return la liste des messages de changement d'état */
    public List<String> getStateChangeMessages() {
        return new ArrayList<>(stateChangeMessages);
    }

    /** @return la liste des messages d'apparition de nourriture */
    public List<String> getFoodSpawningMessages() {
        return new ArrayList<>(foodSpawningMessages);
    }

    /** @return la liste des messages de dégradation de nourriture */
    public List<String> getFoodDegradationMessages() {
        return new ArrayList<>(foodDegradationMessages);
    }

    /** @return le nombre de personnages décédés ce tour */
    public int getCasualties() {
        return casualties;
    }

    /** @return le nombre d'objets alimentaires apparus ce tour */
    public int getFoodSpawned() {
        return foodSpawned;
    }

    /** @return le nombre total de combats réalisés ce tour */
    public int getTotalBattles() {
        return combatResults.size();
    }

    // ------------------ Ajout d'événements ------------------

    /**
     * Ajoute un résultat de combat à ce tour.
     *
     * @param result le résultat de combat
     */
    public void addCombatResult(CombatResult result) {
        combatResults.add(result);
        if (result.hasCasualty()) {
            casualties += (result.isFighter1Died() ? 1 : 0) + (result.isFighter2Died() ? 1 : 0);
        }
    }

    /**
     * Ajoute un message de changement d'état d'un personnage.
     *
     * @param message le message à ajouter
     */
    public void addStateChangeMessage(String message) {
        stateChangeMessages.add(message);
    }

    /**
     * Ajoute un message d'apparition de nourriture.
     *
     * @param message le message à ajouter
     */
    public void addFoodSpawningMessage(String message) {
        foodSpawningMessages.add(message);
        foodSpawned++;
    }

    /**
     * Ajoute un message de dégradation de nourriture.
     *
     * @param message le message à ajouter
     */
    public void addFoodDegradationMessage(String message) {
        foodDegradationMessages.add(message);
    }

    // ------------------ Résumé ------------------

    /**
     * Retourne un résumé de ce tour.
     *
     * @return résumé sous forme de chaîne de caractères
     */
    public String getSummary() {
        return String.format("Tour %d : %d combats, %d morts, %d nourritures apparues",
                roundNumber, getTotalBattles(), casualties, foodSpawned);
    }

    @Override
    public String toString() {
        return getSummary();
    }
}
