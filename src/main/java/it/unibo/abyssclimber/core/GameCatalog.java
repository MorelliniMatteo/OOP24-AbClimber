package it.unibo.abyssclimber.core;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Collections;

import it.unibo.abyssclimber.model.Creature;
import it.unibo.abyssclimber.model.Item;
import it.unibo.abyssclimber.model.Stage;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Gestisce il catalogo centrale di tutte le risorse di gioco (Registry Pattern).
 * Questa classe statica agisce come unica fonte di verità peroggetti e mostri. 
 * Si occupa di inizializzare le risorse tramite {@link DataLoader},
 * gestire la generazione procedurale dei nemici in base al livello e distribuire
 * gli oggetti tra il negozio e il pool di drop.
 */
public class GameCatalog {
    private static Map<Integer, Item> itemsMap = new HashMap<>(); //mapppa che contiene gli oggetti con chiave l'id dell'oggetto
    private static Map<Stage, List<Creature>> monstersMap = new EnumMap<>(Stage.class); //dato che so giá la struttura che deve avere l'hashmap perché uso Enum, uso EnumMap che permette una lettura piú veloce
    
    private static List<Item> items = new ArrayList<>(); //lista che contiene tutti gli oggetti caricati da DataLoader
    private static List<Item> shopItems = new ArrayList<>(); //lista che contiene gli oggetti disponibili nel negozio
    private static List<Item> droppableItems = new ArrayList<>(); //lista che contiene gli oggetti che possono essere droppati dai mostri, esclude quelli del negozio

    private static final Random random = new Random();

    /**
     * Inizializza il catalogo caricando i dati dai file JSON e preparandoli per il gioco.
     * Questo metodo esegue le seguenti operazioni:
     * Svuota le cache precedenti.
     * Carica gli oggetti tramite {@link DataLoader} e popola la mappa per ID.
     * Mescola casualmente gli oggetti e ne assegna i primi 4 al negozio, il resto ai drop.
     * Carica i mostri e li organizza nella {@code monstersMap} in base al loro {@link Stage}.
     * @throws Exception se il caricamento dei dati fallisce (es. file mancanti o JSON malformato).
     */
    public static void initialize() throws Exception {
        itemsMap.clear();
        monstersMap.clear();
        items.clear();
        shopItems.clear();
        droppableItems.clear();
        
        DataLoader dataLoader = new DataLoader();
        items = dataLoader.loadItems();
        for (Item item : items) {
            itemsMap.put(item.getID(), item); //mette dentro itemsMap l'id che prende tramite il metodo getId e l'oggetto stesso
        }

        List<Item> shuffleItems = new ArrayList<>(items); //creo una lista temporanea per mischiare gli oggetti
        Collections.shuffle(shuffleItems);
        int shopSize = 4;
        for(int i = 0; i < shuffleItems.size(); i++){ //il for prende i primi 4 oggetti mischiati e li mette nella lista del negozio, gli altri nella lista degli oggetti droppabili
            if(i < shopSize){
                shopItems.add(shuffleItems.get(i));
            } else {
                droppableItems.add(shuffleItems.get(i));
            }
        }

    /**
     * UTILIZZO DI LAMBDA EXPRESSION:
     * Ordina la lista degli oggetti del negozio in base al prezzo crescente (ASC).
     * Invece di istanziare una classe anonima di Comparator, si passa una funzione
     * che confronta due oggetti Item (item1, item2) basandosi sul valore di getPrice().
     */
        shopItems.sort((item1, item2) -> Integer.compare(item1.getPrice(), item2.getPrice()));

        List<Creature> monsters = dataLoader.loadMonsters();
        for (Stage stage : Stage.values()){ //ad ogni ID che in questo caso corrisponde allo stage, creo una lista vuota che contiene i mostri con quel determinato stage
            monstersMap.put(stage, new ArrayList<>());
        }
        for (Creature monster : monsters){ //cerca i mostri nella lista dei mostri che hanno uno stage definito e li inserisce nella mappa sotto lo stage corrispondente
            if (monster.getStage() != null){
                Stage stageEnum = Stage.valueOf(monster.getStage()); //valueOf converte la stringa che rappresenta lo stage del mostro nella lista e lo converte in un elemento dell'enum Stage.
                monstersMap.get(stageEnum).add(monster);             //é esattamente Stage. che determina in cosa deve essere trasformata la stringa o qualsiasi cosa ci sta dopo
            }
        }
    }


   /**
     * Restituisce un mostro casuale appropriato per il livello attuale del giocatore.
     * Il metodo determina lo {@link Stage} corrente (EARLY, MID, LATE, BOSS) in base al numero del piano
     * e seleziona un mostro casuale dalla lista corrispondente.
     * Successivamente, applica un modificatore di statistiche (scaling) tramite {@link #applyFloorModifier}.
     * @param level il numero del piano attuale (es. 1, 5, 10).
     * @return una NUOVA istanza di {@link Creature} con statistiche scalate, o {@code null} se non ci sono mostri per lo stage.
     */
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
        Creature copy = candidates.get(random.nextInt(candidates.size())); //sceglie un int casuale tra 0 e la dimensiuone della lista dei mostri per lo stage corrente
        Creature copyCreature = new Creature(copy); //crea una nuova istanza di Creature a partire dal mostro preso casualmente dalla lista
        applyFloorModifier(copyCreature, level); //applica il modificatore di piano al mostro creato
        return copyCreature;
    }   

    /**
     * Applica un algoritmo di scaling matematico alle statistiche del mostro.
     * Formula: {@code stats = baseStats * (1 + (level - 1) * 0.1)}
     * Questo garantisce un aumento della difficoltà del 10% per ogni piano.
     * @param monster la creatura da potenziare.
     * @param level il livello attuale del dungeon.
     */
    private static void applyFloorModifier(Creature monster, int level){
        double modifier = 1 + (level - 1) * 0.1; // Aumento del 10% per ogni piano oltre il primo
        monster.setMaxHP((int)(monster.getMaxHP() * modifier));
        monster.setHP(monster.getMaxHP());
        monster.setATK((int)(monster.getATK() * modifier));
        monster.setMATK((int)(monster.getMATK() * modifier));
        monster.setDEF((int)(monster.getDEF() * modifier));
        monster.setMDEF((int)(monster.getMDEF() * modifier));
    }
    
    public static Item lookupItem(int id) {
        return itemsMap.get(id);
    }

    /**
     * Estrae e RIMUOVE un oggetto casuale dalla lista dei drop disponibili.
     * La rimozione garantisce che il giocatore non trovi duplicati dello stesso oggetto
     * durante la stessa run.
     * @return un oggetto {@link Item} casuale, o {@code null} se la lista è vuota.
     */
    public static Item getRandomItem() { //restituisce un oggetto casuale dalla lista degli oggetti droppabili
        if(droppableItems.isEmpty()){
            return null;
        }
        int index = random.nextInt(droppableItems.size());
        Item itemFound = droppableItems.remove(index);
        return itemFound;
    }

    public static List<Item> getShopItems() { 
        return shopItems;
    }

    public static int getRandomGoldsAmount() {
        return random.nextInt(125, 201); // genera un numero casuale tra 100 e 200 (inclusivo)
    }
}
