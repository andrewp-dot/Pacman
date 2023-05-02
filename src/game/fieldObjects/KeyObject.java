package game.fieldObjects;

import game.common.Field;
import game.common.FieldObject;
import game.common.Maze;

/**
 * Represents key in pacman.
 * Keys are immortal and immovable.
 */
public class KeyObject implements FieldObject {
    private Maze ref;
    private int row;
    private int col;

    /**
     * Initializes key without maze reference.
     * {@link KeyObject#setMaze(Maze)} has to be called as well, before use.
     *
     * @param row Row index.
     * @param col Column index.
     */
    public KeyObject(int row, int col){
        this.row = row;
        this.col = col;
        ref = null;
    }

    /**
     * {@inheritDoc}
     * Since {@link KeyObject} is immovable this method always returns false.
     * @return Always false.
     */
    @Override
    public boolean canMove(Field.Direction dir) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean move(Field.Direction dir) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field getField() {
        return ref.getField(this.row, this.col);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLives() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loseLive() {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaze(Maze maze) {
        this.ref = maze;
    }
}
