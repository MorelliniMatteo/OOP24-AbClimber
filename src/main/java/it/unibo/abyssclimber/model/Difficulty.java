package it.unibo.abyssclimber.model;

public class Difficulty {
    private static double DIFFICULTY_MULTIPLAYER;
    public static void setDifficultyMultiplier(double multiplier) {
        DIFFICULTY_MULTIPLAYER = multiplier;
    }
    public static double getDifficultyMultiplier() {
        return DIFFICULTY_MULTIPLAYER;
    }
}
