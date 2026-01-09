package it.unibo.abyssclimber.core;

/**
 * Represents an option for a room in the game.
 *
 * @param type        the type of the room
 * @param title       the title of the room option
 * @param description the description of the room option
 * @param iconPath    the path to the icon representing the room option
 */
public record RoomOption(
        RoomType type,
        String title,
        String description,
        String iconPath
) { }
