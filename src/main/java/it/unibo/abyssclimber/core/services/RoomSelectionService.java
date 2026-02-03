package it.unibo.abyssclimber.core.services;

import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.RoomContext;
import it.unibo.abyssclimber.core.RoomOption;
import it.unibo.abyssclimber.core.RoomType;
import it.unibo.abyssclimber.core.SceneId;

import java.util.List;

/**
 * Service for handling room selection logic.
 */
public class RoomSelectionService {

    /**
     * Loads or generates room options for the given floor.
     *
     * @param floor current floor
     * @return list of room options
     */
    public List<RoomOption> getOptionsForFloor(int floor) {
        return RoomContext.get().getOrCreateOptions(floor);
    }

    /**
     * Applies the room choice and returns the next scene.
     *
     * @param opt   chosen option
     * @param floor current floor
     * @param index door index
     * @return the next scene to open
     */
    public SceneId handleSelection(RoomOption opt, int floor, int index) {
        RoomContext.get().setLastChosen(opt);
        if (opt.type() == RoomType.FIGHT) {
            RoomContext.get().disableDoor(floor, index);
        }
        return routeFor(opt);
    }

    /**
     * Returns the scene associated with the given room type.
     *
     * @param opt room option
     * @return target scene
     */
    public SceneId routeFor(RoomOption opt) {
        return switch (opt.type()) {
            case FIGHT -> SceneId.FIGHT_ROOM;
            case SHOP -> SceneId.SHOP_ROOM;
            case BOSS_ELITE -> SceneId.BOSS_ROOM;
            case FINAL_BOSS -> SceneId.FINAL_BOSS_ROOM;
        };
    }

    /**
     * Forces player death and returns the game over scene.
     *
     * @return game over scene
     */
    public SceneId forceDeath() {
        GameState.get().getPlayer().setHP(0);
        return SceneId.GAME_OVER;
    }
}
