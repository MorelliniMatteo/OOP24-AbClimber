package it.unibo.abyssclimber.ui.room;

import it.unibo.abyssclimber.core.AssetManager;
import it.unibo.abyssclimber.core.CombatResult;
import it.unibo.abyssclimber.core.GameServices;
import it.unibo.abyssclimber.core.RoomContext;
import it.unibo.abyssclimber.core.RoomOption;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class FinalBossRoomController {

    @FXML private Label titleLabel;
    @FXML private Label descLabel;
    @FXML private ImageView backgroundView;

    @FXML
    private void initialize() {
        var bg = AssetManager.tryLoadImage("images/backgrounds/boss_fight.jpeg");
        if (bg != null) {
            backgroundView.setImage(bg);
        }
        backgroundView.setMouseTransparent(true);

        RoomOption opt = RoomContext.get().getLastChosen();
        if (opt != null) {
            titleLabel.setText("Boss Finale");
            descLabel.setText(opt.description());
        }
    }

    @FXML
    private void onContinue() {
        RoomOption opt = RoomContext.get().getLastChosen();
        if (opt == null) {
            SceneRouter.goTo(SceneId.ROOM_SELECTION);
            return;
        }

        CombatResult result = GameServices.getCombatBridge().startFight(opt);

        if (result == CombatResult.LOSE) {
            SceneRouter.goTo(SceneId.GAME_OVER);
            return;
        }

        // Vittoria contro boss finale → fine run (per ora)
        SceneRouter.goTo(SceneId.GAME_OVER);
    }
}
