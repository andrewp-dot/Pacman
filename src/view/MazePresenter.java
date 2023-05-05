package view;
import game.common.Field;
import game.fields.EndField;
import game.fields.PathField;
import game.fields.StartField;
import game.fields.WallField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Custom imports */
import game.MazeConfigure;
import game.common.Maze;
import javafx.scene.text.TextAlignment;
import utils.Observer;

/**
 * TODO:
 * Alert box for esc
 * gui in new thread
 * add buttons to move pacman
 */
public class MazePresenter implements Observer {

    private final int sideSize = 40 ;
    private StackPane[][] fields = null;
    private final String map;
    private Maze maze;
    private int click = 0;
    private final Circle pacmanView;

    //for testing gui
    private int pacRow;
    private int pacCol;

    /**
     * TODO:
     * write update function for observer
     */
    @Override
    public void update(Object obj) {
        //TODO: delete old pacman
        Field pacman = this.maze.getPacman().getField();
        Circle pacmanView = new Circle(10);
        this.fields[pacman.getRow()][pacman.getCol()].getChildren().add(pacmanView);
    }

    public static final class FieldType {
        public static final String WALL = "wallField";
        public static final String PATH = "pathField";
        public static final String START = "startField";
        public static final String END = "endField";
    }

    public MazePresenter(String map) {
        this.map = map;
        this.pacmanView = createPacmanView();
        setupMaze();
    }

    public Circle createPacmanView()
    {
        Circle pacmanView = new Circle(15);
        pacmanView.setId("pacman");
        return pacmanView;
    }

    public Scene CreatePacmanScene(){
        VBox root = new VBox();
        HBox scoreBar = createScoreBar();
        GridPane layout = new GridPane();

        layout.setPadding(new Insets(10,10,10,10));

        addFieldRepresentation();
        renderMaze(layout, fields);
        root.getChildren().addAll(scoreBar,layout);

        Scene pacman_scene = new Scene(root);
        pacman_scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode())
            {
                case W:
                    if(fields[pacRow - 1][pacCol].getId() != FieldType.WALL)
                    {
                        pacRow -= 1;
                        fields[pacRow][pacCol].getChildren().add(pacmanView);
                    }
                    break;
                case A:
                    if(fields[pacRow][pacCol - 1].getId() != FieldType.WALL)
                    {
                        pacCol -= 1;
                        fields[pacRow][pacCol].getChildren().add(pacmanView);
                    }
                    break;
                case S:

                    if(fields[pacRow + 1][pacCol].getId() != FieldType.WALL)
                    {
                        pacRow += 1;
                        fields[pacRow][pacCol].getChildren().add(pacmanView);
                    }
                    break;
                case D:
                    if(fields[pacRow][pacCol + 1].getId() != FieldType.WALL)
                    {
                        pacCol += 1;
                        fields[pacRow][pacCol].getChildren().add(pacmanView);
                    }
                    break;
                default:
                    break;
            }

        });
        pacman_scene.getStylesheets().add("styles/maze.css");
        System.out.println("PacmanWindow created.");
        return pacman_scene;
    }

    public void setMaze(Maze maze) { this.maze = maze; }

    /**
     * Renders pacman maze
     * @param layout - grid pane for pacman maze
     * @param fields - array of fields to be rendered as rectangle
     */
    private void renderMaze(GridPane layout, StackPane[][] fields)
    {
        int col = 0;
        for(int row = 0; row < fields.length; row++)
        {
            for(StackPane field: fields[row]) {
                GridPane.setConstraints(field,col ,row);
                layout.add(field,col,row);
                col += 1;
            }
            col = 0;
        }
    }

    private HBox createScoreBar()
    {
        StackPane score = createScoreBarItem("Time: Cilcked: " + click,Pos.TOP_LEFT);
        score.setOnMouseClicked(mouseEvent ->
        {
            //TODO: set timer here
            click += 1;
            getTextFromPane(score).setText("Time: Clicked: " + click);
        });

        StackPane keyPick = createScoreBarItem("Key: no",Pos.CENTER);
        StackPane hearts = createScoreBarItem("Hearts: HEARTSNUM",Pos.TOP_RIGHT);

        HBox scoreBar = new HBox(score,keyPick,hearts);
        HBox.setHgrow(score, Priority.ALWAYS);
        HBox.setHgrow(hearts, Priority.ALWAYS);
        scoreBar.setId("scoreBar");
        scoreBar.setSpacing(10);
        return scoreBar;
    }

    /**
     * Creates score bar item
     * @param text output text
     * @return score bar item stack pane
     */
    private StackPane createScoreBarItem(String text, Pos alingment)
    {
        StackPane scoreBarOption = new StackPane();
        scoreBarOption.setPrefWidth(100);
        scoreBarOption.setAlignment(alingment);
        Text txt = new Text(text);
        txt.setTextAlignment(TextAlignment.CENTER);
        txt.setFill(Color.rgb(220,220,220));
        scoreBarOption.getChildren().add(txt);
        return scoreBarOption;
    }

    /**
     * Find text in pane children
     * @param pane searched pane
     * @return text object
     */
    private Text getTextFromPane(StackPane pane)
    {
        for(Node child: pane.getChildren())
        {
            if(child instanceof Text) return (Text) child;
        }
        return new Text();
    }

    private Integer[] findNumbers(String str) throws Exception
    {
        Integer[] nums = new Integer[2];
        Pattern findNumsPattern = Pattern.compile("\\d+");

        // TODO: FIX THIS
        if(!str.matches("^[0-9 ]+$") || str.isEmpty())
        {
            throw new Exception("Wrong format of map.");
        }

        Matcher matcher = findNumsPattern.matcher(str);
        int i = 0;
        while (matcher.find()) {
            String number = matcher.group();  // extract the matched number as a string
            try
            {
                nums[i] = Integer.parseInt(number);  // convert the string to an integer
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
                break;
            }
            i += 1;
        }
        return nums;
    }

    /**
     * Sets maze for rendering
     */
    private void setupMaze()
    {
        MazeConfigure mazeConfig = new MazeConfigure();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/maps/" + this.map));

            String line = reader.readLine();
            System.out.println(line);
            Integer[] size;
            try {
                size = findNumbers(line);
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
                return;
            }

            // load maze
            mazeConfig.startReading(size[0],size[1]);
            line = reader.readLine();
            System.out.println(line);
            while (line != null)
            {
                System.out.println(line);
                mazeConfig.processLine(line);
                line = reader.readLine();

            }
            this.maze = mazeConfig.createMaze();
            mazeConfig.stopReading();
            this.fields = new StackPane[this.maze.getRowCount()][this.maze.getColCount()];
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds field as Rectangle to fields array
     */

    //TODO: FIX THIS METHOD
    private void addField(Field field)
    {
        StackPane tile = new StackPane();
        setupField(tile,field.getRow(),field.getCol());
        if(field instanceof WallField) tile.setId(FieldType.WALL);
        else if (field instanceof PathField) {
            if (field instanceof StartField)
            {
                pacRow = field.getRow();
                pacCol = field.getCol();
                tile.getChildren().add(pacmanView);
                tile.setId(FieldType.START);
            }
            else if (field instanceof EndField) tile.setId(FieldType.END);
            else tile.setId(FieldType.PATH);
        }
        this.fields[field.getRow()][field.getCol()] = tile;
    }

    /**
     * Sets basic params and mouse events to field
     * @param tile Field got by maze
     */
    private void setupField(StackPane tile, int row, int col)
    {
        tile.setPrefHeight(sideSize);
        tile.setPrefWidth(sideSize);
        tile.setOnMouseClicked(mouseEvent ->
        {
            System.out.println("x: " + col + "y: "  + row);
            if(tile.getId() != FieldType.WALL && tile.getChildren().isEmpty())
            {
                pacRow = row;
                pacCol = col;
                tile.getChildren().add(pacmanView);
                tile.requestLayout();
            }
        });
    }

    /**
     * adds field to array of field representation
     */
    private void addFieldRepresentation()
    {
        for (int row = 0; row < maze.getColCount(); row++)
        {
            for (int col = 0; col < maze.getRowCount(); col++)
            {
                addField(this.maze.getField(row,col));
            }
        }
    }
}