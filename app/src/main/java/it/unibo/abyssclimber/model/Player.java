package it.unibo.abyssclimber.model;

import java.util.ArrayList;
import java.util.List;
import it.unibo.abyssclimber.model.Classe;

import it.unibo.abyssclimber.core.GameCatalog;

/*
    *Player estende Creature. Le variabili sono ereditate, non visibili direttamente ma modificabili tramite i getter e setter ereditati.
*/
public class Player extends Creature{
    private Classe classe; //variabili specifiche del player
    private int gold=0;
    private List<Item> inventory;

    public Player(Tipo tipo, String name, Classe classe) {
        super(tipo, name);
        this.classe = classe;
        this.inventory = new ArrayList<>();

        this.setHP(120); //setto le statistiche base del player tramite i setter ereditati da Creature
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

    public void applicaClasse(Classe classe) { //metodo che applica le modifiche della classe scelta dal player alle sue statistiche
        this.setHP(this.getHP() + classe.getcHP());
        this.setATK(this.getATK() + classe.getcATK());
        this.setMATK(this.getMATK() + classe.getcMATK());
        this.setDEF(this.getDEF() + classe.getcDEF());
        this.setMDEF(this.getMDEF() + classe.getcMDEF());

        System.out.println("Class " + classe.getName() + " applied, the statistics have been updated.");
    }

    public void addItemToInventory(Item item) { //qui va passato come parametro il randomItem ottenuto tramite GameCatalog.getRandomItem()
        Item itemToAdd = GameCatalog.lookupItem(item.getID()); //itemToAdd diventa l'item che viene trovato tramite lookUpItem
        if (itemToAdd != null) {
            inventory.add(itemToAdd);
            System.out.println("Item " + itemToAdd.getName() + " added to inventory.");
            applyItemStats(itemToAdd);
        } else {
            System.out.println("Item with ID " + item.getID() + " not found in the game catalog.");
        }
    }

    public void applyItemStats(Item item){ //applica le statistiche dell'oggetto al player
        if(item != null){
            this.setHP(this.getHP() + item.getHP());
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

    public void resetInventory() {
        inventory.clear();
        System.out.println("Inventory reset.");
        this.setHP(120); //resetto le statistiche base del player tramite i setter ereditati da Creature
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

    public int getGold() {return gold;} //metodi per gestire il gold
    public void setGold(int gold) {this.gold=gold;}
    

    public String toString() {
        return "Player: " + getName() + " | Class: " + classe.getName() + " | HP: " + getHP() + " | ATK: " + getATK() + " | MATK: " + getMATK() + " | DEF: " + getDEF() + " | MDEF: " + getMDEF() + " | STAM: " + getSTAM() + "/" + getMaxSTAM() + " | Element: " + getElement() + " | Crit: " + getCrit() + "% | CritDMG: " + getCritDMG() + "% | Gold: " + gold;
    }
}
