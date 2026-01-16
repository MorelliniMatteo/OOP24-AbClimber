package it.unibo.abyssclimber.core.combat;

/**
 * Interface for combat UI events.
 * onTurnStart(turn): actions to be performed at the start of each turn.
 * renderLog(): method to render the current {@link CombatLog} to the GUI.
 * onCombatEnd(): method invoked when a battle encounter is over. It determines if the player won, where the player is currently and to what room the player should go.
 */
public interface CombatPresenter {
    void onTurnStart(int turn);
    void renderLog();
    void onCombatEnd(boolean finalBoss, boolean elite, boolean playerWon);
    void setCombatEnd(boolean b);
    void updateStats();
}
