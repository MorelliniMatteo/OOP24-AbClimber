package it.unibo.abyssclimber.core.combat;

public interface CombatPresenter {
    void onTurnStart(int turn);
    void renderLog();
    void onCombatEnd(boolean finalBoss, boolean elite, boolean playerWon);
    void setCombatEnd(boolean b);
    void updateStats();
}
