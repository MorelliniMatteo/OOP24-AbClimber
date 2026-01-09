package it.unibo.abyssclimber.ui.room;

import it.unibo.abyssclimber.core.AssetManager;
import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.Refreshable;
import it.unibo.abyssclimber.core.RoomContext;
import it.unibo.abyssclimber.core.RoomGenerator;
import it.unibo.abyssclimber.core.RoomOption;
import it.unibo.abyssclimber.core.RoomType;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * Controller for the room selection screen.
 */
public class RoomSelectionController implements Refreshable {

    @FXML private Label floorLabel;
    @FXML private Label hpLabel;

    @FXML private ImageView backgroundView;

    @FXML private Button doorBtn1;
    @FXML private Button doorBtn2;
    @FXML private Button doorBtn3;

    private List<RoomOption> currentOptions;

    @Override
    public void onShow() {
        refreshHud();

        var bg = AssetManager.tryLoadImage("images/backgrounds/scelta_porta.png");
        if (bg != null) {
            backgroundView.setImage(bg);
        }
        backgroundView.setMouseTransparent(true);

        currentOptions = RoomGenerator.generateOptions(GameState.get().getFloor());

        applyOptionToButton(doorBtn1, currentOptions.get(0));
        applyOptionToButton(doorBtn2, currentOptions.get(1));
        applyOptionToButton(doorBtn3, currentOptions.get(2));
    }

    private void applyOptionToButton(Button btn, RoomOption opt) {
        btn.setText(opt.title());

        // icona
        ImageView icon = new ImageView();
        var img = AssetManager.tryLoadImage(opt.iconPath());
        if (img != null) {
            icon.setImage(img);
            icon.setFitWidth(46);
            icon.setFitHeight(46);
            icon.setPreserveRatio(true);
            btn.setGraphic(icon);
        } else {
            btn.setGraphic(null);
        }

        // reset classi speciali
        btn.getStyleClass().remove("boss-door");
        btn.getStyleClass().remove("final-boss-door");

        // evidenzio porte boss
        if (opt.type() == RoomType.BOSS_ELITE) {
            btn.getStyleClass().add("boss-door");
        } else if (opt.type() == RoomType.FINAL_BOSS) {
            btn.getStyleClass().add("final-boss-door");
        }
    }

    private void refreshHud() {
        int floor = GameState.get().getFloor();
        int hp = GameState.get().getPlayer().getHp();
        int maxHp = GameState.get().getPlayer().getMaxHp();

        floorLabel.setText("Piano: " + floor);
        hpLabel.setText("HP: " + hp + " / " + maxHp);
    }

    @FXML private void onChooseDoor1() { onChoose(0); }
    @FXML private void onChooseDoor2() { onChoose(1); }
    @FXML private void onChooseDoor3() { onChoose(2); }

    private void onChoose(int index) {
        if (currentOptions == null || currentOptions.size() < 3) {
            System.err.println("WARN: opzioni non inizializzate");
            return;
        }

        RoomOption opt = currentOptions.get(index);
        System.out.println("Scelta: " + opt.type() + " (" + opt.title() + ")");

        RoomContext.get().setLastChosen(opt);
        SceneRouter.goTo(SceneId.ROOM_PLACEHOLDER);
    }

    @FXML
    private void onForceDeath() {
        GameState.get().getPlayer().damage(9999);
        SceneRouter.goTo(SceneId.GAME_OVER);
    }
}
