package it.unibo.abyssclimber.ui.room;

import it.unibo.abyssclimber.core.RoomContext;
import it.unibo.abyssclimber.core.RoomOption;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller for a basic fight room placeholder screen.
 */
public class FightRoomController {

    @FXML private Label titleLabel;
    @FXML private Label descLabel;

    /**
     * Initializes the screen using the last chosen room option.
     */
    @FXML
    private void initialize() {
        RoomOption opt = RoomContext.get().getLastChosen();
        if (opt != null) {
            titleLabel.setText("Combattimento");
            descLabel.setText(opt.description());
        }
    }

    /**
     * Returns to the room selection screen.
     */
    @FXML
    private void onContinue() {
        SceneRouter.goTo(SceneId.ROOM_SELECTION);
    }
}
