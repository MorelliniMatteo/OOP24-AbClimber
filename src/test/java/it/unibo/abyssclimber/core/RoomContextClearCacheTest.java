package it.unibo.abyssclimber.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomContextClearCacheTest {

    @Test
    void testClearCacheResetsDoorsAndOptions() {
        RoomContext context = RoomContext.get();

        context.getOrCreateOptions(1);
        context.disableDoor(1, 2);

        assertTrue(context.isDoorDisabled(2), "La porta 2 deve risultare disabilitata prima del reset");

        context.clearCachedOptions();

        // dopo reset, nessuna porta deve risultare disabilitata
        assertFalse(context.isDoorDisabled(2), "Dopo clearCachedOptions non devono esserci porte disabilitate");

        // rigenera nuove opzioni
        var options = context.getOrCreateOptions(1);
        assertNotNull(options, "Dopo reset deve rigenerare correttamente le opzioni");
        assertEquals(3, options.size(), "Le opzioni devono restare 3 dopo reset");
    }
}
