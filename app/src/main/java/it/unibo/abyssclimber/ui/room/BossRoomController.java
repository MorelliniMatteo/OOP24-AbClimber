package it.unibo.abyssclimber.ui.room;

import it.unibo.abyssclimber.core.AssetManager;
import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.RoomContext;
import it.unibo.abyssclimber.core.RoomOption;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class BossRoomController {

    @FXML private Label titleLabel;
    @FXML private Label descLabel;
    @FXML private ImageView backgroundView;

    @FXML
    private void initialize() {
        var bg = AssetManager.tryLoadImage("images/backgrounds/elite_fight.jpeg");
        if (bg != null) {
            backgroundView.setImage(bg);
        }
        backgroundView.setMouseTransparent(true);

        RoomOption opt = RoomContext.get().getLastChosen();
        if (opt != null) {
            titleLabel.setText("Boss Elite");
            descLabel.setText(opt.description());
        }
    }

    @FXML
    private void onContinue() {
        GameState.get().nextFloor();
        SceneRouter.goTo(SceneId.ROOM_SELECTION);
    }
}
