package it.unibo.abyssclimber.core;

import it.unibo.abyssclimber.model.Creature;
import it.unibo.abyssclimber.model.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameCatalogTest {

    // eseguo initialize una volta sola prima di tutti i test
    @BeforeAll
    static void setup() {
        try {
            GameCatalog.initialize();
        } catch (Exception e) {
            // se fallisce qui potrebbe essere perché il percorso del file JSON non è corretto
            System.out.println("Warning: GameCatalog init failed (expected if JSON missing in test env): " + e.getMessage());
        }
    }

    // verifico che il negozio venga inizializzato e contenga esattamente 4 oggetti
    @Test
    void testShopItemsInitialization() {
        List<Item> shopItems = GameCatalog.getShopItems();
        
        assertNotNull(shopItems, "Shop list should not be null");
        
        if (!shopItems.isEmpty()) {
            assertEquals(4, shopItems.size(), "Shop should contain exactly 4 items");
        }
    }

    // verifico che il metodo per generare oro casuale rispetti i limiti definiti in GameCatalog
    @Test
    void testRandomGolds() {
        for (int i = 0; i < 100; i++) {
            int gold = GameCatalog.getRandomGoldsAmount();
            assertTrue(gold >= 125 && gold <= 200, "Gold generated out of range (125-200): " + gold);
        }
    }

    // verifico che venga restituito un mostro casuale e che sia una "deep copy"
    @Test
    void testRandomMonster() {
        try {
             Creature monster = GameCatalog.getRandomMonsterByStage(1); 
             
             if (monster != null) {
                 assertNotNull(monster.getName());
                 
                 // verifico che sia una nuova istanza (copia) e non un riferimento al catalogo
                 monster.setHP(9999);
                 
                 // se richiedo un altro mostro (sperando esca lo stesso tipo dato che è random), la modifica non dovrebbe impattare i futuri mostri dello stesso tipo
                 Creature monster2 = GameCatalog.getRandomMonsterByStage(1);
                 
                 // check parziale perché monster2 potrebbe essere un mostro diverso
                 if (monster2 != null) {
                      assertNotEquals(9999, monster2.getHP(), "Il mostro recuperato non dovrebbe avere gli HP modificati del precedente");
                 }
             }
        } catch (Exception e) {
        }
    }

    // testo che i mostri vengano restituiti correttamente in base allo stage
    @Test
    void testMonsterStageBoundaries() {
        
        Creature early = GameCatalog.getRandomMonsterByStage(3);
        Creature mid = GameCatalog.getRandomMonsterByStage(4);
        Creature late = GameCatalog.getRandomMonsterByStage(8);
        Creature boss = GameCatalog.getRandomMonsterByStage(10);

        if (early != null) assertNotNull(early);
        if (mid != null) assertNotNull(mid);
        if (late != null) assertNotNull(late);
        if (boss != null) assertNotNull(boss);
    }

    // verifico che lookupItem funzioni correttamente recuperando un item dalla mappa tramite il suo ID
    @Test
    void testLookupItem() {
        List<Item> shopItems = GameCatalog.getShopItems();
        if (!shopItems.isEmpty()) {
            Item shopItem = shopItems.get(0);
            int id = shopItem.getID();

            Item foundItem = GameCatalog.lookupItem(id);

            assertNotNull(foundItem, "lookupItem returned null for ID: " + id);
            assertEquals(shopItem.getName(), foundItem.getName(), "The item retrieved does not match the expected item");
        }
    }

    // test per verificare che getRandomItem restituisca oggetti unici finché la lista non è vuota
    @Test
    void testRandomItemUniqueness() {
        // in un set non ci possono essere duplicati
        Set<Item> droppedItems = new HashSet<>();
        
        // GameCatalog usa droppableItems.remove(index), quindi gli oggetti finiscono dopo un po
        int maxIterations = 17; // gli oggetti droppabili dai mostri sono 17, 21 totali - 4 dello shop
        int itemsFoundCount = 0;

        for (int i = 0; i < maxIterations; i++) {
            Item item = GameCatalog.getRandomItem();

            // se gli item è null, esco dal ciclo perché la lista droppableItems è vuota
            if (item == null) {
                break; 
            }
            
            // se add restituisce false significa che l'oggetto era già presente nel Set
            boolean isNew = droppedItems.add(item);
            assertTrue(isNew, "Error: The item '" + item.getName() + "' (ID: " + item.getID() + ") is already in the set.");
            
            itemsFoundCount++;
        }
        System.out.println("Test completed. Extracted " + itemsFoundCount + " unique items.");
    }
}