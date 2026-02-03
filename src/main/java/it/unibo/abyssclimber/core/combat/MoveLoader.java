package it.unibo.abyssclimber.core.combat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.unibo.abyssclimber.model.Tipo;

/**
 * Class responsible for loading all moves from moves.json
 */
public class MoveLoader {

    private static ArrayList<BaseMove> baseMoves;
    private static ArrayList<Move> fullMoves;
    private static ArrayList<Move> moves = new ArrayList<>();
    
    /**
     * Generates the first 8 moves that have a name composed of {@link Tipo} + {@link BaseMove} name. 
     * @param bml list of {@link BaseMove} loaded from JSON to be upgraded to {@link CombatMove}
     */
    private static void baseMoveAssign(ArrayList<BaseMove> bml){
        int idCounter = 0;
        for (BaseMove bm : bml){
            for (Tipo e : Tipo.values()){
                if (e == Tipo.VOID) continue;
                Move m = new Move(e, idCounter++, bm);

                moves.add(m);
            }
        }
        moves.addAll(fullMoves);
    }
   
    /**
     * Method that reads the {@link BaseMove} and {@link CombatMove} from moves.json
     * @throws IOException if the json file is missing/moved
     */
    private static void loadMovesJSON() throws IOException{
        InputStream movesFile = MoveLoader.class.getResourceAsStream("/liste/moves.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(movesFile);

        List<BaseMove> baseMovesList = mapper.convertValue(
            root.get("BaseMoves"),
            new TypeReference<List<BaseMove>>(){}
        );

        baseMoves = new ArrayList<>(baseMovesList);

        List<Move> fullMovesList = mapper.convertValue(
            root.get("Moves"),
            new TypeReference<List<Move>>(){}
        );

        fullMoves = new ArrayList<>(fullMovesList);

    }

    /**
     * Method that starts loading the list of moves from moves.json and the upgrade from a {@link BaseMove} to a {@link CombatMove}
     * @throws IOException originating in loadMovesJSON and is passed to the caller for handling.
     */
    public static void loadMoves() throws IOException{
        loadMovesJSON();
        baseMoveAssign(baseMoves);
    }

    public static ArrayList<Move> getMoves() {
        return moves;
    }
}