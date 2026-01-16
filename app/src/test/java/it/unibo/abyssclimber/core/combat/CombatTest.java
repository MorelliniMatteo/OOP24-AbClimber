package it.unibo.abyssclimber.core.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.abyssclimber.core.combat.MoveLoader.Move;
import it.unibo.abyssclimber.model.Creature;
import it.unibo.abyssclimber.model.Tipo;
import it.unibo.abyssclimber.ui.combat.CombatController;

@ExtendWith(MockitoExtension.class)
public class CombatTest {
    private Creature monster; 
    private Creature attacker;

    @Mock
    private CombatLog log;

    @Mock
    private CombatController controller;

    @Mock
    private Random rng;

    private Combat combat;
    
    @BeforeEach
    void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        attacker = new Creature(Tipo.FIRE, "Fire");
        monster = new Creature(Tipo.HYDRO, "Hydro");
        attacker.setMaxHP(100);
        attacker.setHP(100);
        monster.setMaxHP(100);
        monster.setHP(100);
        attacker.setATK(20);
        monster.setATK(20);
        attacker.setDEF(10);
        monster.setDEF(10);
        attacker.setCrit(0);
        attacker.setCritDMG(2);
        monster.setCrit(0);
        monster.setCritDMG(0);
        attacker.setSTAM(5);
        monster.setSTAM(5);


        combat = new Combat(attacker, monster, log, controller);

        var randomField = Combat.class.getDeclaredField("random");
        randomField.setAccessible(true);
        randomField.set(combat, rng);
        
    }

    @Test
    void testDamage() throws Exception {
        int dmg;
        Move attack = mock(Move.class);
        when(attack.getName()).thenReturn("Test Strike");
        when(attack.getCost()).thenReturn(0);
        when(attack.getAcc()).thenReturn(100); // Non miss.
        when(attack.getType()).thenReturn(1); // Physical attack using ATK.
        when(attack.getPower()).thenReturn(50); // Move power.

        when(rng.nextInt(101))
            .thenReturn(10) // accuracy roll
            .thenReturn(99); // not crit roll

        try (var mocked = mockStatic(ElementUtils.class)) {
            mocked.when(() -> ElementUtils.getEffect(attack, monster)).thenReturn(0.75);
            dmg = combatTestableDmg(combat, attack, attacker, monster);
        }

        assertEquals(11, dmg);
        assertEquals(89, monster.getHP());

        verify(log, atLeastOnce()).logCombat(anyString(), any());
        verify(controller, atLeastOnce()).updateStats();
    }

    @Test
    void testMiss() throws Exception {
        int dmg;
        Move attack = mock(Move.class);
        when(attack.getName()).thenReturn("Test Strike");
        when(attack.getCost()).thenReturn(0);
        when(attack.getAcc()).thenReturn(0); // Non miss.
        
        when(rng.nextInt(101))
            .thenReturn(10); // miss roll

        try (var mocked = mockStatic(ElementUtils.class)) {
            mocked.when(() -> ElementUtils.getEffect(attack, monster)).thenReturn(0.75);
            dmg = combatTestableDmg(combat, attack, attacker, monster);
        }

        assertEquals(0, dmg);
        assertEquals(100, monster.getHP());

        verify(log, atLeastOnce()).logCombat(anyString(), any());
        verify(controller, atLeastOnce()).updateStats();
    }

    @Test
    void testCrit() throws Exception {
        int dmg;
        Move attack = mock(Move.class);
        when(attack.getName()).thenReturn("Test Strike");
        when(attack.getCost()).thenReturn(0);
        when(attack.getAcc()).thenReturn(100); // Non miss.
        when(attack.getType()).thenReturn(1); // Physical attack using ATK.
        when(attack.getPower()).thenReturn(50); // Move power.
        attacker.setCrit(100);

        when(rng.nextInt(101))
            .thenReturn(10) // accuracy roll
            .thenReturn(99); // crit roll

        try (var mocked = mockStatic(ElementUtils.class)) {
            mocked.when(() -> ElementUtils.getEffect(attack, monster)).thenReturn(0.75);
            dmg = combatTestableDmg(combat, attack, attacker, monster);
        }

        assertEquals(22, dmg);
        assertEquals(78, monster.getHP());

        verify(log, atLeastOnce()).logCombat(anyString(), any());
        verify(controller, atLeastOnce()).updateStats();
    }

    @Test
    void testWeak() {
        double weak = 0;
        Move attack = mock(Move.class);
        when(attack.getElement()).thenReturn(Tipo.FIRE);
        weak = ElementUtils.getEffect(attack, monster);
        assertEquals(0.75, weak);
    }

    private int combatTestableDmg(Combat combat, Move mv, Creature attacker, Creature target) throws Exception {
        var method = Combat.class.getDeclaredMethod("dmgCalc", Move.class, Creature.class, Creature.class);
        method.setAccessible(true);
        return (int) method.invoke(combat, mv, attacker, target);
    }

}
