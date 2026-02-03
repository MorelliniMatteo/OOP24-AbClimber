package it.unibo.abyssclimber.model;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Rappresenta un oggetto consumabile o equipaggiabile (Reliquia) all'interno del gioco.
 * Questa classe estende {@link GameEntity} per sfruttare il riuso del codice:
 * le variabili ereditate in questo contesto non rappresentano
 * lo stato vitale dell'oggetto, ma i bonus passivi che vengono sommati
 * alle statistiche del {@link Player} quando l'oggetto viene acquisito.
 * Gli oggetti sono definiti esternamente in file JSON e caricati tramite {@link it.unibo.abyssclimber.core.DataLoader}.
 */
public class Item extends GameEntity {
    private int ID;
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

    public boolean getDiscovered() {return discovered;}
    public int getID() {return ID;}
    public String getEffect() {return effect;}
    public int getPrice() {return price;}
    
    @JsonProperty("discovered")
    public void setDiscovered(boolean discovered) {this.discovered = discovered;}
    @JsonProperty("ID")
    public void setID(int ID) {this.ID = ID;} 
    @JsonProperty("effect")
    public void setEffect(String effect) {this.effect = effect;}
    @JsonProperty("price")
    public void setPrice(int price) {this.price = price;}

    /**
     * Genera una rappresentazione testuale dinamica delle statistiche dell'oggetto.
     * A differenza di un toString standard, questo metodo utilizza un {@link StringBuilder}
     * per includere nella stringa solo le statistiche positive (maggiori di 0).
     * Questo evita di mostrare all'utente una lista di "0" inutili (es. un oggetto che dà solo ATK
     * non mostrerà "DEF: 0 | HP: 0").
     * @return Una stringa formattata pronta per essere visualizzata nella UI (es. Tooltip).
     */
    @Override
    public String toString() {
    StringBuilder sb = new StringBuilder();

    if (maxHP > 0) {
        sb.append("maxHP: ").append(this.maxHP);
        sb.append(" | ");
    }
    if (HP > 0) {
        sb.append("HP: ").append(this.HP);
        sb.append(" | ");
    }
    if (ATK > 0) {
        sb.append("ATK: ").append(this.ATK);
        sb.append(" | ");
    }
    if (MATK > 0) {
        sb.append("MATK: ").append(this.MATK);
        sb.append(" | ");
    }
    if (DEF > 0) {
        sb.append("DEF: ").append(this.DEF);
        sb.append(" | ");
    }
    if (MDEF > 0) {
        sb.append("MDEF: ").append(this.MDEF);
        sb.append(" | ");
    }
    return sb.toString();
    }
}