package it.unibo.abyssclimber.ui.gameover;

import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.fxml.FXML;

/**
 * Controller for the Game Over screen.
 */
public class GameOverController {
    /**
     * Handles the action when the "Restart" button is clicked.
     * Resets the game state and navigates to the room selection screen.
     */
    @FXML
    private void onRestart() {
        GameState.get().resetRun();
        SceneRouter.goTo(SceneId.ROOM_SELECTION);
    }

    /**
     * Handles the action when the "Back to Menu" button is clicked.
     * Navigates back to the main menu screen.
     */
    @FXML
    private void onBackToMenu() {
        SceneRouter.goTo(SceneId.MAIN_MENU);
    }
}
