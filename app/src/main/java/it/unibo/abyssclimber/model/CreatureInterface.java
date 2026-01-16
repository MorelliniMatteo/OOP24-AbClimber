package it.unibo.abyssclimber.model;

public interface CreatureInterface {
    void promoteToElite();
    void applyDifficultyMultiplier(double multiplier);
    int getId();
    String getStage();
    boolean getIsElite();
    void setId(int ID);
    void setStage(String stage);
    void setIsElite(boolean isElite);
}
