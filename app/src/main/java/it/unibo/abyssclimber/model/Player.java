package it.unibo.abyssclimber.model;

import java.util.ArrayList;
import java.util.List;

import it.unibo.abyssclimber.core.combat.MoveLoader;

/**
 * Rappresenta il personaggio giocante controllato dall'utente.
 * Estende {@link GameEntity} per ereditare la gestione delle statistiche di combattimento,
 * aggiungendo funzionalità specifiche per la progressione del giocatore all'interno della run, 
 * come la gestione dell'inventario (reliquie), l'accumulo di oro e la selezione delle mosse.
 * Implementa {@link PlayerInterface} per definire il contratto pubblico delle azioni del giocatore.
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

    /**
     * Applica i modificatori di statistica definiti dall'Enum {@link Classe}.
     * Questo metodo implementa una logica di composizione: invece di usare l'ereditarietà
     * (es. creare una classe {@code MagePlayer}), si iniettano le statistiche
     * definite nell'oggetto di configurazione.
     * @param classe L'archetipo da applicare.
     */
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

    /**
     * Aggiunge un oggetto all'inventario e ne applica immediatamente gli effetti passivi.
     * * @param item L'oggetto da aggiungere (solitamente ottenuto casualmente dal {@code GameCatalog}).
     */
    @Override
    public void addItemToInventory(Item item) { // qui va passato come parametro il randomItem ottenuto tramite GameCatalog.getRandomItem()
        if (item != null) {
            inventory.add(item);
            applyItemStats(item);
        } 
    }

    /**
     * Calcola e applica i bonus alle statistiche forniti da un oggetto.
     * Gestisce la logica di cura associata all'incremento della vita:
     * Se l'oggetto aumenta i MaxHP, il giocatore viene curato per la stessa quantità (mantieni la % di salute o buff puro).
     * Se l'oggetto fornisce solo HP (senza aumentare il massimo), agisce come pozione/cura istantanea.
     * @param item L'oggetto di cui applicare le statistiche.
     */
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

    /**
     * Ripristina lo stato del giocatore alle condizioni iniziali (Reset della Run).
     * Questo metodo è fondamentale per la meccanica Roguelike: mantiene l'istanza
     * dell'oggetto Player ma ne azzera progressi, inventario, mosse e riporta le statistiche
     * ai valori di base (hardcoded).
     */
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

    /**
     * Restituisce una copia difensiva della lista delle mosse equipaggiate.
     * Viene restituita una nuova {@code ArrayList} per preservare l'incapsulamento 
     * ed evitare che riferimenti esterni possano modificare direttamente la lista privata.
     * @return Una {@link List} di {@link MoveLoader.Move}.
     */
    public List<MoveLoader.Move> getSelectedMoves() {
        return new ArrayList<>(selectedMoves);
    }

    /**
     * Imposta le mosse di combattimento del giocatore.
     * Svuota la lista corrente e aggiunge tutti gli elementi della nuova lista,
     * gestendo correttamente il caso di input nullo.
     * @param moves La lista delle nuove mosse da equipaggiare.
     */
    public void setSelectedMoves(List<MoveLoader.Move> moves) {
        selectedMoves.clear();
        if (moves != null) {
            selectedMoves.addAll(moves);
        }
    }
}