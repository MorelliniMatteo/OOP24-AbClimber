package it.unibo.abyssclimber.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomContextTest {

    @Test
    void testCachingPerFloor() {
        RoomContext context = RoomContext.get();
        context.clearCachedOptions();

        List<RoomOption> first = context.getOrCreateOptions(1);
        List<RoomOption> second = context.getOrCreateOptions(1);

        assertSame(first, second, "Stesso floor -> deve tornare la stessa lista cache");

        List<RoomOption> nextFloor = context.getOrCreateOptions(2);
        assertNotSame(first, nextFloor, "Cambio floor -> nuova lista cache");
    }

    @Test
    void testDisableDoorResetsOnFloorChange() {
        RoomContext context = RoomContext.get();
        context.clearCachedOptions();

        context.getOrCreateOptions(1);
        context.disableDoor(1, 0);

        assertTrue(context.isDoorDisabled(0), "La porta 0 deve risultare disabilitata");

        // Cambio floor: deve pulire i disabledDoors
        context.getOrCreateOptions(2);
        assertFalse(context.isDoorDisabled(0), "Cambio floor -> le porte disabilitate devono essere resettate");
    }
}
