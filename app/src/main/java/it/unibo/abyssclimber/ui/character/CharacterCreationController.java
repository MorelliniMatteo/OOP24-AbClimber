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
        configureElementToggle(hydroBtn, Tipo.HYDRO);
        configureElementToggle(natureBtn, Tipo.NATURE);
        configureElementToggle(thunderBtn, Tipo.LIGHTNING);
        configureElementToggle(fireBtn, Tipo.FIRE);

        // group classes
        configureClassToggle(mageBtn, Classe.MAGO);
        configureClassToggle(soldierBtn, Classe.SOLDATO);
        configureClassToggle(knightBtn, Classe.CAVALIERE);

        elementGroup.selectedToggleProperty().addListener((obs, o, n) -> updateSummary());
        classGroup.selectedToggleProperty().addListener((obs, o, n) -> updateSummary());

        updateSummary();
    }
    
    private void configureElementToggle(ToggleButton button, Tipo tipo) {
        button.setToggleGroup(elementGroup);
        button.setUserData(tipo);
    }

    private void configureClassToggle(ToggleButton button, Classe classe) {
        button.setToggleGroup(classGroup);
        button.setUserData(classe);
    }

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

    @FXML
    private void onBack() {
        SceneRouter.goTo(SceneId.MAIN_MENU);
    }

    // TODO: qui vanno messi gli enum e non le stringhe
    @FXML
    private void onConfirm() {
        String elText = elementGroup.getSelectedToggle() instanceof ToggleButton tb ? tb.getText() : null;
        String clText = classGroup.getSelectedToggle() instanceof ToggleButton tb ? tb.getText() : null;

        if (elText == null || clText == null) {
            System.err.println("Seleziona sia Tipo che Classe prima di confermare.");
            return;
        }

        // conversione da stringa ad enum tipo
        Tipo chosenTipo = null;
        switch (elText) {
            case "Hydro":   chosenTipo = Tipo.HYDRO; break;
            case "Fire":    chosenTipo = Tipo.FIRE; break;
            case "Nature":  chosenTipo = Tipo.NATURE; break;
            case "Thunder": chosenTipo = Tipo.LIGHTNING; break; 
            default: 
                System.err.println("Errore: Tipo non riconosciuto -> " + elText);
                return;
        }

        // conversione da stringa ad enum classe
        Classe chosenClasse = null;
        switch (clText) {
            case "Knight":  chosenClasse = Classe.CAVALIERE; break;
            case "Mage":    chosenClasse = Classe.MAGO; break;
            case "Soldier": chosenClasse = Classe.SOLDATO; break;
            default:
                System.err.println("Errore: Classe non riconosciuta -> " + clText);
                return;
        }

        GameState.get().initializePlayer("Hero", chosenTipo, chosenClasse);

        SceneRouter.goTo(SceneId.MOVE_SELECTION);
    }
}