package it.unibo.abyssclimber.ui.room;

import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.RoomContext;
import it.unibo.abyssclimber.core.RoomOption;
import it.unibo.abyssclimber.core.RoomType;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller for the room placeholder screen.
 */
public class RoomPlaceholderController {

    @FXML private Label titleLabel;
    @FXML private Label descLabel;

    @FXML
    private void initialize() {
        RoomOption opt = RoomContext.get().getLastChosen();
        if (opt == null) {
            titleLabel.setText("Stanza");
            descLabel.setText("Nessuna scelta trovata.");
            return;
        }

        titleLabel.setText("Stanza: " + opt.title() + " (" + opt.type() + ")");
        descLabel.setText(opt.description());
    }

    @FXML
    private void onContinue() {
        RoomOption opt = RoomContext.get().getLastChosen();
        if (opt == null) {
            SceneRouter.goTo(SceneId.ROOM_SELECTION);
            return;
        }

        // Placeholder logica di progressione:
        // - BOSS_ELITE: avanzamento piano
        // - FINAL_BOSS: fine run (per ora)
        if (opt.type() == RoomType.BOSS_ELITE) {
            GameState.get().nextFloor();
            SceneRouter.goTo(SceneId.ROOM_SELECTION);
            return;
        }

        if (opt.type() == RoomType.FINAL_BOSS) {
            // TODO: quando esiste il combattimento vero, qua si andrà alla scena del boss finale
            SceneRouter.goTo(SceneId.GAME_OVER);
            return;
        }

        // FIGHT / SHOP: per ora ritorna a scelta stanze (placeholder)
        SceneRouter.goTo(SceneId.ROOM_SELECTION);
    }
 
    // Debug only
    @FXML
    private void onDieDebug() {
        GameState.get().getPlayer().damage(9999);
        SceneRouter.goTo(SceneId.GAME_OVER);
    }
}
