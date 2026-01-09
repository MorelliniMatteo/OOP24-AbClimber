package it.unibo.abyssclimber.core.combat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.unibo.abyssclimber.model.Tipo;

public class MoveLoader {

    public static ArrayList<BaseMove> baseMoves;
    public static ArrayList<Move> fullMoves;
    public static ArrayList<Move> moves = new ArrayList<>();

    public static class BaseMove {
        private String name;
        private int power;
        private int acc;
        private int type;
        private int cost;

        @Override
        public String toString() {
            return "BaseMove{name='" + name + "', power=" + power + ", acc=" + acc + ", type=" + type + ", cost=" + cost + "}";
        }

        public String getName() {return name;}
        public int getPower() {return power;}
        public int getAcc() {return acc;}
        public int getType() {return type;}
        public int getCost() {return cost;}

        public void setName(String name) {this.name = name;}
        public void setPower(int power) {this.power = power;}
        public void setAcc(int acc) {this.acc = acc;}
        public void setType(int type) {this.type = type;}
        public void setCost(int cost) {this.cost = cost;}
    }
    
    public static class Move extends BaseMove{
        private Tipo element;
        private int id;
        
        @Override
        public String toString() {
            return "Move{name='" + this.getName() + "', power=" + this.getPower() + ", acc=" + this.getAcc() + ", type=" + this.getType() + ", cost=" + this.getCost() + ", type=" + this.getElement() + ", ID=" + this.getId() + "}";
        }
        public Tipo getElement() {return element;}
        public int getId() {return id;}

        public void setElement(Tipo element) {this.element = element;}
        public void setId(int id) {this.id = id;}

    }

    public static void baseMoveAssign(ArrayList<BaseMove> bml){
        int idCounter = 0;
        for (BaseMove bm : bml){
            for (Tipo e : Tipo.values()){
                if (e == Tipo.VOID) continue;
                Move m = new Move();
                m.setName(e+" "+bm.getName());
                m.setPower(bm.getPower());
                m.setAcc(bm.getAcc());
                m.setType(bm.getType());
                m.setCost(bm.getCost());

                m.setElement(e);
                m.setId(idCounter++);

                moves.add(m);
            }
        }
        moves.addAll(fullMoves);
    }
   

    public static void loadMovesJSON() throws IOException{
        InputStream movesFile = MoveLoader.class.getResourceAsStream("/moves.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(movesFile);
        //File movesFile = new File("src\\main\\resources\\moves.json");

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
        //baseMoves.forEach(System.out::println);

    }

    public static void loadMoves() throws IOException{
        loadMovesJSON();
        baseMoveAssign(baseMoves);
        moves.forEach(System.out::println);
        System.out.println(moves.get(8).getPower());
    }
}