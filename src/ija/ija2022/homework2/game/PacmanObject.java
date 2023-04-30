package ija.ija2022.homework2.game;

import ija.ija2022.homework2.common.CommonField;
import ija.ija2022.homework2.common.CommonMaze;
import ija.ija2022.homework2.common.CommonMazeObject;

public class PacmanObject implements CommonMazeObject {
    private MazeClass ref;
    private int PacRow;
    private int PacCol;
    private int lives;

    public PacmanObject(int row, int col){
        PacRow = row;
        PacCol = col;
        ref = null;
        lives = 3;
    }

    public boolean canMove(CommonField.Direction dir) // Ověří, zda je možné přesunout objekt zadaným směrem.
    {
        switch (dir) {
            case D:
                if (ref.numRows() > PacRow + 1 && ref.getField(PacRow + 1, PacCol) instanceof PathField) {
                    return true;
                }
                break;
            case L:
                if (0 <= PacCol - 1 && ref.getField(PacRow, PacCol - 1) instanceof PathField) {
                    return true;
                }
                break;
            case R:
                if (ref.numCols() > PacCol + 1 && ref.getField(PacRow, PacCol + 1) instanceof PathField) {
                    return true;
                }
                break;
            case U:
                if (0 <= PacRow - 1 && ref.getField(PacRow - 1, PacCol) instanceof PathField) {
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
                    ((PathField)ref.getField(PacRow,PacCol)).remove(this);
                    ((PathField)ref.getField(PacRow+1, PacCol)).put(this);
                    ref.getField(PacRow, PacCol).notifyObservers();
                    PacRow++;
                    break;
                case L:
                    ((PathField)ref.getField(PacRow,PacCol)).remove(this);
                    ((PathField)ref.getField(PacRow, PacCol-1)).put(this);
                    ref.getField(PacRow, PacCol).notifyObservers();
                    PacCol--;
                    break;
                case R:
                    ((PathField)ref.getField(PacRow,PacCol)).remove(this);
                    ((PathField)ref.getField(PacRow, PacCol+1)).put(this);
                    ref.getField(PacRow, PacCol).notifyObservers();
                    PacCol++;
                    break;
                case U:
                    ((PathField)ref.getField(PacRow,PacCol)).remove(this);
                    ((PathField)ref.getField(PacRow-1, PacCol)).put(this);
                    ref.getField(PacRow, PacCol).notifyObservers();
                    PacRow--;
                    break;
            }
            ref.getField(PacRow, PacCol).notifyObservers();
            ref.checkCollision();
            return true;
        }
        return false;
    }

    public boolean isPacman() {return true;}

    public CommonField getField() // Vrátí objekt políčka, na kterém je objekt umístěn
    {
        return ref.getField(PacRow,PacCol);
    }

    public int getLives() // Vrátí aktuální počet životů objektu
    {
        return lives;
    }
    public void loseLive(){
        lives-=1;
    }

    public void setMaze(CommonMaze maze) // Asociuje políčko s bludištěm, do kterého je vloženo.
    {
        ref = (MazeClass)maze;
    }
}
