package game.fieldObjects;

import game.common.*;
import game.fields.*;

/**
 * Represents ghost in pacman.
 * Ghosts are immortal.
 */
public class GhostObject implements FieldObject {
    private Maze ref;
    private int row;
    private int col;

    /**
     * Initializes ghost without maze reference.
     * {@link GhostObject#setMaze(Maze)} has to be called as well, before use.
     *
     * @param row Row index.
     * @param col Column index.
     */
    public GhostObject(int row, int col){
        this.row = row;
        this.col = col;
        ref = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canMove(Field.Direction dir)
    {
        switch (dir) {
            case D:
                if (ref.getRowCount() > row + 1 && ref.getField(row + 1, col) instanceof PathField) {
                    return true;
                }
                break;
            case L:
                if (0 <= col - 1 && ref.getField(row, col - 1) instanceof PathField) {
                    return true;
                }
                break;
            case R:
                if (ref.getColCount() > col + 1 && ref.getField(row, col + 1) instanceof PathField) {
                    return true;
                }
                break;
            case U:
                if (0 <= row - 1 && ref.getField(row - 1, col) instanceof PathField) {
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean move(Field.Direction dir)
    {
        if (canMove(dir)) {
            switch (dir) {
                case D:
                    ref.getField(row, col).remove(this);
                    ref.getField(row +1, col).put(this);
//                    ref.getField(row, col).notifyObservers();
                    row++;
                    break;
                case L:
                    ref.getField(row, col).remove(this);
                    ref.getField(row, col -1).put(this);
//                    ref.getField(row, col).notifyObservers();
                    col--;
                    break;
                case R:
                    ref.getField(row, col).remove(this);
                    ref.getField(row, col +1).put(this);
//                    ref.getField(row, col).notifyObservers();
                    col++;
                    break;
                case U:
                    ref.getField(row, col).remove(this);
                    ref.getField(row -1, col).put(this);
//                    ((PathField)ref.getField(row, col)).notifyObservers();
                    row--;
                    break;
            }
//            ref.getField(row, col).notifyObservers();
//            ref.checkCollision();
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field getField()
    {
        return ref.getField(row, col);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLives()
    {
        return 1; // Ghost is immortal
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
    public void setMaze(Maze maze)
    {
        ref = maze;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void teleportTo(int row, int col) {
        ref.getField(this.row, this.col).remove(this);
        ref.getField(row, col).put(this);
        this.col = col;
        this.row = row;
    }
}

