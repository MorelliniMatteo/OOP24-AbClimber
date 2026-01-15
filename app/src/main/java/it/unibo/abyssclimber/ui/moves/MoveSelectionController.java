package it.unibo.abyssclimber.ui.moves;

import it.unibo.abyssclimber.core.GameState;
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
 * Allows the player to choose a fixed number of moves.
 */
public class MoveSelectionController {

    // Maximum number of selectable moves
    private static final int MAX_SELECTED = 6;

    // Number of columns in each grid
    private static final int COLS = 4;

    @FXML private Label infoLabel;
    @FXML private Button startBtn;

    // Grids divided by element type
    @FXML private GridPane hydroGrid;
    @FXML private GridPane natureGrid;
    @FXML private GridPane thunderGrid;
    @FXML private GridPane fireGrid;

    // Currently selected move buttons
    private final Set<ToggleButton> selected = new HashSet<>();

    /**
     * Called automatically after FXML loading.
     * Loads moves and populates the grids.
     */
    @FXML
    private void initialize() {
        List<MoveLoader.Move> moves = loadMovesSafe();

        // Handle loading error
        if (moves.isEmpty()) {
            infoLabel.setText("Errore nel caricamento mosse.");
            startBtn.setDisable(true);
            return;
        }

        // Fill grids with moves filtered by element
        fillGrid(hydroGrid, filterByElement(moves, Tipo.HYDRO));
        fillGrid(natureGrid, filterByElement(moves, Tipo.NATURE));
        fillGrid(thunderGrid, filterByElement(moves, Tipo.LIGHTNING));
        fillGrid(fireGrid, filterByElement(moves, Tipo.FIRE));

        refresh();
    }

    /**
     * Safely loads moves from the MoveLoader.
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
     * Returns only moves of the given element.
     */
    private List<MoveLoader.Move> filterByElement(List<MoveLoader.Move> moves, Tipo element) {
        return moves.stream()
            .filter(m -> m.getElement() == element)
            .toList();
    }

    /**
     * Creates toggle buttons for each move and adds them to the grid.
     */
    private void fillGrid(GridPane grid, List<MoveLoader.Move> list) {
        grid.getChildren().clear();

        for (int i = 0; i < list.size(); i++) {
            MoveLoader.Move m = list.get(i);

            ToggleButton tb = new ToggleButton();
            tb.getStyleClass().add("move-tile");
            tb.setWrapText(true);

            // Short move description
            String desc = "Potenza " + m.getPower()
                + " | Acc " + m.getAcc()
                + " | Costo " + m.getCost();

            tb.setText(m.getName() + "\n" + desc);
            tb.setUserData(m);

            // Selection logic with max limit check
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
     * Updates label text and start button state.
     */
    private void refresh() {
        boolean hasCostOne = hasCostOneSelected();
        if (selected.size() == MAX_SELECTED && !hasCostOne) {
            infoLabel.setText("Devi selezionare almeno una mossa con costo 1.");
        } else {
            infoLabel.setText("Seleziona 6 mosse (" + selected.size() + "/" + MAX_SELECTED + ").");
        }
        startBtn.setDisable(selected.size() != MAX_SELECTED || !hasCostOne);
    }

    /**
     * Goes back to the character creation screen.
     */
    @FXML
    private void onBack() {
        SceneRouter.goTo(SceneId.CHARACTER_CREATION);
    }

    /**
     * Saves selected moves and starts the run.
     */
    @FXML
    private void onStartRun() {
        if (selected.size() != MAX_SELECTED || !hasCostOneSelected()) {
            refresh();
            return;
        }

        // Extract selected moves from toggle buttons
        List<MoveLoader.Move> chosen = selected.stream()
            .map(tb -> (MoveLoader.Move) tb.getUserData())
            .toList();

        // Store moves in the global game state
        GameState.get().getPlayer().setSelectedMoves(chosen);

        SceneRouter.goTo(SceneId.ROOM_SELECTION);
    }

    private boolean hasCostOneSelected() {
        return selected.stream()
            .map(tb -> (MoveLoader.Move) tb.getUserData())
            .anyMatch(move -> move.getCost() == 1);
    }
}
