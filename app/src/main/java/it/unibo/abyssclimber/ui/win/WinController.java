package it.unibo.abyssclimber.ui.win;

import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.fxml.FXML;

public class WinController {

    @FXML
    private void onReturnToMenu() {
        SceneRouter.goTo(SceneId.MAIN_MENU);
    }
}
