package org.example.envahissementarmorique.model.theater;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the results of a complete simulation round.
 * Contains all combat results, food spawning events, and character state changes.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class RoundResult {

    /**
     * The round number.
     */
    private final int roundNumber;

    /**
     * List of all combat results in this round.
     */
    private final List<CombatResult> combatResults;

    /**
     * List of character state change messages.
     */
    private final List<String> stateChangeMessages;

    /**
     * List of food spawning messages.
     */
    private final List<String> foodSpawningMessages;

    /**
     * List of food degradation messages.
     */
    private final List<String> foodDegradationMessages;

    /**
     * Number of characters that died this round.
     */
    private int casualties;

    /**
     * Number of food items spawned this round.
     */
    private int foodSpawned;

    /**
     * Creates a new round result.
     *
     * @param roundNumber the round number
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

    // Getters

    public int getRoundNumber() {
        return roundNumber;
    }

    public List<CombatResult> getCombatResults() {
        return new ArrayList<>(combatResults);
    }

    public List<String> getStateChangeMessages() {
        return new ArrayList<>(stateChangeMessages);
    }

    public List<String> getFoodSpawningMessages() {
        return new ArrayList<>(foodSpawningMessages);
    }

    public List<String> getFoodDegradationMessages() {
        return new ArrayList<>(foodDegradationMessages);
    }

    public int getCasualties() {
        return casualties;
    }

    public int getFoodSpawned() {
        return foodSpawned;
    }

    public int getTotalBattles() {
        return combatResults.size();
    }

    // Adders

    /**
     * Adds a combat result to this round.
     *
     * @param result the combat result
     */
    public void addCombatResult(CombatResult result) {
        combatResults.add(result);
        if (result.hasCasualty()) {
            casualties += (result.isFighter1Died() ? 1 : 0) + (result.isFighter2Died() ? 1 : 0);
        }
    }

    /**
     * Adds a state change message.
     *
     * @param message the message
     */
    public void addStateChangeMessage(String message) {
        stateChangeMessages.add(message);
    }

    /**
     * Adds a food spawning message.
     *
     * @param message the message
     */
    public void addFoodSpawningMessage(String message) {
        foodSpawningMessages.add(message);
        foodSpawned++;
    }

    /**
     * Adds a food degradation message.
     *
     * @param message the message
     */
    public void addFoodDegradationMessage(String message) {
        foodDegradationMessages.add(message);
    }

    /**
     * Gets a summary of this round.
     *
     * @return summary string
     */
    public String getSummary() {
        return String.format("Round %d: %d battles, %d casualties, %d food spawned",
                roundNumber, getTotalBattles(), casualties, foodSpawned);
    }

    @Override
    public String toString() {
        return getSummary();
    }
}

