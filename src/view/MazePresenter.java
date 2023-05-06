//package view;
//import game.common.Field;
//import game.fields.EndField;
//import game.fields.PathField;
//import game.fields.StartField;
//import game.fields.WallField;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Node;
//import javafx.scene.layout.*;
//import javafx.scene.Scene;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//import javafx.scene.text.Text;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import javafx.scene.text.TextAlignment;
//
///* Custom imports */
//import game.MazeConfigure;
//import game.common.Maze;
//import utils.Observer;
//
///**
// * TODO:
// * Alert box for esc
// * gui in new thread
// * add buttons to move pacman
// */
//public class MazePresenter implements Observer {
//
//    private final int sideSize = 40 ;
//    private StackPane[][] fields = null;
//    private final String map;
//    private Maze maze;
//    private int click = 0;
//    private final Circle pacmanView;
//
//    //for testing gui
//    private int pacRow;
//    private int pacCol;
//
//    /**
//     * TODO:
//     * write update function for observer
//     */
//    @Override
//    public void update(Object obj) {
//        //TODO: delete old pacman
//        Field pacman = this.maze.getPacman().getField();
//        Circle pacmanView = new Circle(10);
//        this.fields[pacman.getRow()][pacman.getCol()].getChildren().add(pacmanView);
//    }
//
//    public static final class FieldType {
//        public static final String WALL = "wallField";
//        public static final String PATH = "pathField";
//        public static final String START = "startField";
//        public static final String END = "endField";
//    }
//
//    public MazePresenter(String map) {
//        this.map = map;
//        this.pacmanView = createPacmanView();
//        setupMaze();
//    }
//
//    public Circle createPacmanView()
//    {
//        Circle pacmanView = new Circle(15);
//        pacmanView.setId("pacman");
//        return pacmanView;
//    }
//
//    public Scene CreatePacmanScene(){
//        VBox root = new VBox();
//        HBox scoreBar = createScoreBar();
//        GridPane layout = new GridPane();
//
//        layout.setPadding(new Insets(10,10,10,10));
//
//        addFieldRepresentation();
//        renderMaze(layout, fields);
//        root.getChildren().addAll(scoreBar,layout);
//
//        Scene pacman_scene = new Scene(root);
//        pacman_scene.setOnKeyPressed(keyEvent -> {
//            switch (keyEvent.getCode())
//            {
//                case W:
//                    if(fields[pacRow - 1][pacCol].getId() != FieldType.WALL)
//                    {
//                        pacRow -= 1;
//                        fields[pacRow][pacCol].getChildren().add(pacmanView);
//                    }
//                    break;
//                case A:
//                    if(fields[pacRow][pacCol - 1].getId() != FieldType.WALL)
//                    {
//                        pacCol -= 1;
//                        fields[pacRow][pacCol].getChildren().add(pacmanView);
//                    }
//                    break;
//                case S:
//
//                    if(fields[pacRow + 1][pacCol].getId() != FieldType.WALL)
//                    {
//                        pacRow += 1;
//                        fields[pacRow][pacCol].getChildren().add(pacmanView);
//                    }
//                    break;
//                case D:
//                    if(fields[pacRow][pacCol + 1].getId() != FieldType.WALL)
//                    {
//                        pacCol += 1;
//                        fields[pacRow][pacCol].getChildren().add(pacmanView);
//                    }
//                    break;
//                default:
//                    break;
//            }
//
//        });
//        pacman_scene.getStylesheets().add("styles/maze.css");
//        System.out.println("PacmanWindow created.");
//        return pacman_scene;
//    }
//
//    public void setMaze(Maze maze) { this.maze = maze; }
//
//    /**
//     * Renders pacman maze
//     * @param layout - grid pane for pacman maze
//     * @param fields - array of fields to be rendered as rectangle
//     */
//    private void renderMaze(GridPane layout, StackPane[][] fields)
//    {
//        int col = 0;
//        for(int row = 0; row < fields.length; row++)
//        {
//            for(StackPane field: fields[row]) {
//                GridPane.setConstraints(field,col ,row);
//                layout.add(field,col,row);
//                col += 1;
//            }
//            col = 0;
//        }
//    }
//
//    private HBox createScoreBar()
//    {
//        StackPane score = createScoreBarItem("Time: Cilcked: " + click,Pos.TOP_LEFT);
//        score.setOnMouseClicked(mouseEvent ->
//        {
//            //TODO: set timer here
//            click += 1;
//            getTextFromPane(score).setText("Time: Clicked: " + click);
//        });
//
//        StackPane keyPick = createScoreBarItem("Key: no",Pos.CENTER);
//        StackPane hearts = createScoreBarItem("Hearts: HEARTSNUM",Pos.TOP_RIGHT);
//
//        HBox scoreBar = new HBox(score,keyPick,hearts);
//        HBox.setHgrow(score, Priority.ALWAYS);
//        HBox.setHgrow(hearts, Priority.ALWAYS);
//        scoreBar.setId("scoreBar");
//        scoreBar.setSpacing(10);
//        return scoreBar;
//    }
//
//    /**
//     * Creates score bar item
//     * @param text output text
//     * @return score bar item stack pane
//     */
//    private StackPane createScoreBarItem(String text, Pos alignment)
//    {
//        StackPane scoreBarOption = new StackPane();
//        scoreBarOption.setPrefWidth(100);
//        scoreBarOption.setAlignment(alignment);
//        Text txt = new Text(text);
//        txt.setTextAlignment(TextAlignment.CENTER);
//        txt.setFill(Color.rgb(220,220,220));
//        scoreBarOption.getChildren().add(txt);
//        return scoreBarOption;
//    }
//
//    /**
//     * Find text in pane children
//     * @param pane searched pane
//     * @return text object
//     */
//    private Text getTextFromPane(StackPane pane)
//    {
//        for(Node child: pane.getChildren())
//        {
//            if(child instanceof Text) return (Text) child;
//        }
//        return new Text();
//    }
//
//    private Integer[] findNumbers(String str) throws Exception
//    {
//        Integer[] nums = new Integer[2];
//        Pattern findNumsPattern = Pattern.compile("\\d+");
//
//        // TODO: FIX THIS
//        if(!str.matches("^[0-9 ]+$") || str.isEmpty())
//        {
//            throw new Exception("Wrong format of map.");
//        }
//
//        Matcher matcher = findNumsPattern.matcher(str);
//        int i = 0;
//        while (matcher.find()) {
//            String number = matcher.group();  // extract the matched number as a string
//            try
//            {
//                nums[i] = Integer.parseInt(number);  // convert the string to an integer
//            }
//            catch (Exception e)
//            {
//                System.err.println(e.getMessage());
//                break;
//            }
//            i += 1;
//        }
//        return nums;
//    }
//
//    /**
//     * Sets maze for rendering
//     */
//    private void setupMaze()
//    {
//        MazeConfigure mazeConfig = new MazeConfigure();
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("src/maps/" + this.map));
//
//            String line = reader.readLine();
//            System.out.println(line);
//            Integer[] size;
//            try {
//                size = findNumbers(line);
//            }
//            catch (Exception e) {
//                System.err.println(e.getMessage());
//                return;
//            }
//
//            // load maze
//            mazeConfig.startReading(size[0],size[1]);
//            line = reader.readLine();
//            System.out.println(line);
//            while (line != null)
//            {
//                System.out.println(line);
//                mazeConfig.processLine(line);
//                line = reader.readLine();
//
//            }
//            this.maze = mazeConfig.createMaze();
//            mazeConfig.stopReading();
//            this.fields = new StackPane[this.maze.getRowCount()][this.maze.getColCount()];
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Adds field as Rectangle to fields array
//     */
//
//    //TODO: FIX THIS METHOD
//    private void addField(Field field)
//    {
//        StackPane tile = new StackPane();
//        setupField(tile,field.getRow(),field.getCol());
//        if(field instanceof WallField) tile.setId(FieldType.WALL);
//        else if (field instanceof PathField) {
//            if (field instanceof StartField)
//            {
//                pacRow = field.getRow();
//                pacCol = field.getCol();
//                tile.getChildren().add(pacmanView);
//                tile.setId(FieldType.START);
//            }
//            else if (field instanceof EndField) tile.setId(FieldType.END);
//            else tile.setId(FieldType.PATH);
//        }
//        this.fields[field.getRow()][field.getCol()] = tile;
//    }
//
//    /**
//     * Sets basic params and mouse events to field
//     * @param tile Field got by maze
//     */
//    private void setupField(StackPane tile, int row, int col)
//    {
//        tile.setPrefHeight(sideSize);
//        tile.setPrefWidth(sideSize);
//        tile.setOnMouseClicked(mouseEvent ->
//        {
//            System.out.println("x: " + col + "y: "  + row);
//            if(tile.getId() != FieldType.WALL && tile.getChildren().isEmpty())
//            {
//                pacRow = row;
//                pacCol = col;
//                tile.getChildren().add(pacmanView);
//                tile.requestLayout();
//            }
//        });
//    }
//
//    /**
//     * adds field to array of field representation
//     */
//    private void addFieldRepresentation()
//    {
//        for (int row = 0; row < maze.getColCount(); row++)
//        {
//            for (int col = 0; col < maze.getRowCount(); col++)
//            {
//                addField(this.maze.getField(row,col));
//            }
//        }
//    }
//}


package view;

import game.Game;
import game.astar.Astar;
import game.common.Field;
import game.common.FieldObject;
import game.fieldObjects.GhostObject;
import game.fieldObjects.KeyObject;
import game.fieldObjects.PacmanObject;
import game.fields.EndField;
import game.fields.PathField;
import game.fields.StartField;
import game.fields.WallField;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.Observer;

import java.util.ArrayList;

/**
 * Renders {@link game.Game} in javafx gui.
 */
public class MazePresenter implements Observer {
    private final int fieldHeight = 50;
    private final int fieldWidth = fieldHeight;
    private int mazeRowCount;
    private int mazeColumnCount;
    private ImageView[][] fieldObjects;
    private Image pacman;
    private Image ghost;
    private Image key;

    private Game game;

    public MazePresenter(Game _game, Stage stage) {
        this.game = _game;
        this.mazeRowCount = _game.getMaze().getRowCount();
        this.mazeColumnCount = _game.getMaze().getColCount();

        this.fieldObjects = new ImageView[mazeRowCount][mazeColumnCount];
        pacman = new Image("imgs/pacman.png");
        key = new Image("imgs/key.png");
        ghost = new Image("imgs/ghost.png");

        // Create gridPane with maze map inside.
        GridPane root = new GridPane();
        Scene scene = new Scene(root);

        Image path = new Image("imgs/path.png");
        Image wall = new Image("imgs/wall.jpg");
        Image end = new Image("imgs/target.png");
        Image start = new Image("imgs/start.png");

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

                root.add(stackPane, j, i);
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
        if (!(obj instanceof PathField)) {
            throw new UnsupportedOperationException();
        }

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
                System.out.println("You have clicked on row" + row + "and col" + col);

                // demostracia a star -> vypis v konzole
                Astar a = new Astar(game.getMaze().getMap(),game.getMaze().getMap()[9][1],game.getMaze().getMap()[row][col]);
                ArrayList<Field.Direction> display = a.aStar();
                for (Field.Direction dir: display)
                {
                    switch (dir)
                    {
                        case D -> System.out.println("Down");
                        case U -> System.out.println("Up");
                        case L -> System.out.println("Left");
                        case R -> System.out.println("Right");
                    }
                }
            }
        });
    }
}
