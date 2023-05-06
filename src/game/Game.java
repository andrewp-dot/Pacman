package game;

import game.common.Field;
import game.fieldObjects.GhostObject;
import game.fieldObjects.KeyObject;
import game.fieldObjects.PacmanObject;
import utils.Observable;
import utils.Observer;
import view.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Creates maze from given file and runs.
 */
public class Game {
    // properties below are synchronized, access strictly through getter and setter methods!!
    private MovementType pacmanMoveType;
    private Field.Direction pacmanMove;
    private ArrayList<Field.Direction> pacmanMovs; // moves computed by A*
    private int pacmanMovsIndex; // index of the next move
    private boolean shouldMove;
    private int destinationRow;
    private int destinationCol;
    private Lock lock;
    // end of synchronized properties


    private MazeClass maze;
    private Controller controller;
    Random rand; // for ghost movement
    Timer timer;

    public static boolean terminate = false;

    enum MovementType {
        DIRECTION,
        DESTINATION
    }

    public Game(Controller controller) {
        maze = null;
        timer = new Timer();
        rand = new Random();
        this.controller = controller;
        this.pacmanMoveType = MovementType.DIRECTION;
        this.shouldMove = false;
        pacmanMovs = null;
        lock = new ReentrantLock();
    }

    /**
     * Loads maze from given file and adds observer to observable fields.
     *
     * @param file file to load maze from.
     */
    public void loadMaze(File file) throws Exception {
        MazeConfigure conf = new MazeConfigure();
        FileReader fr = new FileReader(file);
        BufferedReader buffr = new BufferedReader(fr);

        if (!buffr.ready()) {
            throw new Exception("File is empty.");
        }

        String line = buffr.readLine();
        String[] rowsCols = line.split(" ");
        if (rowsCols.length != 2) {
            throw new Exception("Invalid maze map format.");
        }

        conf.startReading(Integer.parseInt(rowsCols[0]), Integer.parseInt(rowsCols[1]));
        while (buffr.ready()) {
            if (!conf.processLine(buffr.readLine())) {
                throw new Exception("Invalid maze map format.");
            }
        }

        if (!conf.stopReading()) {
            throw new Exception("Invalid maze map format.");
        }
        maze = conf.createMaze();
    }

    public MazeClass getMaze() {
        return maze;
    }

    public void addObserver(Observer observer) {
        for (int i = 0; i < maze.getRowCount(); i++) {
            for (int j = 0; j < maze.getColCount(); j++) {
                if (maze.getField(i, j) instanceof Observable) {
                    ((Observable) maze.getField(i, j)).addObserver(observer);
                }
            }
        }
    }

    public void setDirection(Field.Direction dir) {
        lock.lock();
        this.pacmanMove = dir;
        this.pacmanMoveType = MovementType.DIRECTION;
        this.shouldMove = true;
        lock.unlock();
    }

    public Field.Direction getDirection() {
        try {
            lock.lock();
            return this.pacmanMove;
        } finally // finally block executed always before return
        {
            lock.unlock();
        }
    }

    public Field.Direction getDirectionsDirection() {
        try {
            lock.lock();
            return this.pacmanMovs.get(this.pacmanMovsIndex);
        } finally // finally block executed always before return
        {
            lock.unlock();
        }
    }

    public void setDirections(ArrayList<Field.Direction> directions) {
        lock.lock();
        this.pacmanMovs = directions;
        this.pacmanMoveType = MovementType.DESTINATION;
        this.shouldMove = true;
        lock.unlock();
    }

    public void setDestination(int row, int col){
        lock.lock();
        this.destinationRow = row;
        this.destinationCol = col;
        lock.unlock();
    }

    /**
     * @return Array of two ints. Array[0] is destination row index, array[1] is destination column index.
     */
    public int[] getDestination(){
        try {
            lock.lock();
            return new int[] {this.destinationRow, this.destinationCol};
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Determines the pacman next direction of movement. Calls the right getter depending on movement type
     * and returns appropriate direction.
     * @return Next direction of pacman movement.
     */
    private Field.Direction getNextDirection(){
        try{
            lock.lock();
            if (this.pacmanMoveType == MovementType.DIRECTION){
                return this.getDirection();
            }
            return this.getDirectionsDirection();
        }
        finally  // finally block executed always before return
        {
            this.pacmanMovsIndex++;
            lock.unlock();
        }
    }

    public void play() {
//        ticker.play();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runTick();
            }
        }, 0, 250);
    }

    private void runTick() {
        if (terminate){
            timer.cancel();
            return;
        }

        // move pacman
        if (shouldMove) {
            PacmanObject pac = maze.getPacman();
            Field.Direction direction = getNextDirection();
            if (pac.canMove(direction)) {
                pac.move(direction);
            }
        }

        // check collision with ghosts
        for (GhostObject g : maze.getGhosts()) {
            if (maze.getPacman().getField() == g.getField()) {
                maze.getPacman().loseLive();
                System.out.println("You lost live, now you have only " + maze.getPacman().getLives());
                maze.getPacman().teleportTo(maze.getStart().getRow(), maze.getStart().getCol());
                break;
            }
        }

        // move ghosts
        // so far random movement
        for (GhostObject g : maze.getGhosts()) {
            Field.Direction dir = Field.Direction.L; //LEFT
            switch (rand.nextInt(4)) {
                case 1: // RIGHT
                    dir = Field.Direction.R;
                    break;
                case 2: // DOWN
                    dir = Field.Direction.D;
                    break;
                case 3: // UP
                    dir = Field.Direction.U;
                    break;
            }
            if (g.canMove(dir)) {
                g.move(dir);
            }
        }

        // check collision
        // check collision with ghosts
        for (GhostObject g : maze.getGhosts()) {
            if (maze.getPacman().getField() == g.getField()) {
                maze.getPacman().loseLive();
                System.out.println("You lost live, now you have only " + maze.getPacman().getLives());
                maze.getPacman().teleportTo(maze.getStart().getRow(), maze.getStart().getCol());
                break;
            }
        }

        // check key collision
        for (KeyObject k : maze.getKeys()) {
            if (maze.getPacman().getField() == k.getField() && !k.getIsPicked()) {
                k.getField().remove(k);
                k.setIsPicked(true);
            }
        }

        // check losing condition
        if (maze.getPacman().getLives() <= 0) {
            timer.cancel();
            controller.gameEnded(false);
            return;
        }

        // check winning condition
        if (maze.getEnd() == maze.getPacman().getField()) {
            boolean allKeysPicked = true;
            for (KeyObject k : maze.getKeys()) {
                if (!k.getIsPicked()) {
                    allKeysPicked = false;
                    break;
                }
            }

            if (allKeysPicked) {
                timer.cancel();
                controller.gameEnded(true);
                return;
            }
        }
    }

    // TODO add methods for working with changelog
}
