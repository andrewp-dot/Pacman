package game.fieldObjects;

import game.MazeClass;
import game.common.Field;
import game.common.FieldObject;
import game.common.Maze;
import game.fields.PathField;

/**
 * Represents key in pacman.
 * Keys are immortal and immovable.
 */
public class KeyObject implements FieldObject {
    private Maze ref;
    private int row;
    private int col;

    /**
     * Determines whether key was picked by pacman or not.
     */
    private boolean isPicked;

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
        this.isPicked = false;
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
    public void setLives(int lives) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaze(Maze maze) {
        this.ref = maze;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void teleportTo(int row, int col) {
        throw new UnsupportedOperationException();
    }

    /**
     * @return Value of isPicked.
     */
    public boolean getIsPicked(){
        return this.isPicked;
    }

    /**
     * @param isPicked New value of isPicked.
     */
    public void setIsPicked(boolean isPicked){
        this.isPicked = isPicked;
        ((PathField)getField()).notifyObservers();
        ((MazeClass)ref).notifyObservers();
    }
}
