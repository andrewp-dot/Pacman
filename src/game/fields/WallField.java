package game.fields;

import game.common.*;

/**
 * Represents unattainable field to which {@link FieldObject FieldObjects} can NOT move.
 */
public class WallField implements Field {
    private int row;
    private int col;
    private Maze ref;


    /**
     * Initializes an empty {@link WallField}.
     *
     * @param row Row index.
     * @param col Column index.
     */
    public WallField(int row, int col) {
        this.row = row;
        this.col = col;
        this.ref = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field nextField(Direction dirs) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean put(FieldObject object) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(FieldObject object) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaze(Maze maze) {
        ref = maze;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldObject getLast() {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRow() { return  this.row; }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCol() { return this.col; }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<FieldObject> getAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     * Since this Field type does not allow movement, this method will always return false.
     *
     * @return Always false.
     */
    @Override
    public boolean canMove()
    {
        return false;
    }

    // TODO figure out what for
//    @Override
//    public boolean contains(CommonMazeObject obj)
//    {
//        return false;
//    }

    /**
     * Objects are equal if they belong to the same class na they column and row indexes match.
     *
     * @param obj Object to compare to.
     * @return True if objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WallField && row == ((WallField) obj).row && col == ((WallField) obj).col) {
            return true;
        }
        return false;
    }
}
