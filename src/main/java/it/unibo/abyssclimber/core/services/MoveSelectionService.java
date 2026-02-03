package it.unibo.abyssclimber.core.services;

import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.combat.CombatMove;

import java.util.Collection;
import java.util.List;

/**
 * Service for validating and saving selected moves.
 */
public class MoveSelectionService {

    public static final int MAX_SELECTED = 6;

    /**
     * Checks if the player selected a move with cost 1.
     *
     * @param moves selected moves
     * @return true if a cost 1 move exists
     */
    public boolean hasRequiredCostOne(Collection<CombatMove> moves) {
        return moves.stream().anyMatch(move -> move.getCost() == 1);
    }

    /**
     * Checks if the selection has the expected size.
     *
     * @param moves selected moves
     * @return true if size equals MAX_SELECTED
     */
    public boolean isSelectionComplete(Collection<CombatMove> moves) {
        return moves.size() == MAX_SELECTED;
    }

    /**
     * Checks if the selection satisfies all rules.
     *
     * @param moves selected moves
     * @return true if valid
     */
    public boolean isSelectionValid(Collection<CombatMove> moves) {
        return isSelectionComplete(moves) && hasRequiredCostOne(moves);
    }

    /**
     * Saves the selected moves on the current player.
     *
     * @param moves selected moves
     */
    public void saveSelectedMoves(Collection<CombatMove> moves) {
        GameState.get().getPlayer().setSelectedMoves(List.copyOf(moves));
    }

    /**
     * Validates and starts the run with the given moves.
     *
     * @param moves selected moves
     * @return the next scene to open
     */
    public SceneId startRun(Collection<CombatMove> moves) {
        if (!isSelectionValid(moves)) {
            throw new IllegalStateException("Invalid move selection.");
        }
        saveSelectedMoves(moves);
        return SceneId.ROOM_SELECTION;
    }
}
