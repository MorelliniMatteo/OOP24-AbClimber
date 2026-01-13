package it.unibo.abyssclimber.core;

import it.unibo.abyssclimber.core.RoomOption;

/**
 * Bridge interface to connect UI room controllers with the combat system.
 */
public interface CombatBridge {

    /**
     * Starts a combat encounter and returns the outcome.
     *
     * @param option the selected room option
     * @return outcome of the fight
     */
    CombatResult startFight(RoomOption option);
}
