package it.unibo.abyssclimber.core.services;

import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.model.Classe;
import it.unibo.abyssclimber.model.Difficulty;
import it.unibo.abyssclimber.model.Tipo;

/**
 * Service for handling character creation logic.
 */
public class CharacterCreationService {

    /**
     * Applies difficulty and initializes the player.
     *
     * @param name       player name
     * @param tipo       player element type
     * @param classe     player class
     * @param multiplier difficulty multiplier
     * @return the next scene to open
     */
    public SceneId confirmCharacter(String name, Tipo tipo, Classe classe, double multiplier) {
        Difficulty.setDifficultyMultiplier(multiplier);
        GameState.get().initializePlayer(name, tipo, classe);
        return SceneId.MOVE_SELECTION;
    }
}
