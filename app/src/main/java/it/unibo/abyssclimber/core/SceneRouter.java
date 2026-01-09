package it.unibo.abyssclimber.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class responsible for routing between different scenes in the application.
 */
public final class SceneRouter {

    private static Stage stage;

    private SceneRouter() { }

    public static void init(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void goTo(SceneId id) {
        if (stage == null) {
            throw new IllegalStateException("SceneRouter non inizializzato (chiama init() da MainApp)");
        }

        String fxmlName = switch (id) {
            case MAIN_MENU           -> "main_menu.fxml";
            case CHARACTER_CREATION  -> "character_creation.fxml";
            case MOVE_SELECTION      -> "move_selection.fxml";
            case ROOM_SELECTION      -> "room_selection.fxml";
            case ROOM_PLACEHOLDER    -> "room_placeholder.fxml";
            case GAME_OVER           -> "game_over.fxml";
        };

        try {
            var url = SceneRouter.class.getResource("/fxml/" + fxmlName);
            if (url == null) {
                throw new IllegalStateException("FXML non trovato: /fxml/" + fxmlName);
            }

            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            Scene scene = new Scene(root, 1280, 720);

            var cssUrl = SceneRouter.class.getResource("/style/main.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            stage.setScene(scene);

            Object controller = loader.getController();
            if (controller instanceof Refreshable refreshable) {
                refreshable.onShow();
            }

        } catch (IOException e) {
            throw new RuntimeException("Errore nel caricamento di " + fxmlName, e);
        }
    }
}
