package it.unibo.abyssclimber.ui.assets;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestore delle risorse grafiche (Assets) relative alle creature.
 * Questa classe funge da ponte tra il livello dei dati (Model), dove i mostri sono identificati
 * esclusivamente da ID numerici, e il livello di presentazione (View), che richiede file immagine.
 * Centralizzando la mappatura in questa classe di utilità, si ottiene un disaccoppiamento completo:
 * le classi del modello (es. {@code Creature}) non devono conoscere l'esistenza dei file grafici.
 */
public class CreaturesAssets {

    /** 
     * Mappa di registro che associa l'ID univoco del mostro al percorso relativo del file immagine.
     * La scelta di usare una mappa statica permette un accesso O(1) rapido alle risorse
     * senza dover ricalcolare i percorsi a ogni frame di rendering.
     */
    private static final Map<Integer, String> ID_TO_PATH_MAP = new HashMap<>();

    static {
        // collego l'ID del JSON al file immagine
        ID_TO_PATH_MAP.put(1, "assets/images/creatures/slimy_slime.png");
        ID_TO_PATH_MAP.put(2, "assets/images/creatures/black_rat.png");
        ID_TO_PATH_MAP.put(3, "assets/images/creatures/wandering_flame.png");
        ID_TO_PATH_MAP.put(4, "assets/images/creatures/static_spark.png");
        ID_TO_PATH_MAP.put(5, "assets/images/creatures/minor_shade.png");
        ID_TO_PATH_MAP.put(6, "assets/images/creatures/storm_cloud.png");
        ID_TO_PATH_MAP.put(7, "assets/images/creatures/moss_wolf.png");
        ID_TO_PATH_MAP.put(8, "assets/images/creatures/triton_warrior.png");
        ID_TO_PATH_MAP.put(9, "assets/images/creatures/screaming_ghost.png");
        ID_TO_PATH_MAP.put(10, "assets/images/creatures/magma_golem.png");
        ID_TO_PATH_MAP.put(11, "assets/images/creatures/living_storm.png");
        ID_TO_PATH_MAP.put(12, "assets/images/creatures/minor_kraken.png");
        ID_TO_PATH_MAP.put(13, "assets/images/creatures/hell_salamander.png");
        ID_TO_PATH_MAP.put(14, "assets/images/creatures/treant_elder.png");
        ID_TO_PATH_MAP.put(15, "assets/images/creatures/soul_eater.png");
        ID_TO_PATH_MAP.put(16, "assets/images/creatures/dragon_of_the_thunder.png");
        ID_TO_PATH_MAP.put(17, "assets/images/creatures/dark_phoenix.png");
        ID_TO_PATH_MAP.put(18, "assets/images/creatures/hydra_of_the_swamp.png");
        ID_TO_PATH_MAP.put(19, "assets/images/creatures/armored_leviathan.png");
        ID_TO_PATH_MAP.put(20, "assets/images/creatures/forest_titan.png");
        ID_TO_PATH_MAP.put(21, "assets/images/creatures/lord_of_the_abyss.png");
    }

    /**
     * Carica e restituisce l'immagine JavaFX associata all'ID di un mostro.
     * Utilizza {@code getResourceAsStream} per garantire che le immagini vengano caricate
     * correttamente anche quando l'applicazione è pacchettizzata in un JAR.
     * @param monsterId L'identificativo numerico del mostro (proveniente dal JSON/Model).
     * @return L'oggetto {@link Image} caricato, o {@code null} se il caricamento fallisce.
     */
    public static Image getMonsterImage(int monsterId) { // restituisce l'immagine del mostro dato il suo ID
        String path = ID_TO_PATH_MAP.get(monsterId);

        if (path == null) {
            System.out.println("No images for this monster " + monsterId);
        }

        // restitusice l'immagine caricata dal percorso
        try {
            return new Image(CreaturesAssets.class.getResourceAsStream("/" + path));
        } catch (Exception e) {
            System.err.println("Error in loading images: " + path);
            return null;
        }
    }
}