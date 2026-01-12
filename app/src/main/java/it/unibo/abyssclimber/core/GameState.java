package it.unibo.abyssclimber.core;

import it.unibo.abyssclimber.model.Classe;
import it.unibo.abyssclimber.model.Player;
import it.unibo.abyssclimber.model.Tipo;

/**
 * Singleton class representing the overall game state.
 */
public class GameState {

    private static final GameState INSTANCE = new GameState();

    public static GameState get() {
        return INSTANCE;
    }

    private Player player;
    private int floor = 1;

    private GameState() {
    }

    public Player getPlayer() {
        return player;
    }

    public void initializePlayer(String name, Tipo tipo, Classe classe) {
        this.player = new Player(name, tipo, classe);
    }

    public int getFloor() {
        return floor;
    }

    public void nextFloor() {
        floor++;
    }

    public void resetRun() throws Exception {
        floor = 1;
        player = null; // Resetting run means clearing the player for now
        RoomContext.get().setLastChosen(null);
        GameCatalog.initialize();
    }
}
