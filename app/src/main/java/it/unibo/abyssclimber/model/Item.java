package it.unibo.abyssclimber.model;

public class Item {
    private String name;
    private int maxHP; //permette l'aumento permanente della salute massima
    private int HP; //permette la cura
    private int ATK;
    private int MATK;
    private int DEF;
    private int MDEF;
    private boolean discovered;
    private String effect;
    private int ID;
    private int price;

    public Item() {
        // Costruttore vuoto per Jackson
    }

    public Item(String name, int maxHP, int HP, int ATK, int MATK, int DEF, int MDEF, boolean discovered, int id, String effect, int price) {
        this.name = name;
        this.maxHP = maxHP;
        this.HP = HP;
        this.ATK = ATK;
        this.MATK = MATK;
        this.DEF = DEF;
        this.MDEF = MDEF;
        this.discovered = discovered;
        this.ID = id;
        this.effect = effect;
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

    public void setName(String name) {this.name = name;}
    public void setMaxHP(int maxHP) {this.maxHP = maxHP;}
    public void setHP(int HP) {this.HP = HP;}
    public void setATK(int ATK) {this.ATK = ATK;}
    public void setMATK(int MATK) {this.MATK = MATK;}
    public void setDEF(int DEF) {this.DEF = DEF;}
    public void setMDEF(int MDEF) {this.MDEF = MDEF;}
    public void setDiscovered(boolean discovered) {this.discovered = discovered;}
    public void setID(int ID) {this.ID = ID;} 
    public void setEffect(String effect) {this.effect = effect;}
    public void setPrice(int price) {this.price = price;}
}

