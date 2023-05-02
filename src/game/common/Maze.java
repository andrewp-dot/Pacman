package game.common;

import game.fieldObjects.*;

import java.util.List;

/**
 * Common interface for maze.
 */
public interface Maze {

    /**
     * Returns a {@link Field} on specified location.
     * Throws an exception if indexes are out of range.
     *
     * @param row Row number of {@link Field}.
     * @param col Column number of {@link Field}.
     * @return {@link Field} on specified location.
     */
    Field getField(int row, int col);

    /**
     * Getter for row count.
     *
     * @return Number of rows in this maze.
     */
    int getRowCount();

    /**
     * Getter for column count.
     *
     * @return Number of columns in this maze.
     */
    int getColCount();

    /**
     * Returns a list of ghosts in the map.
     *
     * @return List of all ghosts.
     */
    List<GhostObject> getGhosts();


    /**
     * Returns a list of all keys in the map.
     *
     * @return List of all keys.
     */
    List<KeyObject> getKeys();

    /**
     * Returns pacman.
     * @return pacman
     */
    PacmanObject getPacman();
}