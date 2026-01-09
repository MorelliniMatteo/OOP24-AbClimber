package it.unibo.abyssclimber.core;

/**
 * Minimal player state used by UI flow.
 * Will be replaced/extended when real Player logic is merged.
 */
public class PlayerState {

    private int maxHp = 100;
    private int hp = maxHp;

    // Placeholder fields for future integration (do NOT depend on teammates' enums now)
    private String chosenElement; // TODO: replace with PlayerElement enum
    private String chosenClass;   // TODO: replace with PlayerClass enum

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

    public String getChosenElement() {
        return chosenElement;
    }

    public void setChosenElement(String chosenElement) {
        this.chosenElement = chosenElement;
    }

    public String getChosenClass() {
        return chosenClass;
    }

    public void setChosenClass(String chosenClass) {
        this.chosenClass = chosenClass;
    }
}
