package it.unibo.abyssclimber.core.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import it.unibo.abyssclimber.model.Creature;

/**
 * Class that creates a randomly selected list of {@link CombatMove} that an enemy will use in this instance of {@link Combat}.
 * It is guarded against softlocks by giving at least 1 one cost move to the monster.
 * If the monster to call this method is the final boss of the game it also includes a special unique boss {@link CombatMove}.
 */
public final class LoadEnemyMoves {
    private LoadEnemyMoves() {};

    /**
     * The list 
     * @param creature the creature that will make use of these moves.
     * @return a sorted ArrayList of selected moves. With weaker moves on a lower index than stronger moves.
     */
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
