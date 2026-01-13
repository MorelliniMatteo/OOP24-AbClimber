package it.unibo.abyssclimber.core;

/**
 * Simple service locator for game systems (combat, shop, etc.).
 * Can be replaced by dependency injection later.
 */
public final class GameServices {

    private static CombatBridge combatBridge = new DefaultCombatBridge();
    private static ShopBridge shopBridge = new DefaultShopBridge();

    private GameServices() { }

    public static CombatBridge getCombatBridge() {
        return combatBridge;
    }

    public static void setCombatBridge(CombatBridge bridge) {
        if (bridge != null) {
            combatBridge = bridge;
        }
    }

    public static ShopBridge getShopBridge() {
        return shopBridge;
    }

    public static void setShopBridge(ShopBridge bridge) {
        if (bridge != null) {
            shopBridge = bridge;
        }
    }
}
