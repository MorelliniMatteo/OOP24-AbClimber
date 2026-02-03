package it.unibo.abyssclimber;

import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Image icon = new Image(getClass().getResourceAsStream("/assets/images/logo.png"));
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Impossible to load icon: " + e.getMessage());
        }

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

}
