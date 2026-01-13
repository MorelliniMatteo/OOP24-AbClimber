package it.unibo.abyssclimber.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreatureTest {

    @Test
    void testCopyConstructor() {
        // verifico che clonare un mostro crei un oggetto nuovo
        Creature original = new Creature(Tipo.FIRE, "Goblin");
        original.setHP(100);
        
        Creature copy = new Creature(original);
        
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getHP(), copy.getHP());

        // modifico la copia e verifico che l'originale non cambi
        copy.setHP(50);
        assertEquals(100, original.getHP());
        assertEquals(50, copy.getHP());
    }

    // verifico che promuovere un mostro a elite aumenti le sue stats
    @Test
    void testPromoteToElite() {
        Creature monster = new Creature(Tipo.NATURE, "Orco");
        monster.setHP(100);
        monster.setATK(50);

        assertFalse(monster.getIsElite());

        monster.promoteToElite();

        assertTrue(monster.getIsElite());
        assertEquals(120, monster.getHP());
        assertEquals(60, monster.getATK());
    }

    // verifico che promuovere un mostro a elite due volte non cambi le stats ulteriormente
    @Test
    void testPromoteToEliteIdempotency() {
        Creature monster = new Creature(Tipo.NATURE, "Orco");
        monster.setHP(100);
        
        monster.promoteToElite();
        monster.promoteToElite();

        assertEquals(120, monster.getHP());
    }

    // verifico che se gli hp sono meno di 0, il mostro é morto
    @Test
    void testIsDead() {
        Creature monster = new Creature(Tipo.LIGHTNING, "Slime");
        monster.setHP(10);
        assertFalse(monster.isDead());

        monster.setHP(0);
        assertTrue(monster.isDead());

        monster.setHP(-5);
        assertTrue(monster.isDead());
    }
}