package it.unibo.abyssclimber.model;

public class Item {
    private String name;
    private int HP;
    private int ATK;
    private int MATK;
    private int DEF;
    private int MDEF;
    private boolean discovered;
    private String effect;
    private int ID;

    public Item() {
        // Costruttore vuoto per Jackson
    }

    public Item(String name, int HP, int ATK, int MATK, int DEF, int MDEF, boolean discovered, int id, String effect) {
        this.name = name;
        this.HP = HP;
        this.ATK = ATK;
        this.MATK = MATK;
        this.DEF = DEF;
        this.MDEF = MDEF;
        this.discovered = discovered;
        this.ID = id;
        this.effect = effect;
    }
    
    public String getName() {return name;}
    public int getHP() {return HP;}
    public int getATK() {return ATK;}
    public int getMATK() {return MATK;}
    public int getDEF() {return DEF;}
    public int getMDEF() {return MDEF;}
    public boolean isDiscovered() {return discovered;}
    public int getID() {return ID;}
    public String getEffect() {return effect;}

    public void setName(String name) {this.name = name;}
    public void setHP(int HP) {this.HP = HP;}
    public void setATK(int ATK) {this.ATK = ATK;}
    public void setMATK(int MATK) {this.MATK = MATK;}
    public void setDEF(int DEF) {this.DEF = DEF;}
    public void setMDEF(int MDEF) {this.MDEF = MDEF;}
    public void setDiscovered(boolean discovered) {this.discovered = discovered;}
    public void setID(int ID) {this.ID = ID;} 
    public void setEffect(String effect) {this.effect = effect;}
}

