package it.unibo.abyssclimber.model;

public enum Tipo {
    HYDRO("Hydro"),
    FIRE("Fire"),
    NATURE("Nature"),
    LIGHTNING("Lightning"),
    VOID("Void");

    private final String displayName;

    Tipo(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
