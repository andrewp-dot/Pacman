package game;

import game.common.*;
import game.fieldObjects.*;

import java.util.ArrayList;
import java.util.List;

public class MazeClass implements Maze {
    private Field[][] pole;
    private int rowCount;
    private int colCount;
    private PacmanObject pacman;
    private ArrayList<GhostObject> ghostList;
    private ArrayList<KeyObject> keyList;

    public MazeClass(Field[][] _pole, int _rowCount, int _colCount,
                     PacmanObject _pacman, ArrayList<GhostObject> _ghostList, ArrayList<KeyObject> _keyList) {
        pole = _pole;
        rowCount = _rowCount;
        colCount = _colCount;
        ghostList = _ghostList;
        keyList = _keyList;
        pacman = _pacman;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field getField(int row, int col)
    {
        return pole[row][col];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GhostObject> getGhosts()
    {
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
    public int getColCount()
    {
        return colCount;
    }

    /**
     * {@inheritDoc}
     */
    public int getRowCount()
    {
        return rowCount;
    }

    // TODO remake
//    /**
//     * Finds all collisions and reacts to them.
//     */
//    public void checkCollision()
//    {
//        for(GhostObject obj: ghostList){
//            if(obj.getField() == pacman.getField()){
//                if(pacman.getLives() == 0){
//                    return;
//                }
//                pacman.loseLive();
//            }
//        }
//    }
}
