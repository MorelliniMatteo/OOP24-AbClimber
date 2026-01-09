package it.unibo.abyssclimber.core;

import java.util.List;
import java.util.Map;
import java.util.Random;

import it.unibo.abyssclimber.model.Creature;
import it.unibo.abyssclimber.model.Item;
import it.unibo.abyssclimber.model.Stage;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.ArrayList;

/*
    *Classe che crea una mappa chiamata itemsMap e copia su una lista tramite il file DataLoader tutti gli oggetti presi dal file items.json
    *Questi oggetti della lista vengono poi inseriti nella mappa itemsMap con chiave l'id dell'oggetto e l'oggetto stesso come valore
 */
public class GameCatalog {
    private static Map<Integer, Item> itemsMap = new HashMap<>();
    private static Map<Stage, List<Creature>> monstersMap = new EnumMap<>(Stage.class); //dato che so giá la struttura che deve avere l'hashmap perché uso Enum, uso EnumMap che permette una lettura piú veloce
    private static List<Item> items = new ArrayList<>();

    public static void initialize() throws Exception {
        DataLoader dataLoader = new DataLoader();
        items = dataLoader.loadItems();
        for (Item item : items) {
            itemsMap.put(item.getID(), item); //mette dentro itemsMap l'id che prende tramite il metodo getId e l'oggetto stesso
        }
        System.out.println("Registry initialized. Objects in memory: " + itemsMap.size());

        List<Creature> monsters = dataLoader.loadMonsters();
        for (Stage stage : Stage.values()){ //inizializzo la mappa con le chiavi Stage e liste vuote che conteranno i mostri con quel determinato stage
            monstersMap.put(stage, new ArrayList<>());
        }
        for (Creature monster : monsters){ //cerca i mostri nella lista dei mostri che hanno uno stage definito e li inserisce nella mappa sotto lo stage corrispondente
            if (monster.getStage() != null){
                Stage stageEnum = Stage.valueOf(monster.getStage()); //valueOf converte la stringa che rappresenta lo stage del mostro nella lista e lo converte in un elemento dell'enum Stage
                monstersMap.get(stageEnum).add(monster);             //é esattamente Stage. che determina in cosa deve essere trasformata la stringa o qualsiasi cosa ci sta dopo
            }
        }
    }

    public static Creature getRandomMonsterByStage(int level) {
        Stage currentStage;
        if (level <= 3) {
            currentStage = Stage.EARLY;
        } else if (level > 3 && level <= 7) {
            currentStage = Stage.MID;
        } else if (level > 7 && level <= 9) {
            currentStage = Stage.LATE;
        } else {
            currentStage = Stage.BOSS;
        }

        List<Creature> candidates = monstersMap.get(currentStage); //inizializza una lista di mostri tramite la mappa dei mostri prendendo quelli che hanno lo stage corrente
        if (candidates == null || candidates.isEmpty()) {
            return null; // Nessun mostro disponibile per questo stage
        }
        Random randomIndex = new Random();
        Creature copy = candidates.get(randomIndex.nextInt(candidates.size())); //sceglie un int casuale tra 0 e la dimensiuone della lista dei mostri per lo stage corrente
        Creature copyCreature = new Creature(copy); //crea una nuova istanza di Creature a partire dal mostro preso casualmente dalla lista
        return copyCreature;
    }   
    
    public static Item lookupItem(int id) {
        return itemsMap.get(id);
    }

    public static Item getRandomItem() { //restituisce un oggetto casuale dalla mappa degli oggetti
        Random rng = new Random();
        return itemsMap.get(rng.nextInt(items.size()));
    }

    public static int getRandomGoldsAmount() {
        Random rng = new Random();
        return rng.nextInt(50, 151); // Genera un numero casuale tra 50 e 150 (inclusivo)
    }
}
