package it.unibo.abyssclimber.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testPlayerCreationAndBaseStats() {
        // verifico che il player nasca con le stats di base corrette
        Classe testClass = Classe.CAVALIERE;
        Player player = new Player("Hero", Tipo.FIRE, testClass);

        assertEquals("Hero", player.getName());
        assertEquals(120, player.getHP()); // Valore base da costruttore Player
        assertEquals(0, player.getGold()); // Gold iniziale
    }

    // verifico se l'applicazione della classe somma le statistiche correttamente
    @Test
    void testApplicaClasse() {
        Player player = new Player("Hero", Tipo.FIRE, Classe.SOLDATO);
        
        player.applicaClasse(Classe.SOLDATO);

        assertEquals(420, player.getHP(), "HP dovrebbe essere 120 (base) + 300 (Soldato)");
        assertEquals(30, player.getATK(), "ATK dovrebbe essere 15 (base) + 15 (Soldato)");
    }

    // verifico se aggiungere un oggetto all'inventario funziona e applica le stats
    @Test
    void testApplyItemStats() {
        Player player = new Player("Hero", Tipo.FIRE, Classe.MAGO);
        int initialDef = player.getDEF();

        // oggetto finto per il test
        Item elmo = new Item("Elmo Test", 0, 0, 0, 10, 5, true, 1, "None", 100);
        
        player.applyItemStats(elmo);

        assertEquals(initialDef + 10, player.getDEF(), "La difesa dovrebbe aumentare di 10");
        assertEquals(player.getMDEF(), 10, "MDEF base (5) + Item (5) dovrebbe fare 10");
    }

    // verifico se il metodo setGold funziona come addGold
    @Test
    void testGoldHandling() {
        Player player = new Player("Richie", Tipo.HYDRO, Classe.MAGO);
        
        player.setGold(100);
        assertEquals(100, player.getGold());

        player.setGold(50);
        assertEquals(150, player.getGold());

        player.setGold(-20);
        assertEquals(130, player.getGold());
    }
}