package ija.ija2022.homework2.game;

import ija.ija2022.homework2.common.CommonField;
import ija.ija2022.homework2.common.CommonMaze;
import ija.ija2022.homework2.common.CommonMazeObject;

public class GhostObject implements CommonMazeObject {
    private MazeClass ref;
    private int GRow;
    private int GCol;

    public GhostObject(int row, int col){
        GRow = row;
        GCol = col;
        ref = null;
    }

    public boolean canMove(CommonField.Direction dir) // Ověří, zda je možné přesunout objekt zadaným směrem.
    {
        switch (dir) {
            case D:
                if (ref.numRows() > GRow + 1 && ref.getField(GRow + 1, GCol) instanceof PathField) {
                    return true;
                }
                break;
            case L:
                if (0 <= GCol - 1 && ref.getField(GRow, GCol - 1) instanceof PathField) {
                    return true;
                }
                break;
            case R:
                if (ref.numCols() > GCol + 1 && ref.getField(GRow, GCol + 1) instanceof PathField) {
                    return true;
                }
                break;
            case U:
                if (0 <= GRow - 1 && ref.getField(GRow - 1, GCol) instanceof PathField) {
                    return true;
                }
                break;
        }
        return false;
    }

    public boolean move(CommonField.Direction dir) // Přesune objekt na pole v zadaném směru, pokud je to možné.
    {
        if (canMove(dir)) {
            switch (dir) {
                case D:
                    ((PathField)ref.getField(GRow,GCol)).remove(this);
                    ((PathField)ref.getField(GRow+1, GCol)).put(this);
                    ref.getField(GRow, GCol).notifyObservers();
                    GRow++;
                    break;
                case L:
                    ((PathField)ref.getField(GRow,GCol)).remove(this);
                    ((PathField)ref.getField(GRow, GCol-1)).put(this);
                    ref.getField(GRow, GCol).notifyObservers();
                    GCol--;
                    break;
                case R:
                    ((PathField)ref.getField(GRow,GCol)).remove(this);
                    ((PathField)ref.getField(GRow, GCol+1)).put(this);
                    ref.getField(GRow, GCol).notifyObservers();
                    GCol++;
                    break;
                case U:
                    ((PathField)ref.getField(GRow,GCol)).remove(this);
                    ((PathField)ref.getField(GRow-1, GCol)).put(this);
                    ref.getField(GRow, GCol).notifyObservers();
                    GRow--;
                    break;
            }
            ref.getField(GRow, GCol).notifyObservers();
            ref.checkCollision();
            return true;
        }
        return false;
    }

    public CommonField getField() // Vrátí objekt políčka, na kterém je objekt umístěn
    {
        return ref.getField(GRow,GCol);
    }

    public int getLives() // Vrátí aktuální počet životů objektu
    {
        return 1; // cannot kill the ghost
    }

    public void setMaze(CommonMaze maze) // Asociuje políčko s bludištěm, do kterého je vloženo.
    {
        ref = (MazeClass)maze;
    }
}

