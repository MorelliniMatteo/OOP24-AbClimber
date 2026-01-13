package it.unibo.abyssclimber.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Routes the application to the requested JavaFX scene (FXML-based).
 */
public final class SceneRouter {

    // Main application stage (must be set once at startup)
    private static Stage stage;

    private SceneRouter() { }

    /**
     * Initializes the router with the primary stage.
     * Must be called once from MainApp.
     */
    public static void init(Stage primaryStage) {
        stage = primaryStage;
    }

    /**
     * Loads the FXML associated with the given SceneId and sets it on the stage.
     */
    public static void goTo(SceneId id) {
        if (stage == null) {
            throw new IllegalStateException("SceneRouter non inizializzato (chiama init() da MainApp)");
        }

        // Map each SceneId to its corresponding FXML file
        String fxmlName = switch (id) {
            case MAIN_MENU           -> "main_menu.fxml";
            case CHARACTER_CREATION  -> "character_creation.fxml";
            case MOVE_SELECTION      -> "move_selection.fxml";
            case ROOM_SELECTION      -> "room_selection.fxml";

            case FIGHT_ROOM          -> "fight_room.fxml";
            case SHOP_ROOM           -> "shop_room.fxml";
            case BOSS_ROOM           -> "boss_room.fxml";
            case FINAL_BOSS_ROOM     -> "final_boss_room.fxml";

            case ROOM_PLACEHOLDER    -> "room_placeholder.fxml";
            case GAME_OVER           -> "game_over.fxml";
            case SHOP                -> "shop.fxml"; 
        };

        try {
            // Resolve the FXML resource from /resources/fxml/
            var url = SceneRouter.class.getResource("/fxml/" + fxmlName);
            if (url == null) {
                throw new IllegalStateException("FXML non trovato: /fxml/" + fxmlName);
            }

            // Load the UI tree and its controller
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            // Create the scene with a fixed window size
            Scene scene = new Scene(root, 1280, 720);

            // Attach the main CSS stylesheet if available
            var cssUrl = SceneRouter.class.getResource("/style/main.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            // Swap the scene on the stage
            stage.setScene(scene);

            // If the controller supports Refreshable, notify it when shown
            Object controller = loader.getController();
            if (controller instanceof Refreshable refreshable) {
                refreshable.onShow();
            }

        } catch (IOException e) {
            // Wrap loading errors as unchecked exceptions
            throw new RuntimeException("Errore nel caricamento di " + fxmlName, e);
        }
    }
}