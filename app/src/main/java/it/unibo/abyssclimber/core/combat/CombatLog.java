package it.unibo.abyssclimber.core.combat;

import java.util.ArrayList;
import java.util.List;

//Class that manages all the combat logging methods,
public class CombatLog {

    private final List<List<BattleText>> events = new ArrayList<>();
    
    //Adds a new "line". A list of BattleText for split colouring.
    public void logCombat(List<BattleText> text) {
        events.add(text);
    }

    //Adds a new line made of a string to the log.
    public void logCombat(String text, LogType type) {
        events.add(List.of(new BattleText(text, type)));
    }

    //Returns all the queued logs.
    public List<List<BattleText>> getEvents() {
        return List.copyOf(events);
    }

    //Delets all logs.
    public void clearEvents() {
        events.clear();
    }

}
