package it.unibo.abyssclimber.ui.character;

import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * Controller for the character creation screen.
 */
public class CharacterCreationController {

    @FXML private Label summaryLabel;

    @FXML private ToggleButton hydroBtn;
    @FXML private ToggleButton natureBtn;
    @FXML private ToggleButton thunderBtn;
    @FXML private ToggleButton fireBtn;

    @FXML private ToggleButton mageBtn;
    @FXML private ToggleButton soldierBtn;
    @FXML private ToggleButton knightBtn;

    private final ToggleGroup elementGroup = new ToggleGroup();
    private final ToggleGroup classGroup = new ToggleGroup();

    @FXML
    private void initialize() {
        // group elements
        hydroBtn.setToggleGroup(elementGroup);
        natureBtn.setToggleGroup(elementGroup);
        thunderBtn.setToggleGroup(elementGroup);
        fireBtn.setToggleGroup(elementGroup);

        // group classes
        mageBtn.setToggleGroup(classGroup);
        soldierBtn.setToggleGroup(classGroup);
        knightBtn.setToggleGroup(classGroup);

        elementGroup.selectedToggleProperty().addListener((obs, o, n) -> updateSummary());
        classGroup.selectedToggleProperty().addListener((obs, o, n) -> updateSummary());

        updateSummary();
    }

    private void updateSummary() {
        String el = elementGroup.getSelectedToggle() instanceof ToggleButton tb ? tb.getText() : "—";
        String cl = classGroup.getSelectedToggle() instanceof ToggleButton tb ? tb.getText() : "—";
        summaryLabel.setText("Tipo: " + el + " | Classe: " + cl);
    }

    @FXML
    private void onBack() {
        SceneRouter.goTo(SceneId.MAIN_MENU);
    }

    @FXML
    private void onConfirm() {
        // Placeholder save: store strings for now, to avoid depending on teammates' enums
        String el = elementGroup.getSelectedToggle() instanceof ToggleButton tb ? tb.getText() : null;
        String cl = classGroup.getSelectedToggle() instanceof ToggleButton tb ? tb.getText() : null;

        if (el == null || cl == null) {
            System.err.println("Seleziona sia Tipo che Classe prima di confermare.");
            return;
        }

        GameState.get().getPlayer().setChosenElement(el);
        GameState.get().getPlayer().setChosenClass(cl);

        SceneRouter.goTo(SceneId.MOVE_SELECTION);
    }
}
