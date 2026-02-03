package it.unibo.abyssclimber.core.services;

import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.SceneId;

/**
 * Service for handling run lifecycle actions.
 */
public class GameRunService {

    /**
     * Resets the run and returns the next scene.
     *
     * @return the scene to open after resetting the run
     * @throws Exception if game catalog initialization fails
     */
    public SceneId startNewRun() throws Exception {
        GameState.get().resetRun();
        return SceneId.CHARACTER_CREATION;
    }
}
