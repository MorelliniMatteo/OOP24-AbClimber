package it.unibo.abyssclimber.ui.gameover;

import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * Controller for the Game Over screen.
 * It animates the title label to look like it's hanging and swinging.
 */
public class GameOverController {

    @FXML
    private Label titleLabel;

    @FXML
    private void initialize() {

        // Create a Rotate transform that i can animate.
        Rotate hangingRotate = new Rotate(0, 0, 0);
        titleLabel.getTransforms().add(hangingRotate);

        // FALL ANIMATION (translation)
        // Moves the label down a little bit to simulate "falling".
        TranslateTransition fallTranslate = new TranslateTransition(Duration.millis(700), titleLabel);
        fallTranslate.setFromY(0);
        fallTranslate.setToY(24);
        fallTranslate.setInterpolator(Interpolator.EASE_IN);

        // FALL ANIMATION (rotation)
        // Rotates the label while it "falls" to give a hanging effect.
        Timeline fallRotate = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(hangingRotate.angleProperty(), 0, Interpolator.EASE_IN)),
                new KeyFrame(Duration.millis(700),
                        new KeyValue(hangingRotate.angleProperty(), 24, Interpolator.EASE_IN))
        );

        // Run translation + rotation at the same time for the falling effect.
        ParallelTransition fall = new ParallelTransition(fallTranslate, fallRotate);

        // SWING ANIMATION 
        // After falling, the label keeps swinging back and forth forever.
        Timeline swing = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(hangingRotate.angleProperty(), 24, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(1200),
                        new KeyValue(hangingRotate.angleProperty(), 16, Interpolator.EASE_BOTH))
        );

        // AutoReverse makes it go 24 -> 16 -> 24 -> ...
        // CycleCount INDEFINITE means it loops forever.
        swing.setAutoReverse(true);
        swing.setCycleCount(Animation.INDEFINITE);

        // Play the fall once, then start the infinite swing.
        SequentialTransition sequence = new SequentialTransition(fall, swing);
        sequence.play();
    }

    /**
     * Called when the screen is clicked.
     * Returns the player back to the main menu.
     */
    @FXML
    private void onReturnToMenu() {
        SceneRouter.goTo(SceneId.MAIN_MENU);
    }
}
