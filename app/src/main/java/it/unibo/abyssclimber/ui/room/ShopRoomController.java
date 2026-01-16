package it.unibo.abyssclimber.ui.room;

import it.unibo.abyssclimber.core.AssetManager;
import it.unibo.abyssclimber.core.Refreshable;
import it.unibo.abyssclimber.core.RoomContext;
import it.unibo.abyssclimber.core.RoomOption;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ShopRoomController implements Refreshable {

    @FXML private Label titleLabel;
    @FXML private Label descLabel;
    @FXML private ImageView backgroundView;

    @FXML
    private void initialize() {
        onShow();
    }

    @Override
    public void onShow() {
        var bg = AssetManager.tryLoadImage("images/backgrounds/store_room.jpeg");
        if (bg != null) {
            backgroundView.setImage(bg);
        }
        backgroundView.setMouseTransparent(true);

        RoomOption opt = RoomContext.get().getLastChosen();
        if (opt != null) {
            titleLabel.setText("Shop");
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

        // Enter directly form the shop room 
        SceneRouter.goTo(SceneId.SHOP);
    }
    
    @FXML
    private void onEnterShop() {
        SceneRouter.goTo(SceneId.SHOP); 
    }

    @FXML
    private void onBackToMap() {
        SceneRouter.goTo(SceneId.ROOM_SELECTION);
    }
}
