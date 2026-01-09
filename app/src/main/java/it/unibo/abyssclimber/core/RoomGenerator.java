package it.unibo.abyssclimber.core;

import java.util.ArrayList;
import java.util.Collections;
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
     * - 1 BOSS_ELITE (or FINAL_BOSS on last floor)
     * - 2 random between FIGHT and SHOP
     */
    public static List<RoomOption> generateOptions(int floor) {
        List<RoomOption> options = new ArrayList<>();

        // Boss door
        if (floor >= FINAL_FLOOR) {
            options.add(finalBossOption());
        } else {
            options.add(eliteBossOption(floor));
        }

        // Other doors
        while (options.size() < 3) {
            RoomOption candidate = switch (RNG.nextInt(2)) {
                case 0 -> fightOption(floor);
                default -> shopOption(floor);
            };
            options.add(candidate);
        }

        Collections.shuffle(options, RNG);
        return options;
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
