package it.unibo.abyssclimber.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Creature {
    private String name;
    private int ID;
    private int maxHP;
    private int HP;
    private int ATK;
    private int MATK;
    private int DEF;
    private int MDEF;
    private int STAM;
    private int regSTAM = 2;
    private int maxSTAM;
    private Tipo element;
    private int crit;
    private double critDMG;
    private String stage;
    private boolean isElite = false;

    public Creature() {
        // Costruttore vuoto per Jackson
    }

    public Creature(Creature copyCreature) { // serve per creare un'istanza nuova di un mostro a partire dal mostro
                                             // presente nella lista creata tramite DataLoader
        this.name = copyCreature.name; // in questo si lavora su una copia e non si impatta la lista originale dato che
                                       // i mostri vengono presi casualmente e possono ripetersi
        this.ID = copyCreature.ID;
        this.maxHP = copyCreature.maxHP;
        this.HP = copyCreature.HP;
        this.ATK = copyCreature.ATK;
        this.MATK = copyCreature.MATK;
        this.DEF = copyCreature.DEF;
        this.MDEF = copyCreature.MDEF;
        this.STAM = copyCreature.STAM;
        this.regSTAM = copyCreature.regSTAM;
        this.maxSTAM = copyCreature.maxSTAM;
        this.element = copyCreature.element;
        this.crit = copyCreature.crit;
        this.critDMG = copyCreature.critDMG;
        this.stage = copyCreature.stage;
        this.isElite = false;
    }

    public Creature(Tipo tipo, String name) {
        this.element = tipo;
        this.name = name;
    }

    public void heal(int amount) {
        this.HP += amount;
        // Controllo di sicurezza: non superare mai il massimo
        if (this.HP > this.maxHP) {
            this.HP = this.maxHP;
        }
        System.out.println(this.name + " healed for " + amount + ". HP: " + this.HP + "/" + this.maxHP);
    }

    public void promoteToElite() {
        if (!isElite) {
            this.maxHP = (int) (this.maxHP * 1.2);
            this.HP = this.maxHP;
            this.ATK = (int) (this.ATK * 1.2);
            this.MATK = (int) (this.MATK * 1.2);
            this.DEF = (int) (this.DEF * 1.2);
            this.MDEF = (int) (this.MDEF * 1.2);
            this.regSTAM = this.regSTAM + 1;
            this.isElite = true;
        }
    }

    public void applyDifficultyMultiplier(double multiplier) {
        this.maxHP = (int) (this.maxHP * multiplier);
        this.HP = this.maxHP;
        this.ATK = (int) (this.ATK * multiplier);
        this.MATK = (int) (this.MATK * multiplier);
        this.DEF = (int) (this.DEF * multiplier);
        this.MDEF = (int) (this.MDEF * multiplier);
    }

    public String getName() {
        return name;
    }
    
    public int getId() {
        return ID;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getHP() {
        return HP;
    }

    public int getATK() {
        return ATK;
    }

    public int getMATK() {
        return MATK;
    }

    public int getDEF() {
        return DEF;
    }

    public int getMDEF() {
        return MDEF;
    }

    public int getSTAM() {
        return STAM;
    }

    public int regSTAM() {
        return regSTAM;
    }

    public int getMaxSTAM() {
        return maxSTAM;
    }

    public Tipo getElement() {
        return element;
    }

    public int getCrit() {
        return crit;
    }

    public double getCritDMG() {
        return critDMG;
    }

    public String getStage() {
        return stage;
    }

    public boolean getIsElite() {
        return isElite;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("maxHP")
    public void setMaxHP(int maxHp) {
        this.maxHP = maxHp;
    }

    @JsonProperty("HP")
    public void setHP(int hp) {
        this.HP = hp;
    }

    public boolean isDead() {
        return this.HP <= 0;
    }

    @JsonProperty("ID")
    public void setId(int id) {
        this.ID = id;
    }

    @JsonProperty("ATK")
    public void setATK(int atk) {
        this.ATK = atk;
    }

    @JsonProperty("MATK")
    public void setMATK(int matk) {
        this.MATK = matk;
    }

    @JsonProperty("DEF")
    public void setDEF(int def) {
        this.DEF = def;
    }

    @JsonProperty("MDEF")
    public void setMDEF(int mdef) {
        this.MDEF = mdef;
    }

    @JsonProperty("STAM")
    public void setSTAM(int stam) {
        this.STAM = stam;
    }

    @JsonProperty("regSTAM")
    public void setRegSTAM(int rstam) {
        this.regSTAM = rstam;
    }

    @JsonProperty("maxSTAM")
    public void setMaxSTAM(int mstam) {
        this.maxSTAM = mstam;
    }

    public void setElement(Tipo elem) {
        this.element = elem;
    }

    @JsonProperty("crit")
    public void setCrit(int crit) {
        this.crit = crit;
    }

    @JsonProperty("critDMG")
    public void setCritDMG(double critdmg) {
        this.critDMG = critdmg;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setIsElite(boolean elite) {
        this.isElite = elite;
    }
}
