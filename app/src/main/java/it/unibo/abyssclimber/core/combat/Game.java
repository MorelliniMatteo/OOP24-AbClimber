package it.unibo.abyssclimber.core.combat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import it.unibo.abyssclimber.model.Creature;
import it.unibo.abyssclimber.model.Tipo;

import java.nio.file.Files;

public class Game {
    public static void main(String[] args) throws IOException{
        Creature player = new Creature(Tipo.VOID, "Player");
        Creature monster = new Creature(Tipo.FIRE, "Monster");

        // File debugging, TODO: remove
        System.out.println("cwd = " + System.getProperty("user.dir"));
Path p = Paths.get("src", "main", "resources", "moves.json").toAbsolutePath();
System.out.println("trying path = " + p);
System.out.println("exists = " + Files.exists(p));
//
        Combat combat = new Combat(player, monster);
        MoveLoader.loadMoves();
        System.out.println("Combat Start!\n");
        try {
            combat.fight();
        } catch (InterruptedException e) {
            System.out.println("Whoops!");
            System.exit(1);
        }


    }
}

