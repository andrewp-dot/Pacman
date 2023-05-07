package view;

import changelog.Changelog;
import changelog.Coordinates;
import game.Game;
import game.MazeClass;
import game.common.Field;
import game.common.FieldObject;
import game.common.Maze;
import game.fieldObjects.GhostObject;
import game.fieldObjects.KeyObject;
import game.fieldObjects.PacmanObject;
import game.fields.EndField;
import game.fields.PathField;
import game.fields.StartField;
import game.fields.WallField;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Observer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Renders {@link game.Game} in javafx gui.
 */
public class MazePresenterLog implements Observer {
    private final int fieldHeight = 50;
    private final int fieldWidth = fieldHeight;
    private int mazeRowCount;
    private int mazeColumnCount;
    private ImageView[][] fieldObjects;
    private Image pacman;
    private Image ghost;
    private Image key;
    private Text keyPickText;
    private Text heartsText;

    private MazeClass mazeClass;

    private Scene replays;
    private Stage stage;
    private Changelog changelog;
    private int currentTick;

    private Lock currentTickLock;
    private Timer timer;
    public static boolean terminate = false;


    public MazePresenterLog(Changelog changelog, Stage stage, Scene replays) {
        this.mazeClass = changelog.maze;
        this.changelog = changelog;
        this.mazeRowCount = mazeClass.getRowCount();
        this.mazeColumnCount = mazeClass.getColCount();
        this.replays = replays;
        this.stage = stage;
        this.currentTick = 0;
        this.currentTickLock = new ReentrantLock();

        this.fieldObjects = new ImageView[mazeRowCount][mazeColumnCount];
        pacman = new Image("lib/imgs/pacman.png");
        key = new Image("lib/imgs/key.png");
        ghost = new Image("lib/imgs/ghost.png");

        // Create root with score bar, actions
        VBox root = new VBox();
        HBox scoreBar = createScoreBar();
        HBox actions = createActions();

        // Create gridPane with maze map inside.
        GridPane maze = new GridPane();

        // Add score bar and maze to scene
        root.getChildren().addAll(scoreBar, maze, actions);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/maze.css");

        Image path = new Image("lib/imgs/path.png");
        Image wall = new Image("lib/imgs/wall.jpg");
        Image end = new Image("lib/imgs/target.png");
        Image start = new Image("lib/imgs/start.png");

        for (int i = 0; i < mazeRowCount; i++) {
            for (int j = 0; j < mazeColumnCount; j++) {
                // create StackPane
                StackPane stackPane = new StackPane();
//                addEventHandlersStackPane(stackPane, i, j);
                // add background
                ImageView background = new ImageView();
                Field thisField = mazeClass.getField(i, j);
                if (thisField instanceof EndField) {
                    background.setImage(end);
                } else if (thisField instanceof WallField) {
                    background.setImage(wall);
                } else if (thisField instanceof StartField) {
                    background.setImage(start);
                } else {
                    background.setImage(path);
                }
                background.setFitWidth(fieldHeight);
                background.setFitHeight(fieldHeight);
                stackPane.getChildren().add(background);
                // add item if any
                ImageView item = new ImageView();
                if (thisField.canMove()) {
                    FieldObject lastObject = thisField.getLast();
                    setObject(item, lastObject);
                } else {
                    setObject(item, null);
                }
                fieldObjects[i][j] = item;
                stackPane.getChildren().add(item);

                maze.add(stackPane, j, i);
            }
        }

//        addEventHandlersScene(scene);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    private void setObject(ImageView imageView, FieldObject fieldObject) {
        if (fieldObject == null) {
            imageView.setImage(null);
        } else if (fieldObject instanceof GhostObject) {
            imageView.setImage(ghost);
        } else if (fieldObject instanceof PacmanObject) {
            imageView.setImage(pacman);
        } else {
            imageView.setImage(key);
        }
        imageView.setFitHeight(fieldHeight);
        imageView.setFitWidth(fieldWidth);
    }

    @Override
    public void update(Object obj) {
        if (obj instanceof PathField) {
            PathField updated = (PathField) obj;
            FieldObject last = updated.getLast();
            int row = updated.getRow();
            int col = updated.getCol();

            Image newImage;
            if (last instanceof GhostObject) {
                newImage = ghost;
            } else if (last instanceof PacmanObject) {
                newImage = pacman;
            } else if (last instanceof KeyObject) {
                if (((KeyObject) last).getIsPicked()) {
                    newImage = null;
                } else {
                    newImage = key;
                }
            } else {
                newImage = null;
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    fieldObjects[row][col].setImage(newImage);
                }
            });
        } else if (obj instanceof Maze) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    int unpickedKeys = 0;
                    for (KeyObject k : mazeClass.getKeys()) {
                        if (!k.getIsPicked()) {
                            unpickedKeys++;
                        }
                    }

                    heartsText.setText("Lives: " + mazeClass.getPacman().getLives());
                    keyPickText.setText("Keys not picked: " + unpickedKeys);
                }
            });
        } else {
            throw new UnsupportedOperationException();
        }
    }

//    private void addEventHandlersScene(Scene scene) {
//        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                switch (keyEvent.getCode()) {
//                    case UP:
//                    case W:
//                        game.setDirection(Field.Direction.U);
//                        break;
//                    case DOWN:
//                    case S:
//                        game.setDirection(Field.Direction.D);
//                        break;
//                    case LEFT:
//                    case A:
//                        game.setDirection(Field.Direction.L);
//                        break;
//                    case RIGHT:
//                    case D:
//                        game.setDirection(Field.Direction.R);
//                        break;
//                    default:
//                        System.out.println("Another key was pressed");
//                }
//            }
//        });
//    }
//
//    private void addEventHandlersStackPane(StackPane stackPane, int row, int col) {
//        stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                game.setDestination(row, col);
//            }
//        });
//    }

    private HBox createScoreBar() {
        int unpickedKeys = 0;
        for (KeyObject k : mazeClass.getKeys()) {
            if (!k.getIsPicked()) {
                unpickedKeys++;
            }
        }
        StackPane keyPick = createScoreBarItem("Keys not picked: " + unpickedKeys, Pos.CENTER);
        StackPane hearts = createScoreBarItem("Lives: " + mazeClass.getPacman().getLives(), Pos.CENTER);

        keyPickText = (Text) keyPick.getChildren().get(0);
        heartsText = (Text) hearts.getChildren().get(0);

        HBox scoreBar = new HBox(keyPick, hearts);
        HBox.setHgrow(keyPick, Priority.ALWAYS);
        HBox.setHgrow(hearts, Priority.ALWAYS);
        scoreBar.setId("scoreBar");
        return scoreBar;
    }

    /**
     * Creates score bar item
     *
     * @param text output text
     * @return score bar item stack pane
     */
    private StackPane createScoreBarItem(String text, Pos alignment) {
        StackPane scoreBarOption = new StackPane();
        scoreBarOption.setId("scoreBarOption");
        scoreBarOption.setAlignment(alignment);
        Text txt = new Text(text);
        txt.setFill(Color.rgb(220, 220, 220));
        scoreBarOption.getChildren().add(txt);
        return scoreBarOption;
    }

    private HBox createActions() {
        // create buttons
        Button btnBack = new Button("back to replays menu");
        Button btnStart = new Button("start");
        Button btnPlayReverse = new Button("play reverse");
        Button btnPrevious = new Button("previous");
        Button btnNext = new Button("next");
        Button btnPlay = new Button("play");
        Button btnEnd = new Button("end");

        // set buttons actions
        btnBack.setOnMouseClicked(mouseEvent -> {
            try {
                if (timer != null){
                    timer.cancel();
                    timer.purge();
                }
            } catch (Exception ignored) {
            }
            back();
        });
        btnStart.setOnMouseClicked(mouseEvent -> {
            try {
                if (timer != null){
                    timer.cancel();
                    timer.purge();
                }
            } catch (Exception ignored) {
            }
            start();
        });
        btnPlayReverse.setOnMouseClicked(mouseEvent -> {
            try {
                if (timer != null){
                    timer.cancel();
                    timer.purge();
                }
            } catch (Exception ignored) {
            }
            playReverse();
        });
        btnPrevious.setOnMouseClicked(mouseEvent -> {
            try {
                if (timer != null){
                    timer.cancel();
                    timer.purge();
                }
            } catch (Exception ignored) {
            }
            previous();
        });
        btnNext.setOnMouseClicked(mouseEvent -> {
            try {
                if (timer != null){
                    timer.cancel();
                    timer.purge();
                }
            } catch (Exception ignored) {
            }
            next();
        });
        btnPlay.setOnMouseClicked(mouseEvent -> {
            try {
                if (timer != null) {
                    timer.cancel();
                    timer.purge();
                }
            } catch (Exception ignored) {
            }
            play();
        });
        btnEnd.setOnMouseClicked(mouseEvent -> {
            try {
                if (timer != null) {
                    timer.cancel();
                    timer.purge();
                }
            } catch (Exception ignored) {
            }
            end();
        });


        HBox actions = new HBox(btnBack, btnStart, btnPlayReverse, btnPrevious, btnNext, btnPlay, btnEnd);
        actions.setAlignment(Pos.CENTER);
        actions.setSpacing(5);
        actions.setId("actions");
        return actions;
    }

    private void back() {
        stage.setScene(replays);
    }

    /**
     * Go back to first tick
     */
    private void start() {
        this.currentTickLock.lock();
        this.currentTick = 0;
        this.currentTickLock.unlock();
        goToTick(0);
    }

    private void playReverse() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (terminate) {
                    timer.cancel();
                    return;
                }
                previous();
            }
        }, 0, 250);
    }

    private void previous() {
        this.currentTickLock.lock();
        if (this.currentTick > 0) {
            int tick = --this.currentTick;
            this.currentTickLock.unlock();
            goToTick(tick);
        } else {
            timer.cancel();
            this.currentTickLock.unlock();
        }
    }

    private void next() {
        this.currentTickLock.lock();
        if (changelog.tickCount - 1 > this.currentTick) {
            int tick = ++this.currentTick;
            this.currentTickLock.unlock();
            goToTick(tick);
        } else {
            timer.cancel();
            this.currentTickLock.unlock();
        }
    }

    private void play() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (terminate) {
                    timer.cancel();
                    return;
                }
                next();
            }
        }, 0, 250);
    }

    private void end() {
        this.currentTickLock.lock();
        this.currentTick = changelog.tickCount - 1;
        this.currentTickLock.unlock();
        goToTick(changelog.tickCount - 1);
    }

    private void goToTick(int tick) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                // move pacman
                Coordinates coord = changelog.pacmanCoords.get(tick);
                mazeClass.getPacman().teleportTo(coord.row, coord.col);

                // move ghosts
                for (int i = 0; i < mazeClass.getGhosts().size(); i++) {
                    GhostObject g = mazeClass.getGhosts().get(i);
                    coord = changelog.ghostsCoors.get(i).get(tick);
                    g.teleportTo(coord.row, coord.col);
                }

                // pick/activate keys
                for (int i = 0; i < mazeClass.getKeys().size(); i++) {
                    KeyObject k = mazeClass.getKeys().get(i);
                    boolean picked = changelog.keysArePicked.get(i).get(tick);
                    k.setIsPicked(picked);
                }

                // update pacman lives
                mazeClass.getPacman().setLives(changelog.pacmanLives.get(tick));
                return null;
            }
        };

        new Thread(task).start();
    }
}
