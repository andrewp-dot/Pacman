package ija.ija2022.homework2.game;

import ija.ija2022.homework2.common.*;

import java.util.ArrayList;

/*  Třída reprezentující zeď v bludišti. Na políčko nelze vstoupit (vložit objekt). 
    Metody pracující s objekty (put a remove) a okolními poli nextField nejsou implementovány,
    tj. generují výjimku UnsupportedOperationException (při správném použití by neměly být 
    nikdy volány nad objekty této třídy).
*/
public class WallField implements CommonField {
    private int pFRow;
    private int pFCol;

    private ArrayList<Observer> observers;

    // Inicializuje políčko bludiště.
    public WallField(int row, int col) {
        pFRow = row;
        pFCol = col;
        observers = new ArrayList<Observer>();
    }

    public CommonField nextField(CommonField.Direction dirs) // Vrátí sousední pole v daném směru dirs.
    {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() // Test, zda je pole prázdné.
    {
        return true;
    }

    public CommonMazeObject get() // Vrací objekt, který leží na poli.
    {
        return null;
    }

    public boolean canMove() // Vrací informaci, zda je možné na pole umístit objekt (MazeObject).
    {
        return false;
    }

    public boolean contains(CommonMazeObject obj)
    {
        return false;
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
        if (obj instanceof WallField && pFRow == ((WallField) obj).pFRow && pFCol == ((WallField) obj).pFCol) {
            return true;
        }
        return false;
    }
}
