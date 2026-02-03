package it.unibo.abyssclimber.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    // verifico che il player nasca con le stats di base corrette
    @Test
    void testPlayerCreationAndBaseStats() {
        Classe testClass = Classe.CAVALIERE;
        Player player = new Player("Hero", Tipo.FIRE, testClass);

        assertEquals("Hero", player.getName());
        int expectedHP = 120 + testClass.getcMaxHP();
        assertEquals(expectedHP, player.getHP(), "Hp should be (120 + Bonus Classe)");
    }

    // verifico se l'applicazione della classe somma le statistiche correttamente
    @Test
    void testApplicaClasse() {
        Classe classeSoldato = Classe.SOLDATO;
        Player player = new Player("Hero", Tipo.FIRE, Classe.SOLDATO);

        int expectedHP = 120 + classeSoldato.getcMaxHP();
        int expectedATK = 15 + classeSoldato.getcATK();

        assertEquals(expectedHP, player.getHP(), "Hp not correct after Soldier creation");
        assertEquals(expectedATK, player.getATK(), "Atk not correct after Soldier creation");
    }

    // verifico se aggiungere un oggetto all'inventario funziona e applica le stats
    @Test
    void testApplyItemStats() {
        Player player = new Player("Hero", Tipo.FIRE, Classe.MAGO);
        int initialDef = player.getDEF();
        int initialMDef = player.getMDEF();
        
        // Item: name, maxHP, HP, ATK, MATK, DEF, MDEF, discovered, id, effect, price
        Item elmo = new Item(1, "Elmo Test", 0, 0, 0, 0, 10, 5, "None", true, 100);
        player.applyItemStats(elmo);

        assertEquals(initialDef + 10, player.getDEF(), "The DEF should increase by 10");
        assertEquals(initialMDef + 5, player.getMDEF(), "MDEF (5) + Item (5) should be 10");
    }

    // verifico se il metodo setGold funziona come addGold
    @Test
    void testGoldHandling() {
        Player player = new Player("Richie", Tipo.HYDRO, Classe.MAGO);
        
        player.setGold(100);
        assertEquals(100, player.getGold());

        player.setGold(player.getGold() + 50);
        assertEquals(150, player.getGold());

        player.setGold(player.getGold() - 20);
        assertEquals(130, player.getGold());
    }

    // verifico che gli item con paramentro HP e basta curino il player
    @Test
    void testPotionHealing() {
        Player p = new Player("Healer", Tipo.HYDRO, Classe.MAGO);
        p.setMaxHP(100);
        p.setHP(10);

        Item potion = new Item(99, "Small Potion", 0, 50, 0, 0, 0, 0, "None", true, 50);

        p.applyItemStats(potion);

        assertEquals(60, p.getHP(), "The potion should bring HP to 60");
    }

    // verifico che gli item che curano non facciano superare il MaxHP
    @Test
    void testHealingOverCap() {
        // verifico che non si possa superare il MaxHP
        Player p = new Player("Hero", Tipo.FIRE, Classe.CAVALIERE);
        p.setMaxHP(100);
        p.setHP(90);

        Item potion = new Item(97, "Big Potion", 0, 50, 0, 0, 0, 0, "None", true, 100);
        
        p.applyItemStats(potion);

        assertEquals(100, p.getHP(), "The heal should not exceed MaxHP of 100");
    }

    // verifico che equipaggiare un oggetto che aumenta MaxHP curi anche il giocatore della stessa quantità
    @Test
    void testMaxHPIncreaseHealsPlayer() {
        Player p = new Player("Tank", Tipo.NATURE, Classe.SOLDATO);
        p.setMaxHP(100);
        p.setHP(100);
        
        Item armor = new Item(98, "Heavy Armor", 50, 0, 0, 0, 5, 0, "None", true, 200);
        
        p.applyItemStats(armor);

        assertEquals(150, p.getMaxHP(), "MaxHP should increase to 150");
        assertEquals(150, p.getHP(), "Hp should also increase to 150");
    }
}