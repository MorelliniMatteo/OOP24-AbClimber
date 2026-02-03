package it.unibo.abyssclimber.core.combat;

/**
* Represents a single entry in the combat log.
* 
* Each {@code BattleText} contains a textual message to display
* and its corresponding {@code LogType}, which determines how
* the message will be formatted when printed in the {@code TextFlow}.
*
* @param text the message to display in the combat log
* @param type the type of the message
*/ 
public record BattleText(String text, LogType type) {

}
