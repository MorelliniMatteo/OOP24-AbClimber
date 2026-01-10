package it.unibo.abyssclimber.core.combat;

import java.util.Random;
import java.util.Scanner;

import it.unibo.abyssclimber.model.Creature;
import java.lang.Thread;

public class Combat {
    private Creature player;
    private Creature monster;
    private Random random = new Random();

    public Combat(Creature creature1, Creature creature2) {
        this.player = creature1;
        this.monster = creature2;    
    }

    private int dmgCalc(MoveLoader.Move attack, Creature attacker, Creature target, double weak){
        int dmg = 0;
        if (attack.getType() == 1){
            dmg = (int) Math.floor(Math.max(0,(attacker.getATK() - target.getDEF()))*weak);
        } else {
            dmg = (int) Math.floor(Math.max(0,(attacker.getMATK() - target.getMDEF()))*weak);
        }
        if (random.nextInt(101) <= attacker.getCrit()) {
            System.out.println(attacker.getName() + " scored a crit!");
            dmg = (int) Math.floor(dmg*attacker.getCritDMG());
        }
        target.setHP(target.getHP() - dmg );
        return dmg;
    }
    
    public void fight() throws InterruptedException {
        int miss = 0;

        int turn = 0;
        int choice = 0;
        int dmg = 0;
        Scanner scanner;
        double weak = 0;
        MoveLoader.Move usedMove = null;

        scanner = new Scanner(System.in);

    while(player.getHP()>0 && monster.getHP()>0){

        System.out.println("");
        System.out.println("Turn "+turn);

        if(turn % 2 == 0 ) {
            //Player turn, using a text input for testing purposes. TODO: Finish GUI actions
            System.out.println("Player HP is "+player.getHP()+"! Monster HP is "+monster.getHP()+"!\n"+"Input a number to choose an action: 1 Attack; 2 Nothing, 3 Quit. \nConfirm with Enter.");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Input move ID");
                    choice = scanner.nextInt();
                    System.out.println("You attack. Move ID " + choice+" Move used:"+ MoveLoader.moves.get(choice).getName() +".");
                    usedMove = MoveLoader.moves.get(choice);
                    weak = ElementUtils.getEffect(usedMove, monster);
                    miss = random.nextInt(101);
                    if(miss > usedMove.getAcc()){
                        System.out.println("You missed! " + "acc was: " + usedMove.getAcc() + " you rolled: " + miss + ".");
                    } else {
                        System.out.println("You hit! " + "acc was: " + usedMove.getAcc() + " you rolled: " + miss + ".");
                        dmg = dmgCalc(usedMove, player, monster, weak);
                        ElementUtils.weakPhrase(weak);
                        System.out.println("You dealt "+dmg+" damage!\n");
                    }
                    break;
                
                case 3:
                    System.out.println("quitting");
                    
                    Thread.sleep(2000);
                    System.exit(0);

                    break;

                default:
                    System.out.println("You do nothing.");
                    break;
            }
        }
        else {
            //Monster turn
            System.out.println("The " + monster.getName() + " attacks!");
            weak = ElementUtils.getEffect(monster, player);
            choice = random.nextInt(9);
            usedMove = MoveLoader.moves.get(choice);
            dmg = dmgCalc(usedMove, monster, player, weak);
            System.out.println("The monster used " + usedMove.getName() + ".");
            ElementUtils.weakPhrase(weak);
            System.out.println("The monster dealt "+dmg+" damage!\n");
            Thread.sleep(1000);

        }
        turn++;
    }
    
    scanner.close();
    if(player.getHP()<=0){
        System.out.println("The player died! \n You lose!");
    }
    else {
        System.out.println("The monster died! You win!");
    }

        System.exit(0);
    }
}

