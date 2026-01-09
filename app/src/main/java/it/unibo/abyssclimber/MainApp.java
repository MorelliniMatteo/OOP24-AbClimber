package it.unibo.abyssclimber;

import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        // Inizialazing SceneRouter with the primary stage
        SceneRouter.init(stage);

        // Title of the window
        stage.setTitle("Abyss Climber");

        // Blocking resizing of the window
        stage.setResizable(false);

        // Go to the main menu scene
        SceneRouter.goTo(SceneId.MAIN_MENU);

        // It shows the window
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
