package game;

import game.common.*;
import game.fieldObjects.*;
import game.fields.EndField;
import game.fields.StartField;

import java.util.ArrayList;
import java.util.List;

public class MazeClass implements Maze {
    private Field[][] map;
    private int rowCount;
    private int colCount;
    private PacmanObject pacman;
    private ArrayList<GhostObject> ghostList;
    private ArrayList<KeyObject> keyList;

    private EndField target;
    private StartField start;

    public MazeClass(Field[][] _map, int _rowCount, int _colCount,
                     PacmanObject _pacman, ArrayList<GhostObject> _ghostList, ArrayList<KeyObject> _keyList) {
        map = _map;
        rowCount = _rowCount;
        colCount = _colCount;
        ghostList = _ghostList;
        keyList = _keyList;
        pacman = _pacman;

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
}
