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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Creates maze from given file and runs.
 */
public class Game {
    private MazeClass maze;

    private Controller controller;

    Field.Direction PacmanMove;
    boolean shouldMove;

    Random rand; // for ghost movement

    //    Timeline ticker;
    Timer timer;

    public Game(Controller controller) {
//        if (Platform.isFxApplicationThread()){
//            System.out.println("I'm in the fx thread");
//        }
//        else{
//            System.out.println("I'm in the game thread");
//        }
        maze = null;
        timer = new Timer();
        rand = new Random();
        this.controller = controller;

//        ticker = new Timeline(new KeyFrame(Duration.millis(250), e -> runTick()));

//        ticker.setCycleCount(Timeline.INDEFINITE);
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
        this.PacmanMove = dir;
        this.shouldMove = true;
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
//        if (Platform.isFxApplicationThread()){
//            System.out.println("I'm in the fx thread");
//        }
//        else{
//            System.out.println("I'm in the game thread");
//        }
//        System.out.println("Name of game thread is " + Thread.currentThread().getName());

        // move pacman
        if (shouldMove) {
            PacmanObject pac = maze.getPacman();
            if (pac.canMove(PacmanMove)) {
                pac.move(PacmanMove);
            }
        }

        // check collision with ghosts
        for(GhostObject g: maze.getGhosts()){
            if(maze.getPacman().getField() == g.getField()){
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
        for(GhostObject g: maze.getGhosts()){
            if(maze.getPacman().getField() == g.getField()){
                maze.getPacman().loseLive();
                System.out.println("You lost live, now you have only " + maze.getPacman().getLives());
                maze.getPacman().teleportTo(maze.getStart().getRow(), maze.getStart().getCol());
                break;
            }
        }

        // check key collision
        for (KeyObject k:maze.getKeys()){
            if(maze.getPacman().getField() == k.getField() && !k.getIsPicked()){
                k.getField().remove(k);
                k.setIsPicked(true);
            }
        }

        // check losing condition
        if (maze.getPacman().getLives() <= 0){
            timer.cancel();
            controller.gameEnded(false);
            return;
        }

        // check winning condition
        if (maze.getEnd() == maze.getPacman().getField()){
            boolean allKeysPicked = true;
            for (KeyObject k:maze.getKeys()){
                if (!k.getIsPicked()){
                    allKeysPicked = false;
                    break;
                }
            }

            if (allKeysPicked){
                timer.cancel();
                controller.gameEnded(true);
                return;
            }
        }
    }

    // TODO add methods for working with changelog
}
