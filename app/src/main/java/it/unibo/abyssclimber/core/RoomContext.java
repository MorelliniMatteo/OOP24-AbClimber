package it.unibo.abyssclimber.core;

/**
 * A singleton class that holds the context of the current room selection.
 */
public final class RoomContext {

    private static final RoomContext INSTANCE = new RoomContext();

    public static RoomContext get() {
        return INSTANCE;
    }

    private RoomOption lastChosen;

    private RoomContext() {}

    public RoomOption getLastChosen() {
        return lastChosen;
    }

    public void setLastChosen(RoomOption lastChosen) {
        this.lastChosen = lastChosen;
    }
}
