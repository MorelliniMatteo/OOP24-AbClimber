package it.unibo.abyssclimber.core;

import it.unibo.abyssclimber.model.Classe;
import it.unibo.abyssclimber.model.Tipo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @Test
    void testResetRunClearsState() throws Exception {
        GameState state = GameState.get();

        state.initializePlayer("Tester", Tipo.FIRE, Classe.MAGO);
        state.nextFloor();

        RoomContext.get().setLastChosen(new RoomOption(RoomType.FIGHT, "Fight", "Test"));
        RoomContext.get().disableDoor(state.getFloor(), 1);

        state.resetRun();

        assertEquals(1, state.getFloor(), "Reset run deve riportare il floor a 1");
        assertNull(state.getPlayer(), "Reset run deve eliminare il player corrente");
        assertNull(RoomContext.get().getLastChosen(), "Reset run deve pulire lastChosen");
        assertFalse(RoomContext.get().isDoorDisabled(1), "Reset run deve pulire porte disabilitate");
    }
}
