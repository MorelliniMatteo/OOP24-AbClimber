package it.unibo.abyssclimber.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility class for generating room options.
 */
public final class RoomGenerator {

    private static final Random RNG = new Random();

    private RoomGenerator() {}

    /**
     * Generates 3 room options:
     * - Boss in the center
     * - 2 side doors: either both FIGHT or one FIGHT + one SHOP
     */
    public static List<RoomOption> generateOptions(int floor) {
        List<RoomOption> options = new ArrayList<>();

        // Center door = Boss always (elite or final)
        if (floor >= GameState.getFinalFloor()) {
            options.add(finalBossOption());
        } else {
            options.add(eliteBossOption(floor));
        }

        boolean hasShop = RNG.nextBoolean();
        boolean shopOnLeft = RNG.nextBoolean();

        RoomOption leftOption = fightOption(floor);
        RoomOption rightOption = fightOption(floor);

        if (hasShop) {
            if (shopOnLeft) {
                leftOption = shopOption(floor);
            } else {
                rightOption = shopOption(floor);
            }
        }

        // Left door
        options.add(0, leftOption);

        // Right door
        options.add(rightOption);

        return options;
    }

    private static RoomOption eliteBossOption(int floor) {
        return new RoomOption(
                RoomType.BOSS_ELITE,
                "Boss Elite",
                "Challenge the floor guardian " + floor
        );
    }

    private static RoomOption finalBossOption() {
        return new RoomOption(
                RoomType.FINAL_BOSS,
                "Final Boss",
                "The final challenge awaits!"
        );
    }

    private static RoomOption fightOption(int floor) {
        return new RoomOption(
                RoomType.FIGHT,
                "Combat Room",
                "Enemies get stronger (Floor " + floor + ")"
        );
    }

    private static RoomOption shopOption(int floor) {
        return new RoomOption(
                RoomType.SHOP,
                "Shop",
                "Spend gold and power up!"
        );
    }
}
