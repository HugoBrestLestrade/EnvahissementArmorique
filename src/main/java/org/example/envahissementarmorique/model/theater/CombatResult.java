package org.example.envahissementarmorique.model.theater;

/**
 * Represents the result of a combat encounter between two characters.
 * Stores detailed information about the battle including participants,
 * damage dealt, and outcome.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class CombatResult {

    /**
     * Name of the first fighter.
     */
    private final String fighter1Name;

    /**
     * Name of the second fighter.
     */
    private final String fighter2Name;

    /**
     * Faction of the first fighter.
     */
    private final String fighter1Faction;

    /**
     * Faction of the second fighter.
     */
    private final String fighter2Faction;

    /**
     * Damage dealt by fighter 1 to fighter 2.
     */
    private final int damageToFighter2;

    /**
     * Damage dealt by fighter 2 to fighter 1.
     */
    private final int damageToFighter1;

    /**
     * Health of fighter 1 after the battle.
     */
    private final int fighter1HealthAfter;

    /**
     * Health of fighter 2 after the battle.
     */
    private final int fighter2HealthAfter;

    /**
     * Whether fighter 1 died in this battle.
     */
    private final boolean fighter1Died;

    /**
     * Whether fighter 2 died in this battle.
     */
    private final boolean fighter2Died;

    /**
     * Location where the battle took place.
     */
    private final String battleLocation;

    /**
     * Creates a new combat result.
     *
     * @param fighter1Name name of fighter 1
     * @param fighter2Name name of fighter 2
     * @param fighter1Faction faction of fighter 1
     * @param fighter2Faction faction of fighter 2
     * @param damageToFighter2 damage dealt to fighter 2
     * @param damageToFighter1 damage dealt to fighter 1
     * @param fighter1HealthAfter health of fighter 1 after battle
     * @param fighter2HealthAfter health of fighter 2 after battle
     * @param fighter1Died whether fighter 1 died
     * @param fighter2Died whether fighter 2 died
     * @param battleLocation location of the battle
     */
    public CombatResult(String fighter1Name, String fighter2Name,
                       String fighter1Faction, String fighter2Faction,
                       int damageToFighter2, int damageToFighter1,
                       int fighter1HealthAfter, int fighter2HealthAfter,
                       boolean fighter1Died, boolean fighter2Died,
                       String battleLocation) {
        this.fighter1Name = fighter1Name;
        this.fighter2Name = fighter2Name;
        this.fighter1Faction = fighter1Faction;
        this.fighter2Faction = fighter2Faction;
        this.damageToFighter2 = damageToFighter2;
        this.damageToFighter1 = damageToFighter1;
        this.fighter1HealthAfter = fighter1HealthAfter;
        this.fighter2HealthAfter = fighter2HealthAfter;
        this.fighter1Died = fighter1Died;
        this.fighter2Died = fighter2Died;
        this.battleLocation = battleLocation;
    }

    // Getters

    public String getFighter1Name() {
        return fighter1Name;
    }

    public String getFighter2Name() {
        return fighter2Name;
    }

    public String getFighter1Faction() {
        return fighter1Faction;
    }

    public String getFighter2Faction() {
        return fighter2Faction;
    }

    public int getDamageToFighter2() {
        return damageToFighter2;
    }

    public int getDamageToFighter1() {
        return damageToFighter1;
    }

    public int getFighter1HealthAfter() {
        return fighter1HealthAfter;
    }

    public int getFighter2HealthAfter() {
        return fighter2HealthAfter;
    }

    public boolean isFighter1Died() {
        return fighter1Died;
    }

    public boolean isFighter2Died() {
        return fighter2Died;
    }

    public String getBattleLocation() {
        return battleLocation;
    }

    /**
     * Gets the winner of the battle, or null if both died or both survived.
     *
     * @return name of the winner, or null
     */
    public String getWinner() {
        if (fighter2Died && !fighter1Died) {
            return fighter1Name;
        } else if (fighter1Died && !fighter2Died) {
            return fighter2Name;
        }
        return null;
    }

    /**
     * Checks if anyone died in this battle.
     *
     * @return true if at least one fighter died
     */
    public boolean hasCasualty() {
        return fighter1Died || fighter2Died;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) vs %s (%s) at %s - Winner: %s",
                fighter1Name, fighter1Faction,
                fighter2Name, fighter2Faction,
                battleLocation,
                getWinner() != null ? getWinner() : "Draw");
    }
}

