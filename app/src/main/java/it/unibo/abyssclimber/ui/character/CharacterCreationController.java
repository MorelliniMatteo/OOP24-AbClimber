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
        //selezione Elemento
        Tipo chosenTipo = null;
        ToggleButton selectedEl = (ToggleButton) elementGroup.getSelectedToggle();
        
        if (selectedEl == hydroBtn)        chosenTipo = Tipo.HYDRO; //ora controlla quale bottone hai premuto e assegna il tipo corrispondente
        else if (selectedEl == fireBtn)    chosenTipo = Tipo.FIRE; 
        else if (selectedEl == natureBtn)  chosenTipo = Tipo.NATURE;
        else if (selectedEl == thunderBtn) chosenTipo = Tipo.LIGHTNING;

        //Selezione Classe
        Classe chosenClasse = null;
        ToggleButton selectedCl = (ToggleButton) classGroup.getSelectedToggle();
        
        if (selectedCl == knightBtn)       chosenClasse = Classe.CAVALIERE; //la stessa cosa si applica per la classe
        else if (selectedCl == mageBtn)    chosenClasse = Classe.MAGO;
        else if (selectedCl == soldierBtn) chosenClasse = Classe.SOLDATO;

        if (chosenTipo == null || chosenClasse == null) {
            System.err.println("Seleziona sia Tipo che Classe prima di confermare.");
            return;
        }

        // Initialize the player. Name is hardcoded to "Hero" for now as there is no input field.
        GameState.get().initializePlayer("Hero", chosenTipo, chosenClasse);

        SceneRouter.goTo(SceneId.MOVE_SELECTION);
    }
}