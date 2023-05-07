package game;

import game.common.*;
import game.fieldObjects.*;
import game.fields.EndField;
import game.fields.StartField;
import utils.Observable;
import utils.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents maze.
 * @author Ondřej Vrána
 */
public class MazeClass implements Maze, Observable {
    private Field[][] map;
    private int rowCount;
    private int colCount;
    private PacmanObject pacman;
    private ArrayList<GhostObject> ghostList;
    private ArrayList<KeyObject> keyList;
    private ArrayList<Observer> observers;

    private EndField target;
    private StartField start;

    /**
     * Create empty maze.
     * @param _map Map of maze.
     * @param _rowCount Number of rows in the map.
     * @param _colCount Number of columns in the map.
     * @param _pacman Pacman from the map.
     * @param _ghostList All ghosts from the map.
     * @param _keyList All keys from the map.
     */
    public MazeClass(Field[][] _map, int _rowCount, int _colCount,
                     PacmanObject _pacman, ArrayList<GhostObject> _ghostList, ArrayList<KeyObject> _keyList) {
        map = _map;
        rowCount = _rowCount;
        colCount = _colCount;
        ghostList = _ghostList;
        keyList = _keyList;
        pacman = _pacman;
        observers = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (map[i][j] instanceof EndField) {
                    target = (EndField) map[i][j];
                } else if (map[i][j] instanceof StartField) {
                    start = (StartField) map[i][j];
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field getField(int row, int col) {
        return map[row][col];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GhostObject> getGhosts() {
        return ghostList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<KeyObject> getKeys() {
        return keyList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PacmanObject getPacman() {
        return pacman;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StartField getStart() {
        return start;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EndField getEnd() {
        return target;
    }

    /**
     * {@inheritDoc}
     */
    public int getColCount() {
        return colCount;
    }

    /**
     * {@inheritDoc}
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Gets game map
     */
    public Field[][] getMap() { return this.map; }

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
