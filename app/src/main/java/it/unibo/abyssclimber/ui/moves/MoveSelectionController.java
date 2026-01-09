package it.unibo.abyssclimber.ui.moves;

import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller for the move selection screen.
 */
public class MoveSelectionController {

    private static final int MAX_SELECTED = 6;
    private static final int COLS = 4;

    @FXML private Label infoLabel;
    @FXML private Button startBtn;

    @FXML private GridPane hydroGrid;
    @FXML private GridPane natureGrid;
    @FXML private GridPane thunderGrid;
    @FXML private GridPane fireGrid;

    private final Set<ToggleButton> selected = new HashSet<>();

    private enum Element { HYDRO, NATURE, THUNDER, FIRE }

    private record MoveStub(String name, String desc, Element element) {}

    @FXML
    private void initialize() {
        // Placeholder: 8 moves for each element (in total 32)
        List<MoveStub> moves = new ArrayList<>();
        moves.addAll(makeMoves(Element.HYDRO, "Hydro"));
        moves.addAll(makeMoves(Element.NATURE, "Natura"));
        moves.addAll(makeMoves(Element.THUNDER, "Fulmine"));
        moves.addAll(makeMoves(Element.FIRE, "Fuoco"));

        fillGrid(hydroGrid, moves.stream().filter(m -> m.element == Element.HYDRO).toList());
        fillGrid(natureGrid, moves.stream().filter(m -> m.element == Element.NATURE).toList());
        fillGrid(thunderGrid, moves.stream().filter(m -> m.element == Element.THUNDER).toList());
        fillGrid(fireGrid, moves.stream().filter(m -> m.element == Element.FIRE).toList());

        refresh();
    }

    private List<MoveStub> makeMoves(Element el, String label) {
        List<MoveStub> out = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            out.add(new MoveStub(label + " Mossa " + i, "Descrizione breve " + i, el));
        }
        return out;
    }

    private void fillGrid(GridPane grid, List<MoveStub> list) {
        grid.getChildren().clear();

        for (int i = 0; i < list.size(); i++) {
            MoveStub m = list.get(i);

            ToggleButton tb = new ToggleButton();
            tb.getStyleClass().add("move-tile");
            tb.setWrapText(true);

            tb.setText(m.name + "\n" + m.desc);

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

    private void refresh() {
        infoLabel.setText("Seleziona 6 mosse (" + selected.size() + "/" + MAX_SELECTED + ").");
        startBtn.setDisable(selected.size() != MAX_SELECTED);
    }

    @FXML
    private void onBack() {
        SceneRouter.goTo(SceneId.CHARACTER_CREATION);
    }

    @FXML
    private void onStartRun() {
        if (selected.size() != MAX_SELECTED) return;

        // TODO: quando avrò il codice dei compagni:
        // - leggere mosse reali da MoveLoader
        // - salvare le 6 scelte nel Player/PlayerState vero

        SceneRouter.goTo(SceneId.ROOM_SELECTION);
    }
}
