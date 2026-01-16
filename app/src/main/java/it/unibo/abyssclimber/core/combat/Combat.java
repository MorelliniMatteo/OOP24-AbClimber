package it.unibo.abyssclimber.core.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.abyssclimber.core.GameCatalog;
import it.unibo.abyssclimber.core.RoomContext;
import it.unibo.abyssclimber.core.RoomOption;
import it.unibo.abyssclimber.core.RoomType;


import it.unibo.abyssclimber.model.Creature;
import it.unibo.abyssclimber.model.GameEntity;
import it.unibo.abyssclimber.model.Item;
import it.unibo.abyssclimber.model.Player;

//Main combat method. Ineracts with CombatController.
public class Combat {
    private int turn = 1;
    private boolean playerTurn = true;
    private Player player;
    @SuppressWarnings("unused")
    private Creature creature;
    private Creature monster;
    private CombatPresenter controller;
    private Random random = new Random();
    private final CombatLog combatLog;
    private ArrayList<CombatMove> enemyMoves;

    //Constructor for a new combat. Also calls the method to randomly assign enemy moves.
    public Combat(Player creature1, Creature creature2, CombatLog log, CombatPresenter controller) {
        this.player = creature1;
        this.monster = creature2;
        this.enemyMoves = new ArrayList<>(LoadEnemyMoves.load(creature2));
        this.combatLog = log;
        this.controller = controller;
        this.controller.setCombatEnd(false);
    }

    //Calculates damage, creates logs to print (conditional: critical hit, miss), calculates stamina remaning after cost.
    private int dmgCalc(CombatMove attack, GameEntity attacker, GameEntity target){
        int dmg = 0;
        double weak = 0;
        weak = ElementUtils.getEffect(attack, target);
        combatLog.logCombat("" + attacker.getName() + " attacks with " + attack.getName() + ".\n", LogType.NORMAL);
        attacker.setSTAM(Math.max(0,attacker.getSTAM() - attack.getCost()));
        controller.updateStats();
        if (isMiss(attack, random)) {
            combatLog.logCombat(attacker.getName() + " missed.\n", LogType.NORMAL);
            controller.updateStats();
            return 0;
        }
        if (attack.getType() == 1){
            dmg = (int) Math.floor(Math.max(0,(attacker.getATK() - target.getDEF()) * weak * (1 + (attack.getPower()/100.0))));
        } else {
            dmg = (int) Math.floor(Math.max(0,(attacker.getMATK() - target.getMDEF())* weak * (1 + (attack.getPower()/100.0))));
        }
        dmg = applyCrit(dmg, attacker, attack, random);
        target.setHP(Math.max(0, target.getHP() - dmg ));
        combatLog.logCombat(ElementUtils.weakPhrase(weak), LogType.NORMAL);
        combatLog.logCombat(List.of(
            new BattleText("" + attacker.getName() + " dealt ", LogType.NORMAL),
            new BattleText(String.valueOf(dmg), LogType.DAMAGE),
            new BattleText(" damage.\n", LogType.NORMAL),
            new BattleText("" + target.getName() + " has " + target.getHP() + " HP.\n", LogType.NORMAL)
        ));
    
        controller.updateStats();
        return dmg;
    }
    
    private boolean isMiss(CombatMove move, Random rand) {
        return rand.nextInt(101) > move.getAcc();
    }

    private int applyCrit(int damage, GameEntity attacker, CombatMove move, Random rand) {
        if (rand.nextInt(101) <= attacker.getCrit()) {
            combatLog.logCombat(attacker.getName() + " scored a critical hit!\n", LogType.CRITICAL);
            return (int) Math.floor(damage * attacker.getCritDMG());
        } 
        return damage;
    }
    //Player's turn. At the end calls the monster's turn.
    //Assigns dropped gold/items.
    private void playerTurn(CombatMove move) {
        if ( !playerTurn || player.getHP() <= 0 || monster.getHP() <= 0) return;
        else if (move.getCost() > player.getSTAM()) {
            combatLog.logCombat("Insufficient mana.\n", LogType.NORMAL);
            controller.renderLog();
            return;
        }

        playerTurn = false; 
        dmgCalc(move, player, monster);
        if (monster.isDead()) {
            controller.setCombatEnd(true);
            combatLog.logCombat("" + monster.getName() + " died. You win.\n", LogType.NORMAL);
            boolean finalBossFight = isFinalBossFight();
            enemyDrop();
            controller.renderLog();
            controller.onCombatEnd(finalBossFight, monster.getIsElite(), true);
            return;
        }
        
        player.setSTAM(Math.min(player.getMaxSTAM(), player.getSTAM() + player.getRegSTAM())); 
        controller.renderLog();
        monsterTurn();
        turn++;
    }

    //Enemy turn.
    private void monsterTurn() {
        playerTurn = true;

        if (monster.getSTAM() >= monster.getMaxSTAM()) {
            dmgCalc(enemyMoves.getLast(), monster, player);
        } else {
            List<CombatMove> usableMoves = enemyMoves.stream().filter(mv -> monster.getSTAM() >= mv.getCost()).toList();
            if ( usableMoves.isEmpty()) {
                dmgCalc(enemyMoves.getFirst(), monster, player);
            } else {
                CombatMove choice = usableMoves.get(random.nextInt(usableMoves.size()));
                dmgCalc(choice, monster, player);
            }
        }
        controller.renderLog();

        if (player.getHP() <= 0) {
            controller.setCombatEnd(true);
            combatLog.logCombat("" + player.getName() + " died. You lose.\n", LogType.NORMAL);
            controller.renderLog();
            System.out.println("You lose.\n");
            controller.onCombatEnd(false, false, false);
            return;
            
        };
        monster.setSTAM(Math.min(monster.getMaxSTAM(), monster.getSTAM() + monster.getRegSTAM()));

    }
    
    //Clears logs for a new turn then calls the player turn, only public method needed.
    public void fight(CombatMove move) {
        controller.onTurnStart(turn);
        if ( playerTurn ) {
            playerTurn(move);
        } else {
            return;
        }
    }

    private boolean isFinalBossFight() {
        RoomOption option = RoomContext.get().getLastChosen();
        return option != null && option.type() == RoomType.FINAL_BOSS;
    }

    private void enemyDrop() {
        if (isFinalBossFight()) {
            return;
        }
        
        if (!monster.getIsElite()) {
            int gold = GameCatalog.getRandomGoldsAmount();
            combatLog.logCombat("Enemy dropped " + gold + " gold.\n", LogType.NORMAL);
            player.setGold(player.getGold() + gold);
        } else if (monster.getIsElite()) {
            Item item = GameCatalog.getRandomItem();
            combatLog.logCombat("Enemy dropped the item " + item.getName() + ".\n", LogType.NORMAL);
            player.addItemToInventory(item);
        }  
    }
}
