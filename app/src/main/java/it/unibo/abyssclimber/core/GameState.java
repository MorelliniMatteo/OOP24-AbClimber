package it.unibo.abyssclimber.core;

/**
 * Singleton class representing the overall game state.
 */
public class GameState {

    private static final GameState INSTANCE = new GameState();

    public static GameState get() {
        return INSTANCE;
    }

    private final PlayerState player = new PlayerState();
    private int floor = 1;

    private GameState() {}

    public PlayerState getPlayer() {
        return player;
    }

    public int getFloor() {
        return floor;
    }

    public void nextFloor() {
        floor++;
    }

    public void resetRun() {
        floor = 1;
        player.resetForNewRun();
        RoomContext.get().setLastChosen(null);
    }
}
