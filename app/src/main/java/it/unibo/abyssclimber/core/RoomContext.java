package it.unibo.abyssclimber.core;

import java.util.List;

/**
 * Singleton class that stores and manages the context of the current room selection.
 */
public final class RoomContext {

    /**
     * The single instance of the RoomContext (Singleton pattern).
     */
    private static final RoomContext INSTANCE = new RoomContext();

    /**
     * Returns the singleton instance of the RoomContext.
     *
     * @return the unique RoomContext instance
     */
    public static RoomContext get() {
        return INSTANCE;
    }

    private RoomOption lastChosen;

    // A value of -1 means no floor is cached.
    private int cachedFloor = -1;

    // Cached list of room options for the current floor.
    private List<RoomOption> cachedOptions;

    // Disabled door indices for the cached floor.
    private final java.util.Set<Integer> disabledDoors = new java.util.HashSet<>();

    private RoomContext() {}

    /**
     * Returns the last room option chosen by the player.
     *
     * @return the last chosen RoomOption
     */
    public RoomOption getLastChosen() {
        return lastChosen;
    }

    /**
     * Sets the last room option chosen by the player.
     *
     * @param lastChosen the selected RoomOption
     */
    public void setLastChosen(RoomOption lastChosen) {
        this.lastChosen = lastChosen;
    }

    /**
     * Returns the room options generated for the given floor.
     * <p>
     * If the floor is different from the cached one, new options are generated,
     * cached, and then returned. Otherwise, the previously cached options are reused.
     *
     * @param floor the current floor number
     * @return a list of RoomOption available for the given floor
     */
    public List<RoomOption> getOrCreateOptions(int floor) {
        if (cachedOptions == null || cachedFloor != floor) {
            cachedOptions = RoomGenerator.generateOptions(floor);
            cachedFloor = floor;
            disabledDoors.clear();
        }
        return cachedOptions;
    }

    /**
     * Clears the cached room options and resets the cached floor.
     * This should be called when restarting a run or changing floors explicitly.
     */
    public void clearCachedOptions() {
        cachedOptions = null;
        cachedFloor = -1;
        disabledDoors.clear();
    }

    /**
     * Marks a door index as disabled for the current floor.
     *
     * @param floor the current floor number
     * @param index the door index to disable
     */
    public void disableDoor(int floor, int index) {
        if (cachedFloor != floor) {
            cachedFloor = floor;
            disabledDoors.clear();
        }
        disabledDoors.add(index);
    }

    /**
     * Checks if a door index is disabled for the current floor.
     *
     * @param index door index
     * @return true if disabled for the current floor
     */
    public boolean isDoorDisabled(int index) {
        return disabledDoors.contains(index);
    }
}
