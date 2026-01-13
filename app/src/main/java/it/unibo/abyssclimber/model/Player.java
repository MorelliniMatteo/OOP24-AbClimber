package it.unibo.abyssclimber.model;

import java.util.ArrayList;
import java.util.List;

import it.unibo.abyssclimber.core.GameCatalog;
import it.unibo.abyssclimber.core.combat.MoveLoader;

/*
    *Player estende Creature. Le variabili sono ereditate, non visibili direttamente ma modificabili tramite i getter e setter ereditati.
*/
public class Player extends Creature {
    private Classe classe; // variabili specifiche del player

    //TODO: 1000 di gold per testare lo shop ma andrebbe messo a 0
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
        this.setCrit(5);
        this.setCritDMG(5);

        applicaClasse(classe);
    }

    public void applicaClasse(Classe classe) { // metodo che applica le modifiche della classe scelta dal player alle
                                               // sue statistiche
        this.setMaxHP(this.getMaxHP() + classe.getcMaxHP());
        this.setHP(this.getMaxHP()); // imposto la vita attuale al massimo dopo aver aumentato il maxHP
        this.setATK(this.getATK() + classe.getcATK());
        this.setMATK(this.getMATK() + classe.getcMATK());
        this.setDEF(this.getDEF() + classe.getcDEF());
        this.setMDEF(this.getMDEF() + classe.getcMDEF());
        this.setCrit(this.getCrit() + classe.getcCrit());
        this.setCritDMG(this.getCritDMG() + classe.getcCritDMG());

        System.out.println("Class " + classe.getName() + " applied, the statistics have been updated.");
    }

    public void addItemToInventory(Item item) { // qui va passato come parametro il randomItem ottenuto tramite
                                                // GameCatalog.getRandomItem()
        Item itemToAdd = GameCatalog.lookupItem(item.getID()); // itemToAdd diventa l'item che viene trovato tramite
                                                               // lookUpItem
        if (itemToAdd != null) {
            inventory.add(itemToAdd);
            System.out.println("Item " + itemToAdd.getName() + " added to inventory.");
            applyItemStats(itemToAdd);
        } else {
            System.out.println("Item with ID " + item.getID() + " not found in the game catalog.");
        }
    }

    public void applyItemStats(Item item) { // applica le statistiche dell'oggetto al player
        if (item != null) {
            if(item.getMaxHP() > 0) {
                this.setMaxHP(this.getMaxHP() + item.getMaxHP());
                this.heal(item.getMaxHP()); // cura il player di x HP pari all'aumento dell'aumento di maxHP
                System.out.println("Equipped " + item.getName() + ". MaxHP increased by " + item.getMaxHP());
            }
            if(item.getMaxHP() == 0 && item.getHP() > 0) {
                this.heal(item.getHP());
                System.out.println("Used " + item.getName() + ". Healed for " + item.getHP() + " HP.");
            }
            this.setATK(this.getATK() + item.getATK());
            this.setMATK(this.getMATK() + item.getMATK());
            this.setDEF(this.getDEF() + item.getDEF());
            this.setMDEF(this.getMDEF() + item.getMDEF());
            System.out.println("Object Statistics " + item.getName() + " applied.");
        } else {
            System.out.println("Null object, impossible to apply statistics.");
        }
    }

    public void showInventory() {
        for (Item item : inventory) {
            System.out.println("- " + item.getName() + " (ID: " + item.getID() + ")");
        }
    }

    public void resetRun() {
        inventory.clear();
        System.out.println("Inventory reset.");
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

    /**
     * Reset run mantendo classe, elemento e mosse selezionate.
     * Non tocca selectedMoves.
     */
    public void resetRunKeepBuild() {
        inventory.clear();
        System.out.println("Inventory reset (build preserved).");
        this.setHP(120);
        this.setATK(15);
        this.setMATK(15);
        this.setDEF(5);
        this.setMDEF(5);
        this.setSTAM(5);
        this.setRegSTAM(2);
        this.setMaxSTAM(5);
        this.setCrit(5);
        this.setCritDMG(5);
    }

    public int getGold() {
        return gold;
    } // metodi per gestire il gold

    public Classe getClasse() {
        return classe;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String toString() {
        return "Player: " + getName() + " | Class: " + classe.getName() + " | HP: " + getHP() + " | ATK: " + getATK()
                + " | MATK: " + getMATK() + " | DEF: " + getDEF() + " | MDEF: " + getMDEF() + " | STAM: " + getSTAM()
                + "/" + getMaxSTAM() + " | Element: " + getElement() + " | Crit: " + getCrit() + "% | CritDMG: "
                + getCritDMG() + "% | Gold: " + getGold();
    }

    // mosse selezioante dal player
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
