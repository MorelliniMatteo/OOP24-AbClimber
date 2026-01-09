package it.unibo.abyssclimber.core;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class for loading and caching image assets.
 */
public final class AssetManager {
    // Cache for loaded images
    private static final Map<String, Image> IMAGE_CACHE = new ConcurrentHashMap<>();

    private AssetManager() {
    }

    /**
     * Loads an image from the specified path, caching it for future use.
     *
     * @param path the relative path to the image asset
     * @return the loaded Image
     * @throws IllegalArgumentException if the image cannot be found
     * @throws RuntimeException         if there is an error loading the image
     */
    public static Image loadImage(String path) {
        return IMAGE_CACHE.computeIfAbsent(path, p -> {
            String full = "/assets/" + p;
            try (InputStream is = AssetManager.class.getResourceAsStream(full)) {
                if (is == null) {
                    throw new IllegalArgumentException("Immagine non trovata: " + full);
                }
                return new Image(is);
            } catch (Exception e) {
                throw new RuntimeException("Errore caricando immagine: " + full, e);
            }
        });
    }

    /**
     * Attempts to load an image from the specified path.
     * If loading fails, logs a warning and returns null.
     *
     * @param path the relative path to the image asset
     * @return the loaded Image, or null if loading failed
     */
    public static Image tryLoadImage(String path) {
        try {
            return loadImage(path);
        } catch (Exception e) {
            System.err.println("WARN: " + e.getMessage());
            return null;
        }
    }
}
