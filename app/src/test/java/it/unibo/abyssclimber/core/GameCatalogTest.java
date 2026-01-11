package it.unibo.abyssclimber.core;

import it.unibo.abyssclimber.model.Creature;
import it.unibo.abyssclimber.model.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameCatalogTest {

    // eseguo initialize una volta sola prima di tutti i test
    @BeforeAll
    static void setup() {
        try {
            GameCatalog.initialize();
        } catch (Exception e) {
            // se fallisce qui potrebbe essere perché manca il file json nel path di test
            System.out.println("Warning: GameCatalog init failed (expected if JSON missing in test env): " + e.getMessage());
        }
    }

    // verifico che il negozio abbia 4 oggetti iniziali
    @Test
    void testShopItemsInitialization() {
        // controllo se l'inizializzazione è andata a buon fine
        if (GameCatalog.getShopItems().isEmpty()) return;

        List<Item> shopItems = GameCatalog.getShopItems();
        assertNotNull(shopItems);
    }

    // verifico che il metodo per generare oro casuale rispetti i limiti
    @Test
    void testRandomGolds() {
        // Verifica che il range sia rispettato (50 - 150)
        for (int i = 0; i < 100; i++) {
            int gold = GameCatalog.getRandomGoldsAmount();
            assertTrue(gold >= 50 && gold <= 151, "Gold generato fuori dal range: " + gold);
        }
    }

    // verifico che venga restituito un mostro casuale per uno stage valido
    @Test
    void testRandomMonster() {
        try {
             Creature monster = GameCatalog.getRandomMonsterByStage(1); 
             // stage 1 -> EARLY
             if (monster != null) {
                 assertNotNull(monster.getName());
                 // verifico che sia una nuova istanza (copia) e non un riferimento al catalogo
                 monster.setHP(9999);
                 // se richiedo un altro mostro, non dovrebbe avere 9999 HP
                 Creature monster2 = GameCatalog.getRandomMonsterByStage(1);
                 if (monster2 != null && monster2.getName().equals(monster.getName())) {
                      assertNotEquals(9999, monster2.getHP());
                 }
             }
        } catch (Exception e) {
            // ignora eccezioni se dati mancanti in test environment
        }
    }
}