package it.unibo.abyssclimber.core.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import it.unibo.abyssclimber.core.GameCatalog;
import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import it.unibo.abyssclimber.core.combat.MoveLoader.Move;
import it.unibo.abyssclimber.model.Creature;
import it.unibo.abyssclimber.model.Item;
import it.unibo.abyssclimber.model.Player;
import it.unibo.abyssclimber.ui.combat.CombatController;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class Combat {
    private int turn = 1;
    private boolean playerTurn = true;
    private Player player;
    private Creature monster;
    private CombatController controller;
    private Random random = new Random();
    private final CombatLog combatLog; // = new CombatLog();
    private ArrayList<Move> enemyMoves;

    public Combat(Player creature1, Creature creature2, CombatLog log, CombatController controller) {
        this.player = creature1;
        this.monster = creature2;
        this.loadEnemyMove();
        this.combatLog = log;
        this.controller = controller;
        this.controller.setCombatEnd(false);
    }

    private void loadEnemyMove () {
        TreeSet<MoveLoader.Move> moveSet = new TreeSet<>(Comparator.comparingInt(Move::getCost).thenComparingInt(Move::getId));
        moveSet.add(MoveLoader.moves.get(random.nextInt(8)));
        while ( moveSet.size() < 4) {
            moveSet.add(MoveLoader.moves.get(random.nextInt(8)));
        }
        if ("BOSS".equalsIgnoreCase(monster.getStage())) {
            moveSet.add(MoveLoader.moves.get(8));
        }

        enemyMoves = new ArrayList<>(moveSet);
    }


    private int dmgCalc(MoveLoader.Move attack, Creature attacker, Creature target){
        int miss = 0;
        int dmg = 0;
        double weak = 0;
        weak = ElementUtils.getEffect(attack, target);
        combatLog.logCombat("" + attacker.getName() + " attacks with " + attack.getName() + ".\n", LogType.NORMAL);
        attacker.setSTAM(Math.max(0,attacker.getSTAM() - attack.getCost()));
        controller.updateStats();
        miss = random.nextInt(101);
        if (miss > attack.getAcc()) {
            combatLog.logCombat(attacker.getName() + " missed.\n", LogType.NORMAL);
        } else {
            if (attack.getType() == 1){
                dmg = (int) Math.floor(Math.max(0,(attacker.getATK() - target.getDEF()) * weak * (1 + (attack.getPower()/100))));
            } else {
                dmg = (int) Math.floor(Math.max(0,(attacker.getMATK() - target.getMDEF())* weak * (1 + (attack.getPower()/100))));
            }
            if (random.nextInt(101) <= attacker.getCrit()) {
                combatLog.logCombat(attacker.getName() + " scored a critical hit!\n", LogType.CRITICAL);
                dmg = (int) Math.floor(dmg*attacker.getCritDMG());
            }
            target.setHP(Math.max(0, target.getHP() - dmg ));
            ElementUtils.weakPhrase(weak, combatLog);
            combatLog.logCombat(List.of(
                new BattleText("" + attacker.getName() + " dealt ", LogType.NORMAL),
                new BattleText(String.valueOf(dmg), LogType.DAMAGE),
                new BattleText(" damage.\n", LogType.NORMAL),
                new BattleText("" + target.getName() + " has " + target.getHP() + " HP.\n", LogType.NORMAL)
            ));
        }
        controller.updateStats();
        return dmg;
    }
    
    public void playerTurn(Move move) {
        if ( !playerTurn || player.getHP() <= 0 || monster.getHP() <= 0) return;
        else if (move.getCost() > player.getSTAM()) {
            combatLog.logCombat("Insufficient mana.", LogType.NORMAL);
            controller.renderLog();
            return;
        }

        playerTurn = false; 
        dmgCalc(move, player, monster);
        if (monster.getHP() <= 0) {
            controller.setCombatEnd(true);
            combatLog.logCombat("" + monster.getName() + " died. You win.\n", LogType.NORMAL);
            System.out.println("You win.\n");
            if (!monster.getIsElite()) {
                int gold = GameCatalog.getRandomGoldsAmount();
                combatLog.logCombat("Enemy dropped " + gold + " gold.", LogType.NORMAL);
                player.setGold(player.getGold() + gold);
            } else if (monster.getIsElite()) {
                Item item = GameCatalog.getRandomItem();
                combatLog.logCombat("Enemy dropped the item " + item.getName() + " .", LogType.NORMAL);
                player.addItemToInventory(item);
            }
            controller.renderLog();
            // TODO: HANDLE WIN CONDITION
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(e -> {
                if (monster.getIsElite()) { GameState.get().nextFloor();}
                SceneRouter.goTo(SceneId.ROOM_SELECTION);
            });
            pause.play();
            return;
        }
        
        player.setSTAM(Math.min(player.getMaxSTAM(), player.getSTAM() + player.regSTAM())); 
        controller.renderLog();
        monsterTurn();
        turn++;
    }

    public void monsterTurn() {
        int choice = 0;
        playerTurn = true;

        if (monster.getSTAM() >= monster.getMaxSTAM()) {
            dmgCalc(enemyMoves.getLast(), monster, player);
        } else {
            choice = random.nextInt(enemyMoves.size());
            dmgCalc(enemyMoves.get(choice), monster, player);
        }

        controller.renderLog();
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        if (player.getHP() <= 0) {
            controller.setCombatEnd(true);
            combatLog.logCombat("" + player.getName() + " died. You lose.\n", LogType.NORMAL);
            controller.renderLog();
            // TODO: handle lose condition
            System.out.println("You lose.\n");
            delay.setOnFinished(e -> {
                SceneRouter.goTo(SceneId.GAME_OVER);
            });
            delay.play();
            
        };
        monster.setSTAM(Math.min(monster.getMaxSTAM(), monster.getSTAM() + monster.regSTAM()));

    }
    

    public void fight(Move move) {
        combatLog.clearEvents();
        combatLog.logCombat("Turn " + turn + "\n", LogType.NORMAL);
        if ( playerTurn ) {
            playerTurn(move);
        } else {
            return;
        }
    }
}

