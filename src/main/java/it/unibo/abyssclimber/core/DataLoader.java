package it.unibo.abyssclimber.core;

import java.io.InputStream;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unibo.abyssclimber.model.Creature;
import it.unibo.abyssclimber.model.Item;

/**
 * Classe responsabile della gestione della persistenza e del caricamento dei dati di gioco.
 * Utilizza la libreria Jackson per deserializzare i file JSON presenti nelle risorse
 * e convertirli in oggetti Java utilizzabili dal sistema ({@link Item} e {@link Creature}).
 * Questa classe agisce come un Data Access Object (DAO) semplificato per le risorse statiche.
 */
public class DataLoader {
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Carica la lista degli oggetti dal file di configurazione JSON.
     * Il metodo cerca il file nel percorso risorse {@code /liste/items.json}.
     * @return una {@link List} contenente tutti gli oggetti {@link Item} caricati.
     * @throws RuntimeException se il file JSON non viene trovato nel percorso specificato.
     * @throws Exception se si verificano errori di I/O o di parsing durante la lettura del file.
     */
    public List<Item> loadItems() throws Exception {
        try (InputStream in = DataLoader.class.getResourceAsStream("/liste/items.json")) { 
            if (in == null) {
                throw new RuntimeException("File /liste/items.json not found in resources!");
            }
            return mapper.readValue(in, new TypeReference<List<Item>>() {});
        }
    }

    /**
     * Carica la lista delle creature dal file di configurazione JSON.
     * Il metodo cerca il file nel percorso risorse {@code /liste/creatures.json}.
     * I dati caricati includono statistiche base, elementi e stage di appartenenza.
     * @return una {@link List} contenente tutte le creature {@link Creature} caricate.
     * @throws RuntimeException se il file JSON non viene trovato nel percorso specificato.
     * @throws Exception se si verificano errori di I/O o di parsing durante la lettura del file.
     */
    public List<Creature> loadMonsters() throws Exception {
        try (InputStream in = DataLoader.class.getResourceAsStream("/liste/creatures.json")) {
            if (in == null) {
                throw new RuntimeException("File /liste/creatures.json not found in resources!");
            }
            return mapper.readValue(in, new TypeReference<List<Creature>>() {}); //mapper fa parte di jackson, in é lo stream di input che legge il file json e TypeReference serve per specificare il tipo di dato che voglio leggere, in questo caso una lista di Creature
        }
    }
}