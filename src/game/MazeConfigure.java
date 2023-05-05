
package game;

import game.common.Field;
import game.common.Maze;
import game.fieldObjects.GhostObject;
import game.fieldObjects.KeyObject;
import game.fields.EndField;
import game.fields.PathField;
import game.fields.StartField;
import game.fields.WallField;
import game.fieldObjects.PacmanObject;

import java.util.ArrayList;
import java.util.List;

public class MazeConfigure{
    private int rows = 0;
    private int cols = 0;
    private int read_lines = 0;
    private boolean isReading = false;
    private ArrayList<ArrayList<Field>> map = new ArrayList<>();
    private ArrayList<GhostObject> ghosts = new ArrayList<>();
    private ArrayList<KeyObject> keys = new ArrayList<>();
    private PacmanObject pacman = null;

    public Maze createMaze(){
        if(map.size() != this.rows) return null;
        for(ArrayList<Field> row: map) {
            if(row.size() != this.cols) return null;
        }

        // converts arrayList to array
        Field[][] mazeMap = new Field[rows][cols];
        for (int i = 0;i<rows;i++){
            for (int j =0;j<cols;j++){
                mazeMap[i][j] = map.get(i).get(j);
            }
        }
        // create maze
        MazeClass mazeClass = new MazeClass(mazeMap, rows, cols, pacman, ghosts, keys);

        // TODO add Observer
        // add Maze references to objects in mazeClass
        for (int i=0;i<mazeClass.getRowCount();i++){
            for(int j=0;j<mazeClass.getColCount();j++){
                mazeClass.getField(i,j).setMaze(mazeClass);
            }
        }
        mazeClass.getPacman().setMaze(mazeClass);
        for(KeyObject key :mazeClass.getKeys()){
            key.setMaze(mazeClass);
        }
        for(GhostObject ghost: mazeClass.getGhosts()){
            ghost.setMaze(mazeClass);
        }

        return mazeClass;
    }

    /**
     * Funkcia prida do pola horizontalnu stenu
     */
    private void add_horizontal_wall(int row_index){
        this.map.add(row_index,new ArrayList<>(0));
        for (int i = 0; i < cols; i++) {
            this.map.get(row_index).add(new WallField(row_index,i));
        }
        read_lines++;
    }
    /**
     * Funckia pre nacitanie a overenie spravnosti nacitania mapoveho podl
     * @param line - nacitacia linka
     * @return uspesnost nacitanie
     */
    public boolean processLine(String line){
        if(!this.isReading || read_lines > this.rows-1 || //pozor na regez, to Z tam mozno nepatri
                line.length() != this.cols-2 || !line.matches("^[TXGK.S]*$")) return false;

        this.map.add(new ArrayList<>(0));

        this.map.get(read_lines).add(new WallField(read_lines,0));
        for(int col = 0; col < cols-2 ; col++)
        {
            if(line.charAt(col) == 'X')
            {
                this.map.get(read_lines).add(new WallField(read_lines,col+1));

            }
            else
            {
                if(line.charAt(col) == 'S')
                {
                    this.map.get(read_lines).add(new StartField(read_lines,col+1));
                    PacmanObject pacman = new PacmanObject(read_lines,col+1);
//                   pacman.setMaze(this);
                    this.pacman = pacman;
                    this.map.get(read_lines).get(col+1).put(pacman);
                }
                else if(line.charAt(col) == 'T') this.map.get(read_lines).add(new EndField(read_lines,col+1));
                else {
                    this.map.get(read_lines).add(new PathField(read_lines,col+1));
                    if(line.charAt(col) == 'G')
                    {
                        GhostObject ghost = new GhostObject(read_lines,col + 1);
                        this.map.get(read_lines).get(col+1).put(ghost);
                    }
                }
            }
//            this.map.get(read_lines).get(col+1).setMaze(this);
        }
        this.map.get(read_lines).add(new WallField(read_lines,cols-1));

        this.read_lines++;

        if(read_lines == this.rows-1)
        {
            add_horizontal_wall(read_lines);
        }
        return true;
    }

    /**
     * Nastavi hodnotu parametru isReading na true
     * @param rows pocet citanych riadkov
     * @param cols pocet citanych stlpcov
     */
    public void startReading(int rows, int cols){
        this.isReading = true;
        this.rows = rows + 2;
        this.cols = cols + 2;
        add_horizontal_wall(0);
    }

    /**
     * Nastavi hodnotu parametru isReading na true
     * @return true/false podla toho, ci sa nastavenie podarilo
     */
    public boolean stopReading(){
        if(!this.isReading) return false;
        this.isReading = false;
        return true;
    }

//    @Override
//    public Field getField(int row, int col) {
//        if(row >= this.rows || col >= this.cols ) return null;
//        return map.get(row).get(col);
//    }

//    @Override
//    public int getRowCount() {
//        return this.rows;
//    }

//    @Override
//    public int getColCount() {
//        return this.cols;
//    }

//    @Override
//    public List<GhostObject> getGhosts() { return this.ghosts; }

//    @Override
//    public List<KeyObject> getKeys() { return this.keys; }

//    @Override
//    public PacmanObject getPacman() { return this.pacman; }
}