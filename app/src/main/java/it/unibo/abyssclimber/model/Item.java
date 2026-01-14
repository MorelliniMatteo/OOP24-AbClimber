package it.unibo.abyssclimber.model;
import com.fasterxml.jackson.annotation.JsonProperty;

//TODO:Il gioco é da bilanciare

public class Item {
    private int ID;
    private String name;
    private int maxHP; //permette l'aumento permanente della salute massima
    private int HP; //permette la cura
    private int ATK;
    private int MATK;
    private int DEF;
    private int MDEF;
    private String effect;
    private boolean discovered;
    private int price;

    public Item() {
        // Costruttore vuoto per Jackson
    }

    public Item(int id, String name, int maxHP, int HP, int ATK, int MATK, int DEF, int MDEF, String effect, boolean discovered, int price) {
        this.ID = id;
        this.name = name;
        this.maxHP = maxHP;
        this.HP = HP;
        this.ATK = ATK;
        this.MATK = MATK;
        this.DEF = DEF;
        this.MDEF = MDEF;
        this.effect = effect;
        this.discovered = discovered;
        this.price = price;
    }
    

    public String getName() {return name;}
    public int getMaxHP() {return maxHP;}
    public int getHP() {return HP;}
    public int getATK() {return ATK;}
    public int getMATK() {return MATK;}
    public int getDEF() {return DEF;}
    public int getMDEF() {return MDEF;}
    public boolean getDiscovered() {return discovered;}
    public int getID() {return ID;}
    public String getEffect() {return effect;}
    public int getPrice() {return price;}

    @JsonProperty("name")
    public void setName(String name) {this.name = name;}
    @JsonProperty("maxHP")
    public void setMaxHP(int maxHP) {this.maxHP = maxHP;}
    @JsonProperty("HP")
    public void setHP(int HP) {this.HP = HP;}
    @JsonProperty("ATK")
    public void setATK(int ATK) {this.ATK = ATK;}
    @JsonProperty("MATK")
    public void setMATK(int MATK) {this.MATK = MATK;}
    @JsonProperty("DEF")
    public void setDEF(int DEF) {this.DEF = DEF;}
    @JsonProperty("MDEF")
    public void setMDEF(int MDEF) {this.MDEF = MDEF;}
    @JsonProperty("discovered")
    public void setDiscovered(boolean discovered) {this.discovered = discovered;}
    @JsonProperty("ID")
    public void setID(int ID) {this.ID = ID;} 
    @JsonProperty("effect")
    public void setEffect(String effect) {this.effect = effect;}
    @JsonProperty("price")
    public void setPrice(int price) {this.price = price;}
}

