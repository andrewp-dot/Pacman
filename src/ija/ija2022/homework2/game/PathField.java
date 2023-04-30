package ija.ija2022.homework2.game;

import ija.ija2022.homework2.common.CommonField;
import ija.ija2022.homework2.common.CommonMaze;
import ija.ija2022.homework2.common.CommonMazeObject;
import ija.ija2022.homework2.common.Observable;

import java.util.ArrayList;

public class PathField implements CommonField {
    private int pFRow;
    private int pFCol;
    private MazeClass ref;
    private ArrayList<Observer> observers;

    private ArrayList<CommonMazeObject> mazeObjects;

    // Inicializuje políčko bludiště.
    public PathField(int row, int col) {
        pFRow = row;
        pFCol = col;
        mazeObjects = new ArrayList<CommonMazeObject>();
        observers = new ArrayList<Observer>();
        ref = null;
    }

    public CommonField nextField(CommonField.Direction dirs) // Vrátí sousední pole v daném směru dirs.
    {
        CommonField tmp = null;
        switch (dirs) {
            case D:
                tmp = ref.getField(pFRow + 1, pFCol);
                break;
            case L:
                tmp = ref.getField(pFRow, pFCol - 1);
                break;
            case R:
                tmp = ref.getField(pFRow, pFCol + 1);
                break;
            case U:
                tmp = ref.getField(pFRow - 1, pFCol);
                break;
        }
        return tmp;
    }

    public boolean isEmpty() // Test, zda je pole prázdné.
    {
        return mazeObjects.size() == 0;
    }

    public CommonMazeObject get() // Vrací objekt, který leží na poli.
    {
        return mazeObjects.size() != 0 ? mazeObjects.get(0) : null;
    }

    public boolean canMove() // Vrací informaci, zda je možné na pole umístit objekt (CommonMazeObject).
    {
        return true;
    }

    public boolean contains(CommonMazeObject obj)
    {
        return mazeObjects.contains(obj);
    }

    public void addObserver(Observable.Observer o) // Registruje nový observer
    {
        observers.add(o);
    }

    public void removeObserver(Observable.Observer o) // Odregistruje observer
    {
        observers.remove(o);
    }

    public void notifyObservers() // Notifikuje (informuje) registrované observery, že došlo ke změně stavu objektu
    {
        for(Observer o:observers){
            o.update(this);
        }
    }

    // Objekty jsou shodné, pokud reprezentují stejný typ políčka (políčko na cestě)
    // na stejné pozici (row, col).
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PathField && pFRow == ((PathField) obj).pFRow && pFCol == ((PathField) obj).pFCol) {
            return true;
        }
        return false;
    }

    public boolean put(CommonMazeObject object) // Vloží na pole objekt bludiště.
    {
        mazeObjects.add(object);
        return true;
    }

    public boolean remove(CommonMazeObject object) // Odebere zadaný objekt z pole.
    {
        return mazeObjects.remove(object);
    }

    public void setMaze(CommonMaze maze) // Asociuje políčko s bludištěm, do kterého je vloženo.
    {
        ref = (MazeClass)maze;
    }
}
