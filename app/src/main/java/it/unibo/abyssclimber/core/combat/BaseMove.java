package it.unibo.abyssclimber.core.combat;

/**
 * Represents a fundamental combat move used in the AbyssClimber battle system.
 * 
 * Base moves include the elemental "Strike" and "Swirl" variants (two per element),
 * each costing 1 mana point. These moves are intentionally minimal and are
 * completed during the {@code baseMoveAssign} initialization phase, where their
 * final attributes are populated.
 *
 * @param name  the display name of the move
 * @param power the base damage of the move
 * @param acc   the accuracy value determining hit probability
 * @param type  the physical or magical type identifier
 * @param cost  the mana point cost required to use the move
 */
public record BaseMove(
    String name,
    int power,
    int acc,
    int type,
    int cost) {}
