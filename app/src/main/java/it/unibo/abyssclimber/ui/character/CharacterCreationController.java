package it.unibo.abyssclimber.ui.character;

import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import it.unibo.abyssclimber.model.Classe;
import it.unibo.abyssclimber.model.Difficulty;
import it.unibo.abyssclimber.model.Tipo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * Controller for the character creation screen.
 * Handles element, class and difficulty selection.
 */
public class CharacterCreationController {

    @FXML private Label summaryLabel;

    // Element selection buttons
    @FXML private ToggleButton hydroBtn;
    @FXML private ToggleButton natureBtn;
    @FXML private ToggleButton thunderBtn;
    @FXML private ToggleButton fireBtn;

    // Class selection buttons
    @FXML private ToggleButton mageBtn;
    @FXML private ToggleButton soldierBtn;
    @FXML private ToggleButton knightBtn;

    // Difficulty selection buttons
    @FXML private ToggleButton normalBtn;
    @FXML private ToggleButton hardBtn;

    // ToggleGroups ensure that only one option per category can be selected
    private final ToggleGroup elementGroup = new ToggleGroup();
    private final ToggleGroup classGroup = new ToggleGroup();
    private final ToggleGroup difficultyGroup = new ToggleGroup();

    @FXML
    private void initialize() {

        // Bind element buttons to their enum values
        configureElementToggle(hydroBtn, Tipo.HYDRO);
        configureElementToggle(natureBtn, Tipo.NATURE);
        configureElementToggle(thunderBtn, Tipo.LIGHTNING);
        configureElementToggle(fireBtn, Tipo.FIRE);

        // Bind class buttons to their enum values
        configureClassToggle(mageBtn, Classe.MAGO);
        configureClassToggle(soldierBtn, Classe.SOLDATO);
        configureClassToggle(knightBtn, Classe.CAVALIERE);

        // Bind difficulty buttons to their multipliers
        configureDifficultyToggle(normalBtn, "Normal", 1.0);
        configureDifficultyToggle(hardBtn, "Hard", 1.15);

        // Default difficulty selection
        normalBtn.setSelected(true);

        // Update the summary label when a selection changes
        elementGroup.selectedToggleProperty().addListener((obs, o, n) -> updateSummary());
        classGroup.selectedToggleProperty().addListener((obs, o, n) -> updateSummary());
        difficultyGroup.selectedToggleProperty().addListener((obs, o, n) -> updateSummary());

        updateSummary();
    }

    // Associates an element enum value with a toggle button
    private void configureElementToggle(ToggleButton button, Tipo tipo) {
        button.setToggleGroup(elementGroup);
        button.setUserData(tipo);
        button.setText(tipo.displayName());
    }

    // Associates a class enum value with a toggle button
    private void configureClassToggle(ToggleButton button, Classe classe) {
        button.setToggleGroup(classGroup);
        button.setUserData(classe);
        button.setText(classe.getName());
    }

    // Associates a difficulty multiplier with a toggle button
    private void configureDifficultyToggle(ToggleButton button, String label, double multiplier) {
        button.setToggleGroup(difficultyGroup);
        button.setText(label);
        button.setUserData(multiplier);
    }

    // Updates the summary label based on the current selections
    private void updateSummary() {
        String el = elementGroup.getSelectedToggle() != null
                ? ((Tipo) elementGroup.getSelectedToggle().getUserData()).displayName()
                : "—";

        String cl = classGroup.getSelectedToggle() != null
                ? ((Classe) classGroup.getSelectedToggle().getUserData()).getName()
                : "—";

        String diff = difficultyGroup.getSelectedToggle() != null
                ? ((ToggleButton) difficultyGroup.getSelectedToggle()).getText()
                : "—";

        summaryLabel.setText(
                "Tipo: " + el + " | Classe: " + cl + " | Difficoltà: " + diff
        );
    }

    // Returns to the main menu without creating the character
    @FXML
    private void onBack() {
        SceneRouter.goTo(SceneId.MAIN_MENU);
    }

    // Confirms the selections and initializes the player
    @FXML
    private void onConfirm() {

        // Prevent confirmation if something is not selected
        if (elementGroup.getSelectedToggle() == null
                || classGroup.getSelectedToggle() == null
                || difficultyGroup.getSelectedToggle() == null) {
            System.err.println("Select Tipo, Classe and Difficulty first.");
            return;
        }

        Tipo tipo = (Tipo) elementGroup.getSelectedToggle().getUserData();
        Classe classe = (Classe) classGroup.getSelectedToggle().getUserData();
        double multiplier = (double) difficultyGroup.getSelectedToggle().getUserData();

        // Apply the selected difficulty multiplier
        Difficulty.setDifficultyMultiplier(multiplier);

        // Create the player and move to the next scene
        GameState.get().initializePlayer("Hero", tipo, classe);
        SceneRouter.goTo(SceneId.MOVE_SELECTION);
    }
}
