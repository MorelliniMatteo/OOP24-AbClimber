package it.unibo.abyssclimber.core;

import it.unibo.abyssclimber.model.Classe;
import it.unibo.abyssclimber.model.Player;
import it.unibo.abyssclimber.model.Tipo;

/**
 * Singleton class representing the overall game state.
 * Manages the current player, floor progression, and run lifecycle.
 */
public class GameState {

    // The single instance of the GameState (Singleton pattern).
    private static final GameState INSTANCE = new GameState();

    /**
     * Returns the singleton instance of the GameState.
     *
     * @return the unique GameState instance
     */
    public static GameState get() {
        return INSTANCE;
    }

    // The current player of the game.
    private Player player;

    // The current floor of the run.
    private int floor = 1;

    // Final floor for the current run (progression cap).
    private static final int FINAL_FLOOR = 10;

    // Private constructor to prevent external instantiation.
    private GameState() {
    }

    /**
     * Returns the current player.
     *
     * @return the current Player instance, or null if not initialized
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Initializes the player with the given parameters.
     *
     * @param name   the player's name
     * @param tipo   the chosen element type
     * @param classe the chosen class
     */
    public void initializePlayer(String name, Tipo tipo, Classe classe) {
        this.player = new Player(name, tipo, classe);
    }

    /**
     * Returns the current floor number.
     *
     * @return the current floor
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Returns the final floor for the run.
     *
     * @return the final floor number
     */
    public static int getFinalFloor() {
        return FINAL_FLOOR;
    }

    /**
     * Advances the game to the next floor.
     * Room options will be regenerated automatically when needed.
     */
    public void nextFloor() {
        floor++;
        // When the floor changes, room options will be regenerated automatically
    }

    /**
     * Resets the current run.
     * <p>
     * This clears the player, resets the floor counter,
     * resets room selection state, and reinitializes the game catalog.
     *
     * @throws Exception if game catalog initialization fails
     */
    public void resetRun() throws Exception {
        floor = 1;
        player = null; // Resetting the run clears the current player
        RoomContext.get().setLastChosen(null);
        RoomContext.get().clearCachedOptions(); // Reset cached room options
        GameCatalog.initialize();
    }

    /**
     * Restart but the moves, classes and element remain the same.
     */
    public void resetRunKeepBuild() throws Exception {
        floor = 1;
        RoomContext.get().setLastChosen(null);
        RoomContext.get().clearCachedOptions();
        GameCatalog.initialize();

        if (player != null) {
            player.resetRunKeepBuild();
        }
    }
}
