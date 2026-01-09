package it.unibo.abyssclimber.model;

public class Creature {
    private String name;
    private int ID;
    private int HP=100;
    private int ATK=15;
    private int MATK=10;
    private int DEF=10;
    private int MDEF=0;
    private int STAM=0;
    private int regSTAM=1;
    private int maxSTAM=5;
    private Tipo element; 
    private int crit = 50;
    private double critDMG = 1.5;
    private String stage;
    private boolean isElite = false;

    public Creature() {
        // Costruttore vuoto per Jackson
    }

    public Creature(Creature copyCreature) { //serve per creare un'istanza nuova di un mostro a partire dal mostro presente nella lista creata tramite DataLoader
        this.name = copyCreature.name;       //in questo si lavora su una copia e non si impatta la lista originale dato che i mostri vengono presi casualmente e possono ripetersi
        this.ID = copyCreature.ID;
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

    public void promoteToElite() {
        if (!isElite) {
            this.HP = (int)(this.HP * 1.2);
            this.ATK = (int)(this.ATK * 1.2);
            this.MATK = (int)(this.MATK * 1.2);
            this.DEF = (int)(this.DEF * 1.2);
            this.MDEF = (int)(this.MDEF * 1.2);
            this.isElite = true;
        }
    }

    public String getName() {return name;}
    public int getId() {return ID;}
    public int getHP() {return HP;}
    public int getATK() {return ATK;}
    public int getMATK() {return MATK;}
    public int getDEF() {return DEF;}
    public int getMDEF() {return MDEF;}
    public int getSTAM() {return STAM;}
    public int regSTAM() {return regSTAM;}
    public int getMaxSTAM() {return maxSTAM;}
    public Tipo getElement() {return element;}
    public int getCrit() {return crit;}
    public double getCritDMG() {return critDMG;}
    public String getStage() {return stage;}
    public boolean getIsElite() {return isElite;}

    public void setName(String name) {this.name=name;}
    public void setHP(int hp) {this.HP=hp;}
    public void setId(int id) {this.ID=id;}
    public void setATK(int atk) {this.ATK=atk;}
    public void setMATK(int matk) {this.MATK=matk;}
    public void setDEF(int def) {this.DEF=def;}
    public void setMDEF(int mdef) {this.MDEF=mdef;}
    public void setSTAM(int stam) {this.STAM=stam;}
    public void setRegSTAM(int rstam) {this.regSTAM=rstam;}
    public void setMaxSTAM(int mstam) {this.maxSTAM=mstam;}
    public void setElement(Tipo elem) {this.element=elem;}
    public void setCrit(int crit) {this.crit=crit;}
    public void setCritDMG(double critdmg) {this.critDMG=critdmg;}
    public void setStage(String stage) {this.stage=stage;}
    public void setIsElite(boolean elite) {this.isElite=elite;}
}
