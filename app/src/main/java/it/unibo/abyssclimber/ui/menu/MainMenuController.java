package it.unibo.abyssclimber.ui.menu;

import it.unibo.abyssclimber.core.AssetManager;
import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

/**
 * Controller for the main menu UI.
 */
public class MainMenuController {

    @FXML
    private ImageView logoView;

    @FXML
    private void initialize() {
        if (logoView != null) {
            logoView.setImage(AssetManager.loadImage("images/logo.png"));
        }
    }

    @FXML
    private void onStartGame() {
        GameState.get().resetRun();
        SceneRouter.goTo(SceneId.CHARACTER_CREATION);
    }

    @FXML
    private void onExit() {
        System.exit(0);
    }
}
