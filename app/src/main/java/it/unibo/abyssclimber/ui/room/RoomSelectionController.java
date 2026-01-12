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
 * Shows 3 door options and routes to the selected room.
 */
public class RoomSelectionController implements Refreshable {

    @FXML private Label floorLabel;
    @FXML private Label hpLabel;

    @FXML private ImageView backgroundView;

    @FXML private Button doorBtn1;
    @FXML private Button doorBtn2;
    @FXML private Button doorBtn3;

    // Current generated options for the three doors
    private List<RoomOption> currentOptions;

    /**
     * Called by SceneRouter when this scene is shown.
     * Updates HUD, background and door options.
     */
    @Override
    public void onShow() {
        refreshHud();

        // Load and set background image
        var bg = AssetManager.tryLoadImage("images/backgrounds/scelta_porta.png");
        if (bg != null) {
            backgroundView.setImage(bg);
        }
        backgroundView.setMouseTransparent(true);

        // Generate 3 options depending on current floor
        currentOptions = RoomGenerator.generateOptions(GameState.get().getFloor());

        // Apply options to door buttons
        applyOptionToButton(doorBtn1, currentOptions.get(0));
        applyOptionToButton(doorBtn2, currentOptions.get(1));
        applyOptionToButton(doorBtn3, currentOptions.get(2));
    }

    /**
     * Updates button text, icon and special styles based on the room option.
     */
    private void applyOptionToButton(Button btn, RoomOption opt) {
        btn.setText(opt.title());

        // Set icon graphic if available
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

        // Remove previous special classes
        btn.getStyleClass().remove("boss-door");
        btn.getStyleClass().remove("final-boss-door");

        // Highlight boss / final boss doors
        if (opt.type() == RoomType.BOSS_ELITE) {
            btn.getStyleClass().add("boss-door");
        } else if (opt.type() == RoomType.FINAL_BOSS) {
            btn.getStyleClass().add("final-boss-door");
        }
    }

    /**
     * Updates HUD labels with floor and player HP.
     */
    private void refreshHud() {
        int floor = GameState.get().getFloor();
        int hp = GameState.get().getPlayer().getHP();

        floorLabel.setText("Piano: " + floor);
        hpLabel.setText("HP: " + hp);
    }

    // Door button handlers
    @FXML private void onChooseDoor1() { onChoose(0); }
    @FXML private void onChooseDoor2() { onChoose(1); }
    @FXML private void onChooseDoor3() { onChoose(2); }

    /**
     * Common logic for choosing one of the 3 options.
     * Stores the choice and routes to the correct scene.
     */
    private void onChoose(int index) {
        if (currentOptions == null || currentOptions.size() < 3) {
            System.err.println("WARN: opzioni non inizializzate");
            return;
        }

        RoomOption opt = currentOptions.get(index);
        System.out.println("Scelta: " + opt.type() + " (" + opt.title() + ")");

        // Save last chosen option for the next room screen
        RoomContext.get().setLastChosen(opt);

        // Route to the scene associated with the room type
        switch (opt.type()) {
            case FIGHT -> SceneRouter.goTo(SceneId.FIGHT_ROOM);
            case SHOP -> SceneRouter.goTo(SceneId.SHOP_ROOM);
            case BOSS_ELITE -> SceneRouter.goTo(SceneId.BOSS_ROOM);
            case FINAL_BOSS -> SceneRouter.goTo(SceneId.FINAL_BOSS_ROOM);
        }
    }

    /**
     * Debug button: instantly kills the player and shows game over.
     */
    @FXML
    private void onForceDeath() {
        GameState.get().getPlayer().setHP(0);
        SceneRouter.goTo(SceneId.GAME_OVER);
    }
}
