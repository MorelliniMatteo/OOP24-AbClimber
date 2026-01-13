package it.unibo.abyssclimber.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility class for generating room options.
 */
public final class RoomGenerator {

    private static final Random RNG = new Random();

    // TODO: link this value to the real progression
    private static final int FINAL_FLOOR = 10;

    private RoomGenerator() {}

    /**
     * Generates 3 room options:
     * - Boss in the center
     * - 2 random between FIGHT and SHOP
     */
    public static List<RoomOption> generateOptions(int floor) {
        List<RoomOption> options = new ArrayList<>();

        // Left door (random)
        options.add(randomSideOption(floor));

        // Center door = Boss always (elite or final)
        if (floor >= FINAL_FLOOR) {
            options.add(finalBossOption());
        } else {
            options.add(eliteBossOption(floor));
        }

        // Right door (random)
        options.add(randomSideOption(floor));

        return options;
    }

    private static RoomOption randomSideOption(int floor) {
        return switch (RNG.nextInt(2)) {
            case 0 -> fightOption(floor);
            default -> shopOption(floor);
        };
    }

    private static RoomOption eliteBossOption(int floor) {
        return new RoomOption(
                RoomType.BOSS_ELITE,
                "Boss Elite",
                "Sfida il guardiano del piano " + floor,
                "images/icons/boss_elite.png"
        );
    }

    private static RoomOption finalBossOption() {
        return new RoomOption(
                RoomType.FINAL_BOSS,
                "Boss Finale",
                "La sfida definitiva ti attende",
                "images/icons/final_boss.png"
        );
    }

    private static RoomOption fightOption(int floor) {
        return new RoomOption(
                RoomType.FIGHT,
                "Combattimento",
                "Nemici sempre più forti (Piano " + floor + ")",
                "images/icons/sword.png"
        );
    }

    private static RoomOption shopOption(int floor) {
        return new RoomOption(
                RoomType.SHOP,
                "Negozio",
                "Spendi oro e potenziati",
                "images/icons/shop.png"
        );
    }
}
