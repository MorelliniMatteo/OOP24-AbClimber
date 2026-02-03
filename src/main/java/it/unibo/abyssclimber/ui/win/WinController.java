package it.unibo.abyssclimber.ui.win;

import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Controller for the Win screen.
 */
public class WinController {

    @FXML
    private Label titleLabel;

    @FXML
    private void initialize() {

        // Creates a vertical bounce animation for the title label.
        TranslateTransition bounce = new TranslateTransition(Duration.millis(900), titleLabel);
        bounce.setFromY(0);
        bounce.setToY(-14);

        // Makes the animation go up and down continuously.
        bounce.setAutoReverse(true);
        bounce.setCycleCount(Animation.INDEFINITE);

        // Smooth interpolation for a soft bouncing effect.
        bounce.setInterpolator(Interpolator.EASE_BOTH);

        // Starts the animation when the screen is loaded.
        bounce.play();
    }

    /**
     * Handles the click action on the screen.
     * Returns to the main menu.
     */
    @FXML
    private void onReturnToMenu() {
        SceneRouter.goTo(SceneId.MAIN_MENU);
    }
}
