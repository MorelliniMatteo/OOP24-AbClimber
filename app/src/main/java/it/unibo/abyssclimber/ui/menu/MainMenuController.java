package it.unibo.abyssclimber.ui.menu;

import it.unibo.abyssclimber.core.AssetManager;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import it.unibo.abyssclimber.core.services.GameRunService;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

/**
 * Controller for the main menu UI.
 */
public class MainMenuController {

    @FXML
    private ImageView logoView;
    private final GameRunService runService = new GameRunService();

    @FXML
    private void initialize() {
        if (logoView != null) {
            logoView.setImage(AssetManager.loadImage("images/logo.png"));
        }
    }

    @FXML
    private void onStartGame() throws Exception {
        SceneId nextScene = runService.startNewRun();
        SceneRouter.goTo(nextScene);
    }

    @FXML
    private void onExit() {
        System.exit(0);
    }
}
