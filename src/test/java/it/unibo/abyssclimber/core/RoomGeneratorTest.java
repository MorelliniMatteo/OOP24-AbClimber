package it.unibo.abyssclimber.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomGeneratorTest {

    @Test
    void testBossInCenterBeforeFinalFloor() {
        int floor = 1;
        List<RoomOption> options = RoomGenerator.generateOptions(floor);

        assertEquals(3, options.size(), "Devono essere generate 3 opzioni");
        assertEquals(RoomType.BOSS_ELITE, options.get(1).type(), "Il boss deve essere in posizione centrale");

        // Le porte laterali devono essere FIGHT o SHOP
        assertTrue(options.get(0).type() == RoomType.FIGHT || options.get(0).type() == RoomType.SHOP);
        assertTrue(options.get(2).type() == RoomType.FIGHT || options.get(2).type() == RoomType.SHOP);

        // Al massimo uno shop sulle due laterali
        long shopCount = options.stream().filter(o -> o.type() == RoomType.SHOP).count();
        assertTrue(shopCount <= 1, "Tra le due porte laterali può esserci al massimo uno shop");
    }

    @Test
    void testFinalBossInCenterOnFinalFloor() {
        int finalFloor = GameState.getFinalFloor();
        List<RoomOption> options = RoomGenerator.generateOptions(finalFloor);

        assertEquals(3, options.size(), "Devono essere generate 3 opzioni");
        assertEquals(RoomType.FINAL_BOSS, options.get(1).type(), "Il final boss deve essere in posizione centrale");
    }
}
