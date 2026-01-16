package it.unibo.abyssclimber.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Creature extends GameEntity {
    private int ID;
    private String stage;
    private boolean isElite = false;

    public Creature() {
        super(); // Costruttore vuoto per Jackson
    }

    public Creature(Creature copyCreature) { // serve per creare un'istanza nuova di un mostro a partire dal mostro
                                             // presente nella lista creata tramite DataLoader
        this.name = copyCreature.name; // in questo si lavora su una copia e non si impatta la lista originale dato che
                                       // i mostri vengono presi casualmente e possono ripetersi
        this.ID = copyCreature.ID;
        this.maxHP = copyCreature.HP;
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
        this.isElite = copyCreature.isElite;
    }

    public Creature(Tipo tipo, String name) {
        super(tipo, name);
    }

    //permette di promuovere un mostro a elite, aumentando le sue statitstiche del 20%
    public void promoteToElite() {
        if (!this.isElite) {
            this.maxHP = (int) (this.HP * 1.2);
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

    public int getId() {
        return ID;
    }

    public String getStage() {
        return stage;
    }

    public boolean getIsElite() {
        return isElite;
    }

    @JsonProperty("ID")
    public void setId(int id) {
        this.ID = id;
    }

    @JsonProperty("stage")
    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setIsElite(boolean elite) {
        this.isElite = elite;
    }

}