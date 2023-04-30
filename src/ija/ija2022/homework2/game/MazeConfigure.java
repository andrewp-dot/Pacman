package ija.ija2022.homework2.game;

import ija.ija2022.homework2.common.CommonField;
import ija.ija2022.homework2.common.CommonMaze;
import ija.ija2022.homework2.common.CommonMazeObject;

import java.util.ArrayList;
import java.util.List;

/*
 * Třída pro načtení mapového podkladu a vytvoření hry (bludiště). 
 * V této implementaci nenačítá ze souboru, ale přijímá mapový podklad po 
 * řádcích prostřednictvím metod. Implementace pracuje pouze s následujícími 
 * prvky bludiště: 
 * X (zeď, reprezentováno třídou WallField), 
 * . (průchozí políčko, cesta, reprezentováno třídou PathField) 
 * S (výchozí pozice panáčka Pacmana na cestě PathField, při inicializaci bude na políčku s 
 * tímto symbolem umístěn objekt MazeObject reprezentující panáčka). 
 * 
 * Mapový podklad neobsahuje ohraničující zeď. Při vytváření bludiště je toto 
 * ohraničení vygenerováno automaticky. Skutečný rozměr bludiště je tedy 
 * rozměr mapového podkladu zvýšený o hodnotu 2 v každém směru.
 */
public class MazeConfigure {
    private CommonField[][] game;
    private PacmanObject pacman;
    private ArrayList<CommonMazeObject> ghostList;
    private int rowRead;
    private int rowCount;
    private int colCount;
    private boolean isActive;
    private boolean hasStart;

    public MazeConfigure() {
        game = null;
        rowRead = 1;
        isActive = false;
        hasStart = false;
        pacman = null;
        ghostList = new ArrayList<CommonMazeObject>();
    }

    public CommonMaze createMaze() // Vytvoří hru (bludiště) podle načteného mapového podkladu.
    {
        if (!isActive && hasStart && rowRead + 1 == rowCount) {
            MazeClass tmp = new MazeClass(game, rowCount, colCount,pacman, ghostList);
            for(int i = 0;i < rowCount;i++){
                for (int j = 0;j < colCount;j++){
                    // pathfield MUST have a reference to maze otherwise idk how to implement some methods
                    if (tmp.getField(i,j) instanceof PathField)
                    {
                        ((PathField)tmp.getField(i,j)).setMaze(tmp);
                    }
                }
            }

            // pass reference to the objects
            pacman.setMaze(tmp);
            for(CommonMazeObject obj : ghostList ){
                ((GhostObject)obj).setMaze(tmp);
            }

            return tmp;
        }
        return null;
    }

    public boolean processLine(String line) // Načte (příjme) jeden řádek mapového podkladu.
    {
        if (line.length() == colCount-2 && isActive && rowRead < rowCount-1) {
            for (int i = 1; i <= line.length(); i++) {
                switch (line.charAt(i-1)) {
                    case 'X':
                        game[rowRead][i] = new WallField(rowRead, i);
                        break;
                    case '.':
                        game[rowRead][i] = new PathField(rowRead, i);
                        break;
                    case 'S':
                        if (!hasStart) {
                            game[rowRead][i] = new PathField(rowRead, i);
                            pacman = new PacmanObject(rowRead,i);

                            ((PathField)game[rowRead][i]).put(pacman);
                            hasStart = true;
                        }
                        else{
                            isActive = false;
                            return false;
                        }
                        break;
                    case 'G':
                        game[rowRead][i] = new PathField(rowRead, i);
                        GhostObject tmp = new GhostObject(rowRead, i);
                        ((PathField)game[rowRead][i]).put(tmp);
                        ghostList.add(tmp);
                        break;
                    default:
                        isActive = false;
                        return false;
                }
            }
            rowRead++;
            return true;
        }
        isActive = false;
        return false;
    }

    public void startReading(int rows, int cols) // Zahájí čtení mapového podkladu zadaného rozměru.
    {
        game = new CommonField[rows + 2][cols + 2];
        rowCount = rows+2;
        colCount = cols+2;
        isActive = true;
        hasStart = false;
        rowRead = 1;

        // vytvoreni hranic
        // top and bottom line
        for( int i = 0; i< colCount; i++){
            game[0][i] = new WallField(0, i);
            game[rowCount-1][i] = new WallField(rowCount-1, i);
        }
        // left and right
        for( int i = 0; i< rowCount; i++){
            game[i][0] = new WallField(i, 0);
            game[i][colCount-1] = new WallField(i, colCount-1);
        }
    }

    public boolean stopReading() // Ukončí čtení mapového podkladu.
    {
        if (rowRead + 1 == rowCount) {
            isActive = false;
            return true;
        }
        return false;
    }
}
