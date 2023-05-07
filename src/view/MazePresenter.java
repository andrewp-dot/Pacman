package view;

import game.Game;
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
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Observer;

/**
 * Renders {@link game.Game} in javafx gui.
 */
public class MazePresenter implements Observer {
    private final int fieldHeight = 50;
    private final int fieldWidth = fieldHeight;
    private int mazeRowCount;
    private int mazeColumnCount;
    private ImageView[][] fieldObjects;
    private Text keyPickText;
    private Text heartsText;

    private Image pacman;
    private Image ghost;
    private Image key;

    private Game game;

    public MazePresenter(Game _game, Stage stage) {
        this.game = _game;
        this.mazeRowCount = _game.getMaze().getRowCount();
        this.mazeColumnCount = _game.getMaze().getColCount();

        this.fieldObjects = new ImageView[mazeRowCount][mazeColumnCount];
        pacman = new Image("lib/imgs/pacman.png");
        key = new Image("lib/imgs/key.png");
        ghost = new Image("lib/imgs/ghost.png");

        // Create root with score bar
        VBox root = new VBox();
        HBox scoreBar = createScoreBar();

        // Create gridPane with maze map inside.
        GridPane maze = new GridPane();

        // Add score bar and maze to scene
        root.getChildren().addAll(scoreBar, maze);
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
                addEventHandlersStackPane(stackPane, i, j);
                // add background
                ImageView background = new ImageView();
                Field thisField = _game.getMaze().getField(i, j);
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

        addEventHandlersScene(scene);
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
                newImage = key;
            } else {
                newImage = null;
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    fieldObjects[row][col].setImage(newImage);
                }
            });
        }
        else if (obj instanceof Maze){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    int unpickedKeys = 0;
                    for(KeyObject k:game.getMaze().getKeys()){
                        if (!k.getIsPicked()){
                            unpickedKeys++;
                        }
                    }

                    heartsText.setText("Lives: " + game.getMaze().getPacman().getLives());
                    keyPickText.setText("Keys not picked: " + unpickedKeys);
                }
            });
        }
        else{
            throw new UnsupportedOperationException();

        }
    }

    private void addEventHandlersScene(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case UP:
                    case W:
                        game.setDirection(Field.Direction.U);
                        break;
                    case DOWN:
                    case S:
                        game.setDirection(Field.Direction.D);
                        break;
                    case LEFT:
                    case A:
                        game.setDirection(Field.Direction.L);
                        break;
                    case RIGHT:
                    case D:
                        game.setDirection(Field.Direction.R);
                        break;
                    default:
                        System.out.println("Another key was pressed");
                }
            }
        });
    }

    private void addEventHandlersStackPane(StackPane stackPane, int row, int col) {
        stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                game.setDestination(row, col);
            }
        });
    }

    private HBox createScoreBar() {
        int unpickedKeys = 0;
        for(KeyObject k:game.getMaze().getKeys()){
            if (!k.getIsPicked()){
                unpickedKeys++;
            }
        }
        StackPane keyPick = createScoreBarItem("Keys not picked: " + unpickedKeys , Pos.CENTER);
        StackPane hearts = createScoreBarItem("Lives: " + game.getMaze().getPacman().getLives(), Pos.CENTER);

        keyPickText = (Text)keyPick.getChildren().get(0);
        heartsText = (Text)hearts.getChildren().get(0);

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

}
