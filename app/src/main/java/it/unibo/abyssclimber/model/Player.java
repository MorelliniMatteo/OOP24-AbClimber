package it.unibo.abyssclimber.model;

import java.util.ArrayList;
import java.util.List;

import it.unibo.abyssclimber.core.combat.MoveLoader;

/*
    *Player estende GameEntity. Le variabili sono ereditate, non visibili direttamente ma modificabili tramite i getter e setter ereditati.
*/
public class Player extends GameEntity implements PlayerInterface{
    private Classe classe; // variabili specifiche del player
    private int gold = 0;
    private List<Item> inventory;

    public Player(String name, Tipo tipo, Classe classe) {
        super(tipo, name);
        this.classe = classe;
        this.inventory = new ArrayList<>();

        this.setMaxHP(120);
        this.setHP(120); // setto le statistiche base del player tramite i setter ereditati da Creature
        this.setATK(15);
        this.setMATK(15);
        this.setDEF(5);
        this.setMDEF(5);
        this.setSTAM(5);
        this.setRegSTAM(2);
        this.setMaxSTAM(5);
        this.setCrit(1);
        this.setCritDMG(0);

        applicaClasse(classe);
    }

    @Override
    public void applicaClasse(Classe classe) { // metodo che applica le modifiche della classe scelta dal player alle sue statistiche
        this.setMaxHP(this.getMaxHP() + classe.getcMaxHP());
        this.setHP(this.getMaxHP()); // imposto la vita attuale al massimo dopo aver aumentato il maxHP
        this.setATK(this.getATK() + classe.getcATK());
        this.setMATK(this.getMATK() + classe.getcMATK());
        this.setDEF(this.getDEF() + classe.getcDEF());
        this.setMDEF(this.getMDEF() + classe.getcMDEF());
        this.setCrit(this.getCrit() + classe.getcCrit());
        this.setCritDMG(this.getCritDMG() + classe.getcCritDMG());
    }

    @Override
    public void addItemToInventory(Item item) { // qui va passato come parametro il randomItem ottenuto tramite GameCatalog.getRandomItem()
        if (item != null) {
            inventory.add(item);
            applyItemStats(item);
        } 
    }

    @Override
    public void applyItemStats(Item item) { // applica le statistiche dell'oggetto al player
        if (item != null) {
            if(item.getMaxHP() > 0) {
                this.setMaxHP(this.getMaxHP() + item.getMaxHP());
                this.heal(item.getMaxHP()); // cura il player di x HP pari all'aumento dell'aumento di maxHP
            }
            if(item.getMaxHP() == 0 && item.getHP() > 0) { // se l'oggetto non aumenta il maxHP ma da HP allora cura il player
                this.heal(item.getHP());
            }
            this.setATK(this.getATK() + item.getATK());
            this.setMATK(this.getMATK() + item.getMATK());
            this.setDEF(this.getDEF() + item.getDEF());
            this.setMDEF(this.getMDEF() + item.getMDEF());
        }
    }

    @Override
    public void resetRun() {
        inventory.clear();
        this.setMaxHP(120);
        this.setHP(120); // resetto le statistiche base del player tramite i setter ereditati da Creature
        this.setATK(15);
        this.setMATK(15);
        this.setDEF(5);
        this.setMDEF(5);
        this.setSTAM(5);
        this.setRegSTAM(2);
        this.setMaxSTAM(5);
        this.setCrit(5);
        this.setCritDMG(5);
        selectedMoves.clear();
    }

    @Override
    public int getGold() {
        return gold;
    }

    @Override
    public Classe getClasse() {
        return classe;
    }

    @Override
    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    public String toString() {
        return "Player: " + getName() + " | Class: " + classe.getName() + " | HP: " + getHP() + " | ATK: " + getATK()
                + " | MATK: " + getMATK() + " | DEF: " + getDEF() + " | MDEF: " + getMDEF() + " | STAM: " + getSTAM()
                + "/" + getMaxSTAM() + " | Element: " + getElement() + " | Crit: " + getCrit() + "% | CritDMG: "
                + getCritDMG() + "% | Gold: " + getGold();
    }

    // mosse selezionate dal player
    private final List<MoveLoader.Move> selectedMoves = new ArrayList<>();

    public List<MoveLoader.Move> getSelectedMoves() {
        return new ArrayList<>(selectedMoves);
    }

    // setta le mosse del player
    public void setSelectedMoves(List<MoveLoader.Move> moves) {
        selectedMoves.clear();
        if (moves != null) {
            selectedMoves.addAll(moves);
        }
    }
}