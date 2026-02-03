package it.unibo.abyssclimber.core.combat;

import it.unibo.abyssclimber.model.Tipo;

/**
 * Represents a combat move usable by a {@link it.unibo.abyssclimber.model.GameEntity}.
 */
public interface CombatMove {
    String getName();
    int getPower();
    int getAcc();
    int getType(); // 1 for physical, 2 for magical.
    int getCost();
    int getId();
    Tipo getElement();

}
