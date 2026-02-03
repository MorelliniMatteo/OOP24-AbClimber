package it.unibo.abyssclimber.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe astratta che rappresenta un'entità generica all'interno del mondo di gioco.
 * Funge da classe padre per {@link Player}, {@link Creature} e {@link Item}, centralizzando
 * la gestione delle statistiche di combattimento (HP, ATK, DEF, ecc.) e degli attributi comuni.
 * Utilizza le annotazioni della libreria Jackson(es. {@code @JsonProperty}) per
 * permettere il caricamento automatico dello stato dai file di configurazione JSON.
 */
public abstract class GameEntity {
    protected String name;
    protected int maxHP;
    protected int HP;
    protected int ATK;
    protected int MATK;
    protected int DEF;
    protected int MDEF;
    protected int STAM;

    /** 
     * Tasso di rigenerazione della Stamina per turno.
     * Annotato con {@code @JsonIgnore} per escluderlo dalla serializzazione JSON,
     * in quanto è spesso un valore derivato o costante di gameplay (default: 2).
     */
    @JsonIgnore
    protected int regSTAM = 2;
    protected int maxSTAM;
    protected int crit;
    protected double critDMG;
    protected Tipo element;

    public GameEntity() {}

    public GameEntity(Tipo element, String name) {
        this.element = element;
        this.name = name;
    }

    /**
     * Ripristina una quantità specificata di Punti Salute (HP).
     * Il metodo garantisce che gli HP non superino mai il valore di {@code maxHP}.
     * @param amount La quantità di cura da applicare.
     */
    public void heal(int amount) {
        this.HP += amount;
        // controllo di sicurezza per non superare il maxHP
        if (this.HP > this.maxHP) {
            this.HP = this.maxHP;
        }
    }

    public boolean isDead() {
        return this.HP <= 0;
    }

    public String getName() {
        return name;
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

    public int getRegSTAM() {
        return regSTAM;
    }

    public int getMaxSTAM() {
        return maxSTAM;
    }

    public int getCrit() {
        return crit;
    }

    public double getCritDMG() {
        return critDMG;
    }

    public Tipo getElement() {
        return element;
    }

    /**
     * Imposta il nome dell'entità. Mappato sulla proprietà JSON "name".
     * @param name il nome da assegnare.
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("maxHP")
    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    @JsonProperty("HP")
    public void setHP(int HP) {
        this.HP = HP;
    }

    @JsonProperty("ATK")
    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    @JsonProperty("MATK")
    public void setMATK(int MATK) {
        this.MATK = MATK;
    }

    @JsonProperty("DEF")
    public void setDEF(int DEF) {
        this.DEF = DEF;
    }

    @JsonProperty("MDEF")
    public void setMDEF(int MDEF) {
        this.MDEF = MDEF;
    }

    @JsonProperty("STAM")
    public void setSTAM(int STAM) {
        this.STAM = STAM;
    }

    /**
     * Imposta la rigenerazione stamina. Ignorato dal parsing JSON.
     * @param regSTAM valore di rigenerazione.
     */
    @JsonIgnore
    public void setRegSTAM(int regSTAM) {
        this.regSTAM = regSTAM;
    }

    @JsonProperty("maxSTAM")
    public void setMaxSTAM(int maxSTAM) {
        this.maxSTAM = maxSTAM;
    }

    @JsonProperty("crit")
    public void setCrit(int crit) {
        this.crit = crit;
    }

    @JsonProperty("critDMG")
    public void setCritDMG(double critDMG) {
        this.critDMG = critDMG;
    }

    @JsonProperty("element")
    public void setElement(Tipo element) {
        this.element = element;
    }
}