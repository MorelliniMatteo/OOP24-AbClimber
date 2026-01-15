package it.unibo.abyssclimber.core;
public interface ShopBridge {

    /**
     * Opens the shop logic and returns the outcome.
     *
     * @param option the selected room option
     * @return outcome of the shop interaction
     */
    ShopResult openShop(RoomOption option);
}
