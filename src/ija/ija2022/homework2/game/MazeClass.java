package ija.ija2022.homework2.game;

import ija.ija2022.homework2.common.CommonField;
import ija.ija2022.homework2.common.CommonMaze;
import ija.ija2022.homework2.common.CommonMazeObject;

import java.util.ArrayList;
import java.util.List;

public class MazeClass implements CommonMaze {
    private CommonField[][] pole;
    private int rowCount;
    private int colCount;
    private PacmanObject pacman;
    private ArrayList<CommonMazeObject> ghostList;

    public MazeClass(CommonField[][] _pole, int _rowCount, int _colCount,
                     PacmanObject _pacman, ArrayList<CommonMazeObject> _ghostList) {
        pole = _pole;
        rowCount = _rowCount;
        colCount = _colCount;
        ghostList = _ghostList;
        pacman = _pacman;
    }

    public CommonField getField(int row, int col) // Metoda vrací pole Field podle zadané pozice.
    {
        return pole[row][col];
    }

    public List<CommonMazeObject> ghosts() // Vrátí seznam všech duchů v bludišti
    {
        return new ArrayList<CommonMazeObject>(ghostList);
    }

    public int numCols() // Vrací počet sloupců desky hry.
    {
        return colCount;
    }

    public int numRows() // Vrací počet řádků desky hry.
    {
        return rowCount;
    }

    public void checkCollision() // zjisti zda pacman neni na stejnem poli jako ghost
    {
        for(CommonMazeObject obj: ghostList){
            if(obj.getField() == pacman.getField()){
                if(pacman.getLives() == 0){
                    return;
                }
                pacman.loseLive();
            }
        }
    }
}
