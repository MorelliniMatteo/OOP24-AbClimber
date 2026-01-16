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
     * Usable move for both the player and the monster.
     */
    public static class Move implements CombatMove{
        private String name;
        private int power;
        private int acc;
        private int type;
        private int cost;
        private Tipo element;
        private int id;
        
        @Override
        public String toString() {
            return "Move{name='" + this.getName() + "', power=" + this.getPower() + ", acc=" + this.getAcc() + ", type=" + this.getType() + ", cost=" + this.getCost() + ", type=" + this.getElement() + ", ID=" + this.getId() + "}";
        }

        //Jackson constructor.
        public Move () {

        }
        
        public Move (Tipo el, int ID, BaseMove bMove) {
            this.element = el;
            this.id = ID;
            this.name = element + " " + bMove.name();
            this.power = bMove.power();
            this.acc = bMove.acc();
            this.type = bMove.type();
            this.cost = bMove.cost();
        }

        @Override public String getName() {return name;}
        @Override public int getPower() {return power;}
        @Override public int getAcc() {return acc;}
        @Override public int getType() {return type;}
        @Override public int getCost() {return cost;}
        @Override public Tipo getElement() {return element;}
        @Override public int getId() {return id;}

        public void setName(String name) {this.name = name;}
        public void setPower(int power) {this.power = power;}
        public void setAcc(int acc) {this.acc = acc;}
        public void setType(int type) {this.type = type;}
        public void setCost(int cost) {this.cost = cost;}
        public void setElement(Tipo element) {this.element = element;}
        public void setId(int id) {this.id = id;}

    }

    /**
     * Generates the first 8 moves that have a name composed of {@link Tipo} + {@link BaseMove} name. 
     * @param bml list of {@link BaseMove} loaded from JSON to be upgraded to {@link CombatMove}
     */
    public static void baseMoveAssign(ArrayList<BaseMove> bml){
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
    public static void loadMovesJSON() throws IOException{
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