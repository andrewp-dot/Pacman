package game.fields;

import game.common.*;
import utils.Observable;
import utils.Observer;
//import ija.ija2022.homework2.common.Observable;
//import ija.ija2022.homework2.game.MazeClass;

import java.util.ArrayList;


/**
 * Represents blank field to which {@link FieldObject FieldObjects} can move.
 * Changes can be registered by {@link Observer Observers}.
 */
public class PathField implements Field, Observable {
    private int row;
    private int col;
    private Maze ref;

    private ArrayList<Observer> observers;

    private ArrayList<FieldObject> fieldObjects;

    /**
     * Initializes an empty {@link PathField}.
     *
     * @param row Row index.
     * @param col Column index.
     */
    public PathField(int row, int col) {
        this.row = row;
        this.col = col;
        fieldObjects = new ArrayList<>();
        observers = new ArrayList<>();
        ref = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field nextField(Direction dirs) {
        Field tmp = null;
        switch (dirs) {
            case D:
                tmp = ref.getField(row + 1, col);
                break;
            case L:
                tmp = ref.getField(row, col - 1);
                break;
            case R:
                tmp = ref.getField(row, col + 1);
                break;
            case U:
                tmp = ref.getField(row - 1, col);
                break;
        }
        return tmp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return fieldObjects.size() == 0;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<FieldObject> getAll() {
        return fieldObjects;
    }

    /**
     * {@inheritDoc}
     * Since this Field type allows movement, this method will always return true.
     *
     * @return Always true.
     */
    @Override
    public boolean canMove() {
        return true;
    }

    // TODO figure out what for
//    public boolean contains(CommonMazeObject obj)
//    {
//        return mazeObjects.contains(obj);
//    }

    /**
     * Objects are equal if they belong to the same class na they column and row indexes match.
     *
     * @param obj Object to compare to.
     * @return True if objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PathField && row == ((PathField) obj).row && col == ((PathField) obj).col) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean put(FieldObject object)
    {
        if (fieldObjects.add(object)){
            this.notifyObservers();
            return true;
        }
        return false; // should not get here
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(FieldObject object) {
        if (fieldObjects.remove(object)) {
            this.notifyObservers();
            return true;
        }
        return false;
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
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeObserver(Observer observer) {
        return this.observers.remove(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.update(this);
        }
    }
}
