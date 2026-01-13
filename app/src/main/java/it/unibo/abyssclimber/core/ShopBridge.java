package it.unibo.abyssclimber.core;

import it.unibo.abyssclimber.core.RoomOption;

/**
 * Bridge interface to connect UI room controllers with the shop system.
 */
public interface ShopBridge {

    /**
     * Opens the shop logic and returns the outcome.
     *
     * @param option the selected room option
     * @return outcome of the shop interaction
     */
    ShopResult openShop(RoomOption option);
}
