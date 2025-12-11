package org.example.envahissementarmorique.model.theater;

/**
 * Représente le résultat d'un combat entre deux personnages.
 * <p>
 * Cette classe stocke les informations détaillées du combat,
 * y compris les participants, les dégâts infligés et l'issue de la bataille.
 * </p>
 *
 * @author Envahissement
 * @version 1.0
 */
public class CombatResult {

    /** Nom du premier combattant. */
    private final String fighter1Name;

    /** Nom du second combattant. */
    private final String fighter2Name;

    /** Faction du premier combattant. */
    private final String fighter1Faction;

    /** Faction du second combattant. */
    private final String fighter2Faction;

    /** Dégâts infligés par le combattant 1 au combattant 2. */
    private final int damageToFighter2;

    /** Dégâts infligés par le combattant 2 au combattant 1. */
    private final int damageToFighter1;

    /** Points de vie du combattant 1 après le combat. */
    private final int fighter1HealthAfter;

    /** Points de vie du combattant 2 après le combat. */
    private final int fighter2HealthAfter;

    /** Indique si le combattant 1 est mort durant le combat. */
    private final boolean fighter1Died;

    /** Indique si le combattant 2 est mort durant le combat. */
    private final boolean fighter2Died;

    /** Lieu où le combat a eu lieu. */
    private final String battleLocation;

    /**
     * Crée un nouvel enregistrement de combat.
     *
     * @param fighter1Name nom du combattant 1
     * @param fighter2Name nom du combattant 2
     * @param fighter1Faction faction du combattant 1
     * @param fighter2Faction faction du combattant 2
     * @param damageToFighter2 dégâts infligés au combattant 2
     * @param damageToFighter1 dégâts infligés au combattant 1
     * @param fighter1HealthAfter points de vie du combattant 1 après le combat
     * @param fighter2HealthAfter points de vie du combattant 2 après le combat
     * @param fighter1Died indique si le combattant 1 est mort
     * @param fighter2Died indique si le combattant 2 est mort
     * @param battleLocation lieu du combat
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

    // ------------------ Getters ------------------

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
     * Récupère le vainqueur du combat.
     *
     * @return le nom du vainqueur, ou null si égalité ou si les deux sont morts
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
     * Vérifie si un combattant est mort durant le combat.
     *
     * @return true si au moins un combattant est mort
     */
    public boolean hasCasualty() {
        return fighter1Died || fighter2Died;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) vs %s (%s) à %s - Vainqueur : %s",
                fighter1Name, fighter1Faction,
                fighter2Name, fighter2Faction,
                battleLocation,
                getWinner() != null ? getWinner() : "Égalité");
    }
}
