package it.unibo.abyssclimber.core.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import it.unibo.abyssclimber.core.combat.MoveLoader.Move;
import it.unibo.abyssclimber.model.Creature;

//Loads enemy moves randomly from the moves.json . 
//Begins by loading a cost 1 move, this prevents the from softlocking itself for not having a move it can use.
//The final boss loads an extra, unique move.
public final class LoadEnemyMoves {
    private LoadEnemyMoves() {};

    public static ArrayList<CombatMove> load(Creature creature){
        TreeSet<CombatMove> moveSet = new TreeSet<CombatMove>(Comparator.comparingInt(CombatMove::getCost).thenComparingInt(CombatMove::getId));
        Random random = new Random();

        //Check if enemy prefers ATK or MATK moves
        int preferType = creature.getATK() >= creature.getMATK() ? 1 : 2;

        //Filter the first 8 moves by the preffered attack type
        List<Move> filtered = MoveLoader.getMoves().stream().filter(m -> m.getCost() == 1).filter(mv -> mv.getType() == preferType).toList();
        //Empty fallback
        if (filtered.isEmpty()) {
            moveSet.add(MoveLoader.getMoves().getFirst());
        } else {
            moveSet.add(filtered.get(random.nextInt(filtered.size())));
        }

        while ( moveSet.size() < 4 ) {
            moveSet.add(MoveLoader.getMoves().get(random.nextInt(MoveLoader.getMoves().size()-1)));
        }
        if ("BOSS".equalsIgnoreCase(creature.getStage())) {
            moveSet.add(MoveLoader.getMoves().getLast());
        }
        return new ArrayList<CombatMove>(moveSet);
    }
}
