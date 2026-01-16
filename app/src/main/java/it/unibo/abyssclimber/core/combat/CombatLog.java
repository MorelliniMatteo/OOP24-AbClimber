package it.unibo.abyssclimber.core.combat;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages all the combat logging.
 */
public class CombatLog {

    private final List<List<BattleText>> events = new ArrayList<>();
    
    /**
     * Adds a new line to the log. 
     * @param text a list of {@link BattleText} that will be rendered depending on their {@link LogType}.
     */
    public void logCombat(List<BattleText> text) {
        events.add(text);
    }

    /**
     * Creates a new line from a single string.
     * @param text the string to be added.
     * @param type the {@link LogType} to be used.
     */
    public void logCombat(String text, LogType type) {
        events.add(List.of(new BattleText(text, type)));
    }

    /**
     * Returns the complete list of logs to print-
     * @return a list of list of {@link BattleText}. It is the entire log to print.
     */
    public List<List<BattleText>> getEvents() {
        return List.copyOf(events);
    }

    /**
     * Deletes all logs.
     */
    public void clearEvents() {
        events.clear();
    }

}
