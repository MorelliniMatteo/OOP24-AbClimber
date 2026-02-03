package it.unibo.abyssclimber.core.combat;

import it.unibo.abyssclimber.model.Tipo;

/**
 * Usable move for both the player and the monster.
 */
public class Move implements CombatMove{
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