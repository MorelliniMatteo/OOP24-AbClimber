package it.unibo.abyssclimber.ui.room;

import it.unibo.abyssclimber.core.RoomContext;
import it.unibo.abyssclimber.core.RoomOption;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ShopRoomController {

    @FXML private Label titleLabel;
    @FXML private Label descLabel;

    @FXML
    private void initialize() {
        RoomOption opt = RoomContext.get().getLastChosen();
        if (opt != null) {
            titleLabel.setText("Negozio");
            descLabel.setText(opt.description());
        }
    }

    @FXML
    private void onContinue() {
        SceneRouter.goTo(SceneId.ROOM_SELECTION);
    }
}
