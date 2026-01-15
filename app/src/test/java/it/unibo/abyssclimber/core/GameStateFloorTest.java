package it.unibo.abyssclimber.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateFloorTest {

    @Test
    void testNextFloorIncrements() {
        GameState state = GameState.get();

        // reset prima per coerenza
        try {
            state.resetRun();
        } catch (Exception ignored) {}

        int startFloor = state.getFloor();
        state.nextFloor();

        assertEquals(startFloor + 1, state.getFloor(), "nextFloor() deve incrementare il floor di 1");
    }

    @Test
    void testResetRunRestoresFloorToOne() throws Exception {
        GameState state = GameState.get();

        state.nextFloor();
        state.nextFloor();

        assertTrue(state.getFloor() > 1, "Il floor dovrebbe essere > 1 prima del reset");

        state.resetRun();

        assertEquals(1, state.getFloor(), "resetRun() deve riportare il floor a 1");
    }
}
