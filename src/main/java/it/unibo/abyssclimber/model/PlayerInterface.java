package it.unibo.abyssclimber.model;

public interface PlayerInterface {
    void applicaClasse(Classe classe);
    void addItemToInventory(Item item);
    void applyItemStats(Item item);
    void resetRun();
    int getGold();
    Classe getClasse();
    void setGold(int gold);
    String toString();
}
