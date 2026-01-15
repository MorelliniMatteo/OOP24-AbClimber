package it.unibo.abyssclimber.core;

/**
 * Temporary shop bridge implementation.
 * Replace with real shop system.
 */
public class DefaultShopBridge implements ShopBridge {

    @Override
    public ShopResult openShop(RoomOption option) {
        return ShopResult.DONE;
    }
}
