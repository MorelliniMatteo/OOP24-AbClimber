package it.unibo.abyssclimber.ui.character;

import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import it.unibo.abyssclimber.model.Classe;
import it.unibo.abyssclimber.model.Tipo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * Controller for the character creation screen.
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

    // Toggle groups ensure that only one element and one class can be selected
    private final ToggleGroup elementGroup = new ToggleGroup();
    private final ToggleGroup classGroup = new ToggleGroup();

    @FXML
    private void initialize() {
        // Configure element toggles and bind enum values
        configureElementToggle(hydroBtn, Tipo.HYDRO);
        configureElementToggle(natureBtn, Tipo.NATURE);
        configureElementToggle(thunderBtn, Tipo.LIGHTNING);
        configureElementToggle(fireBtn, Tipo.FIRE);

        // Configure class toggles and bind enum values
        configureClassToggle(mageBtn, Classe.MAGO);
        configureClassToggle(soldierBtn, Classe.SOLDATO);
        configureClassToggle(knightBtn, Classe.CAVALIERE);

        // Update summary whenever a selection changes
        elementGroup.selectedToggleProperty().addListener((obs, o, n) -> updateSummary());
        classGroup.selectedToggleProperty().addListener((obs, o, n) -> updateSummary());

        updateSummary();
    }

    // Binds an element enum value to a toggle button
    private void configureElementToggle(ToggleButton button, Tipo tipo) {
        button.setToggleGroup(elementGroup);
        button.setUserData(tipo);
        button.setText(tipo.displayName()); // label comes from enum
    }

    // Binds a class enum value to a toggle button
    private void configureClassToggle(ToggleButton button, Classe classe) {
        button.setToggleGroup(classGroup);
        button.setUserData(classe);
        button.setText(classe.getName()); // label comes from enum
    }

    // Updates the summary label based on current selections
    private void updateSummary() {
        Tipo el = elementGroup.getSelectedToggle() != null
            ? (Tipo) elementGroup.getSelectedToggle().getUserData()
            : null;
        Classe cl = classGroup.getSelectedToggle() != null
            ? (Classe) classGroup.getSelectedToggle().getUserData()
            : null;

        String elLabel = el != null ? el.displayName() : "—";
        String clLabel = cl != null ? cl.getName() : "—";
        summaryLabel.setText("Tipo: " + elLabel + " | Classe: " + clLabel);
    }

    // Returns to the main menu without creating a player
    @FXML
    private void onBack() {
        SceneRouter.goTo(SceneId.MAIN_MENU);
    }

    // Confirms the selection and initializes the player
    @FXML
    private void onConfirm() {
        Tipo chosenTipo = elementGroup.getSelectedToggle() != null
            ? (Tipo) elementGroup.getSelectedToggle().getUserData()
            : null;

        Classe chosenClasse = classGroup.getSelectedToggle() != null
            ? (Classe) classGroup.getSelectedToggle().getUserData()
            : null;

        // Prevent confirmation if both selections are not made
        if (chosenTipo == null || chosenClasse == null) {
            System.err.println("Seleziona sia Tipo che Classe prima di confermare.");
            return;
        }

        GameState.get().initializePlayer("Hero", chosenTipo, chosenClasse);
        SceneRouter.goTo(SceneId.MOVE_SELECTION);
    }
}
