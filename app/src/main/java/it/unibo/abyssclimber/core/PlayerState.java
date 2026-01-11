package it.unibo.abyssclimber.core;

import it.unibo.abyssclimber.core.combat.MoveLoader;
import it.unibo.abyssclimber.model.Classe;
import it.unibo.abyssclimber.model.Tipo;

import java.util.ArrayList;
import java.util.List;

/**
 * Minimal player state used by the UI flow.
 * Temporary implementation before the full Player logic.
 */
public class PlayerState {

    // Base health values
    private int maxHp = 100;
    private int hp = maxHp;

    // Player choices during character creation
    private Tipo chosenElement;
    private Classe chosenClass;

    // Moves selected by the player
    private final List<MoveLoader.Move> selectedMoves = new ArrayList<>();

    /**
     * @return current HP
     */
    public int getHp() {
        return hp;
    }

    /**
     * @return maximum HP
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Sets max HP and clamps current HP accordingly.
     */
    public void setMaxHp(int maxHp) {
        this.maxHp = Math.max(1, maxHp);
        this.hp = Math.min(this.hp, this.maxHp);
    }

    /**
     * Heals the player by the given amount.
     */
    public void heal(int amount) {
        if (amount <= 0) return;
        hp = Math.min(maxHp, hp + amount);
    }

    /**
     * Damages the player by the given amount.
     */
    public void damage(int amount) {
        if (amount <= 0) return;
        hp = Math.max(0, hp - amount);
    }

    /**
     * @return true if the player has no HP left
     */
    public boolean isDead() {
        return hp <= 0;
    }

    /**
     * Resets the state for a new run.
     */
    public void resetForNewRun() {
        maxHp = 100;
        hp = maxHp;
        chosenElement = null;
        chosenClass = null;
        selectedMoves.clear();
    }

    /**
     * @return chosen elemental type
     */
    public Tipo getChosenElement() {
        return chosenElement;
    }

    /**
     * Sets the chosen elemental type.
     */
    public void setChosenElement(Tipo chosenElement) {
        this.chosenElement = chosenElement;
    }

    /**
     * @return chosen class
     */
    public Classe getChosenClass() {
        return chosenClass;
    }

    /**
     * Sets the chosen class.
     */
    public void setChosenClass(Classe chosenClass) {
        this.chosenClass = chosenClass;
    }

    /**
     * @return a copy of the selected moves list
     */
    public List<MoveLoader.Move> getSelectedMoves() {
        return new ArrayList<>(selectedMoves);
    }

    /**
     * Replaces the currently selected moves.
     */
    public void setSelectedMoves(List<MoveLoader.Move> moves) {
        selectedMoves.clear();
        if (moves != null) {
            selectedMoves.addAll(moves);
        }
    }
}
