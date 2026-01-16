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

/*
    *Classe che crea una mappa chiamata itemsMap e copia su una lista tramite il file DataLoader tutti gli oggetti presi dal file items.json
    *Questi oggetti della lista vengono poi inseriti nella mappa itemsMap con chiave l'id dell'oggetto e l'oggetto stesso come valore
 */
public class GameCatalog {
    private static Map<Integer, Item> itemsMap = new HashMap<>(); //mapppa che contiene gli oggetti con chiave l'id dell'oggetto
    private static Map<Stage, List<Creature>> monstersMap = new EnumMap<>(Stage.class); //dato che so giá la struttura che deve avere l'hashmap perché uso Enum, uso EnumMap che permette una lettura piú veloce
    
    private static List<Item> items = new ArrayList<>(); //lista che contiene tutti gli oggetti caricati da DataLoader
    private static List<Item> shopItems = new ArrayList<>(); //lista che contiene gli oggetti disponibili nel negozio
    private static List<Item> droppableItems = new ArrayList<>(); //lista che contiene gli oggetti che possono essere droppati dai mostri, esclude quelli del negozio

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


    /*
        *Classe che permette di restituire un mostro casuale in base allo stage attuale del giocatore
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
        Random randomIndex = new Random();
        Creature copy = candidates.get(randomIndex.nextInt(candidates.size())); //sceglie un int casuale tra 0 e la dimensiuone della lista dei mostri per lo stage corrente
        Creature copyCreature = new Creature(copy); //crea una nuova istanza di Creature a partire dal mostro preso casualmente dalla lista
        applyFloorModifier(copyCreature, level); //applica il modificatore di piano al mostro creato
        return copyCreature;
    }   

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

    public static Item getRandomItem() { //restituisce un oggetto casuale dalla lista degli oggetti droppabili
        if(droppableItems.isEmpty()){
            return null;
        }
        Random rng = new Random();
        int index = rng.nextInt(droppableItems.size());
        Item itemFound = droppableItems.remove(index);
        return itemFound;
    }

    public static List<Item> getShopItems() { 
        return shopItems;
    }

    public static int getRandomGoldsAmount() {
        Random rng = new Random();
        return rng.nextInt(125, 201); // Genera un numero casuale tra 100 e 200 (inclusivo)
    }
}
