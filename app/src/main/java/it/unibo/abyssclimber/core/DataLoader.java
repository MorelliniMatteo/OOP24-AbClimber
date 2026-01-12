package it.unibo.abyssclimber.core;

import java.io.InputStream;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unibo.abyssclimber.model.Creature;
import it.unibo.abyssclimber.model.Item;

public class DataLoader {
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Crea e restituisce una lista di oggetti caricati dal file items.json
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
     * Crea e restituisce una lista di mostri caricati dal file monsters.json
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