package it.unibo.abyssclimber.core;

/**
 * An interface for components that need to refresh their state when they become visible.
 */
public interface Refreshable {
    /**
     * Called when the component becomes visible and needs to refresh its state.
     */
    void onShow();
}
