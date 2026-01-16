package it.unibo.abyssclimber.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomGeneratorExtraTest {

    @Test
    void testBossOnlyInCenter() {
        List<RoomOption> options = RoomGenerator.generateOptions(3);

        assertEquals(3, options.size(), "Devono esserci sempre 3 opzioni");

        RoomType left = options.get(0).type();
        RoomType center = options.get(1).type();
        RoomType right = options.get(2).type();

        assertNotEquals(RoomType.BOSS_ELITE, left, "Il boss elite non deve stare a sinistra");
        assertNotEquals(RoomType.FINAL_BOSS, left, "Il final boss non deve stare a sinistra");

        assertNotEquals(RoomType.BOSS_ELITE, right, "Il boss elite non deve stare a destra");
        assertNotEquals(RoomType.FINAL_BOSS, right, "Il final boss non deve stare a destra");

        assertTrue(center == RoomType.BOSS_ELITE || center == RoomType.FINAL_BOSS,
                "Il centro deve essere sempre un boss");
    }
}
