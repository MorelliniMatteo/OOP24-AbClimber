package it.unibo.abyssclimber.model;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Rappresenta un'entità ostile (Mostro) all'interno del gioco.
 * Estende {@link GameEntity} ereditandone le statistiche di combattimento base.
 * Le istanze di questa classe vengono inizialmente create tramite deserializzazione JSON
 * (vedi {@link it.unibo.abyssclimber.core.DataLoader}) e successivamente clonate
 * per generare i nemici effettivi nei vari piani del dungeon.
 */
public class Creature extends GameEntity implements CreatureInterface {
    private int ID;
    private String stage;
    private boolean isElite = false;

    public Creature() {
        super(); // Costruttore vuoto per Jackson
    }

    /**
     * Costruttore di copia (Copy Constructor).
     * Questo costruttore è fondamentale per l'implementazione del pattern Prototype.
     * Permette di creare una nuova istanza indipendente di un mostro partendo da un "template"
     * presente nella lista cacheata del {@link it.unibo.abyssclimber.core.GameCatalog}.
     * Senza questo costruttore, le modifiche alle statistiche (es. danni subiti o scaling)
     * si rifletterebbero sul mostro originale nel catalogo, corrompendo i dati per i futuri spawn.
     * @param copyCreature il mostro "template" da copiare.
     */
    public Creature(Creature copyCreature) { 
        this.name = copyCreature.name; 
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

    /**
     * Promuove il mostro allo stato "Elite", potenziandone le statistiche.
     * L'operazione è idempotente: se il mostro è già Elite, il metodo non ha effetto.
     * Il potenziamento consiste in un aumento del 20% a tutte le statistiche principali
     * e un incremento della rigenerazione stamina.
     */
    @Override
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

    /**
     * Applica un moltiplicatore di difficoltà alle statistiche del mostro.
     * Utilizzato dal sistema di scaling procedurale per rendere i mostri più forti
     * man mano che il giocatore avanza nei piani.
     * @param multiplier il fattore di moltiplicazione (es. 1.1 per un +10%).
     */
    @Override
    public void applyDifficultyMultiplier(double multiplier) {
        this.maxHP = (int) (this.maxHP * multiplier);
        this.HP = this.maxHP;
        this.ATK = (int) (this.ATK * multiplier);
        this.MATK = (int) (this.MATK * multiplier);
        this.DEF = (int) (this.DEF * multiplier);
        this.MDEF = (int) (this.MDEF * multiplier);
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getStage() {
        return stage;
    }

    @Override
    public boolean getIsElite() {
        return isElite;
    }

    @Override
    @JsonProperty("ID")
    public void setId(int id) {
        this.ID = id;
    }

    @Override
    @JsonProperty("stage")
    public void setStage(String stage) {
        this.stage = stage;
    }

    @Override
    public void setIsElite(boolean elite) {
        this.isElite = elite;
    }

}