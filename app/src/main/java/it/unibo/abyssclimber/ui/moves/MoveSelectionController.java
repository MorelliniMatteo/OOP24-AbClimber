package it.unibo.abyssclimber.ui.moves;

import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import it.unibo.abyssclimber.core.combat.MoveLoader;
import it.unibo.abyssclimber.model.Tipo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller for the move selection screen.
 * Handles loading, displaying and selecting combat moves.
 */
public class MoveSelectionController {

    // Maximum number of selectable moves
    private static final int MAX_SELECTED = 6;

    // Number of columns for each grid
    private static final int COLS = 4;

    @FXML private Label infoLabel;
    @FXML private Button startBtn;

    // Grids for each elemental type
    @FXML private GridPane hydroGrid;
    @FXML private GridPane natureGrid;
    @FXML private GridPane thunderGrid;
    @FXML private GridPane fireGrid;

    // Set of currently selected toggle buttons
    private final Set<ToggleButton> selected = new HashSet<>();

    /**
     * Called automatically by JavaFX after FXML loading.
     * Loads moves and fills all grids.
     */
    @FXML
    private void initialize() {
        List<MoveLoader.Move> moves = loadMovesSafe();

        // Disable screen if moves cannot be loaded
        if (moves.isEmpty()) {
            infoLabel.setText("Errore nel caricamento mosse.");
            startBtn.setDisable(true);
            return;
        }

        // Fill grids by element type
        fillGrid(hydroGrid, filterByElement(moves, Tipo.HYDRO));
        fillGrid(natureGrid, filterByElement(moves, Tipo.NATURE));
        fillGrid(thunderGrid, filterByElement(moves, Tipo.LIGHTNING));
        fillGrid(fireGrid, filterByElement(moves, Tipo.FIRE));

        refresh();
    }

    /**
     * Loads moves from MoveLoader, handling IO errors safely.
     */
    private List<MoveLoader.Move> loadMovesSafe() {
        if (MoveLoader.moves.isEmpty()) {
            try {
                MoveLoader.loadMoves();
            } catch (IOException e) {
                System.err.println("Errore caricamento mosse: " + e.getMessage());
                return List.of();
            }
        }
        return new ArrayList<>(MoveLoader.moves);
    }

    /**
     * Filters moves by their elemental type.
     */
    private List<MoveLoader.Move> filterByElement(List<MoveLoader.Move> moves, Tipo element) {
        return moves.stream()
            .filter(m -> m.getElement() == element)
            .toList();
    }

    /**
     * Populates a grid with toggle buttons representing moves.
     */
    private void fillGrid(GridPane grid, List<MoveLoader.Move> list) {
        grid.getChildren().clear();

        for (int i = 0; i < list.size(); i++) {
            MoveLoader.Move m = list.get(i);

            ToggleButton tb = new ToggleButton();
            tb.getStyleClass().add("move-tile");
            tb.setWrapText(true);

            // Move short description
            String desc = "Potenza " + m.getPower()
                + " | Acc " + m.getAcc()
                + " | Costo " + m.getCost();

            tb.setText(m.getName() + "\n" + desc);
            tb.setUserData(m);

            // Handle selection and max limit
            tb.setOnAction(e -> {
                if (tb.isSelected()) {
                    if (selected.size() >= MAX_SELECTED) {
                        tb.setSelected(false);
                        return;
                    }
                    selected.add(tb);
                } else {
                    selected.remove(tb);
                }
                refresh();
            });

            int row = i / COLS;
            int col = i % COLS;
            grid.add(tb, col, row);
        }
    }

    /**
     * Updates UI info and start button state.
     */
    private void refresh() {
        infoLabel.setText("Seleziona 6 mosse (" + selected.size() + "/" + MAX_SELECTED + ").");
        startBtn.setDisable(selected.size() != MAX_SELECTED);
    }

    /**
     * Returns to character creation screen.
     */
    @FXML
    private void onBack() {
        SceneRouter.goTo(SceneId.CHARACTER_CREATION);
    }

    /**
     * Starts the run after move selection.
     */
    @FXML
    private void onStartRun() {
        if (selected.size() != MAX_SELECTED) return;

        // TODO: save selected moves into Player / PlayerState

        SceneRouter.goTo(SceneId.ROOM_SELECTION);
    }
}
