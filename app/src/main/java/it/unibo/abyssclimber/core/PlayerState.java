package it.unibo.abyssclimber.core;

import it.unibo.abyssclimber.model.Classe;
import it.unibo.abyssclimber.model.Tipo;

/**
 * Minimal player state used by UI flow.
 * Will be replaced/extended when real Player logic is merged.
 */
public class PlayerState {

    private int maxHp = 100;
    private int hp = maxHp;

    private Tipo chosenElement;
    private Classe chosenClass;

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = Math.max(1, maxHp);
        this.hp = Math.min(this.hp, this.maxHp);
    }

    public void heal(int amount) {
        if (amount <= 0) return;
        hp = Math.min(maxHp, hp + amount);
    }

    public void damage(int amount) {
        if (amount <= 0) return;
        hp = Math.max(0, hp - amount);
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public void resetForNewRun() {
        maxHp = 100;
        hp = maxHp;
        chosenElement = null;
        chosenClass = null;
    }

    public Tipo getChosenElement() {
        return chosenElement;
    }

    public void setChosenElement(Tipo chosenElement) {
        this.chosenElement = chosenElement;
    }

    public Classe getChosenClass() {
        return chosenClass;
    }

    public void setChosenClass(Classe chosenClass) {
        this.chosenClass = chosenClass;
    }
}
