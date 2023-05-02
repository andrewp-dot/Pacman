package game.common;

/**
 * Common interface for all objects in {@link Maze}.
 *
 * @see Maze
 */
public interface FieldObject {
    /**
     * Determines if this object can move in given {@link game.common.Field.Direction}.
     * @param dir Direction to move in.
     * @return True if this object can move, false otherwise.
     */
    boolean canMove(Field.Direction dir);

    /**
     * Moves this object in given {@link game.common.Field.Direction}.
     * Throws {@link UnsupportedOperationException} if object type is immovable.
     *
     * @param dir Direction to move in.
     * @return True if successful, false otherwise.
     */
    boolean move(Field.Direction dir);

    /**
     * Returns a reference to {@link Field}, where this object is stored in.
     *
     * @return Reference to {@link Field}, where this object is stored in.
     */
    Field getField();

    /**
     * Returns number of lives of given object.
     * For immortal object types returns 1.
     *
     * @return Number of lives.
     */
    int getLives();

    /**
     * Decrements number of lives by 1.
     * Throws {@link UnsupportedOperationException} if object is immortal.
     */
    void loseLive();

    /**
     * Sets a reference to {@link Maze}.
     *
     * @param maze maze to set the reference to.
     */
    void setMaze(Maze maze);
}