package game.common;

/**
 * Common interface for all field types that are in maze.
 *
 * @author Ondřej Vrána
 * @see Maze
 */
public interface Field {

    /**
     * Directions of movement.
     * @author Ondřej Vrána
     */
    enum Direction {
        L, // left
        U, // up
        R, // right
        D // down
    }

    /**
     * Determines if any {@link FieldObject} could move to this field (aka it's not wall or something similar).
     *
     * @return True if any {@link FieldObject} can move here, false otherwise.
     */
    boolean canMove();

    /**
     * Returns an iterable list of all {@link FieldObject FieldObjects} on this field.
     * The order has to be respected for rendering.
     * Throws an {@link UnsupportedOperationException} when {@link FieldObject} can not be put in this field.
     * Use {@link Field#canMove()} to determine if object can be put in this field.
     *
     * @return {@link Iterable} of all {@link FieldObject FieldObjects} on this field.
     */
    Iterable<FieldObject> getAll();

    /**
     * Determines if this field is empty of {@link FieldObject FieldObjects}.
     *
     * @return True if there are no {@link FieldObject MazeObjects}, false otherwise.
     */
    boolean isEmpty();

    /**
     * Returns neighboring field in the dir {@link Direction}.
     *
     * @param dir Direction.
     * @return Neighboring field in the dir {@link Direction}.
     */
    Field nextField(Direction dir);

    /**
     * Puts {@link FieldObject} into this field.
     * Throws an {@link UnsupportedOperationException} when {@link FieldObject} can not be put in this field.
     * Use {@link Field#canMove()} to determine if object can be put in this field.
     *
     * @param object Object to put in.
     * @return True if successful, false otherwise.
     */
    boolean put(FieldObject object);

    /**
     * Removes {@link FieldObject} from this field.
     * Throws an {@link UnsupportedOperationException} when {@link FieldObject} can not be put in this field.
     * Use {@link Field#canMove()} to determine if object can be put in this field.
     *
     * @param object Object to remove from this field.
     * @return True if successful, false otherwise.
     */
    boolean remove(FieldObject object);

    /**
     * Sets a reference to {@link Maze}.
     *
     * @param maze maze to set the reference to.
     */
    void setMaze(Maze maze);

    /**
     * Returns last/newest {@link FieldObject FieldObjects} on this field.
     * Throws an {@link UnsupportedOperationException} when {@link FieldObject} can not be put in this field.
     * Use {@link Field#canMove()} to determine if object can be put in this field.
     *
     * @return {@link Iterable} of all {@link FieldObject FieldObjects} on this field.
     */
    FieldObject getLast();

    /**
     * @return Row index of this field.
     */
    int getRow();

    /**
     * @return Column index of this field.
     */
    int getCol();


}