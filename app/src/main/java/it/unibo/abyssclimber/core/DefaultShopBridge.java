package it.unibo.abyssclimber.core;

import it.unibo.abyssclimber.core.RoomOption;

/**
 * Temporary shop bridge implementation.
 * Replace with real shop system.
 */
public class DefaultShopBridge implements ShopBridge {

    @Override
    public ShopResult openShop(RoomOption option) {
        System.out.println("DefaultShopBridge: shop placeholder for " + option.title());
        return ShopResult.DONE;
    }
}
