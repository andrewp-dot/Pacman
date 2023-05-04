package view;
import game.common.Field;
import game.fields.EndField;
import game.fields.PathField;
import game.fields.StartField;
import game.fields.WallField;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Custom imports */
import game.MazeConfigure;
import game.common.Maze;
import utils.Observer;

/**
 * TODO:
 * Fix rendering with maze objects
 * attach Maze presenter to specific maze
 */
public class MazePresenter implements Observer {

    private final int sideSize = 25;
    StackPane[][] fields = null;
    String map;
    // attached maze
    Maze maze;

    @Override
    public void update(Object obj) {

    }

    public static final class FieldType {
        public static final String WALL = "wallField";
        public static final String PATH = "pathField";
        public static final String START = "startField";
        public static final String END = "endField";
    }

    //change this to size ( rows:height x cols: width);
    public MazePresenter(String map) {
        this.map = map;
        setupMaze();
    }

    public Scene CreatePacmanScene(){

        //TODO: use 1 root pane and then hbox and grid or whatever
        GridPane layout = new GridPane();

        //TODO: use update
        //layout.add(new Text("Score"),  0, 0, GridPane.REMAINING, 1);
        layout.setPadding(new Insets(10,10,10,10));

        //layout.setGridLinesVisible(true);
        addFieldRepresentation();

        renderMaze(layout, fields);

        for (int row = 0; row < maze.getColCount(); row++)
        {
            for (int col = 0; col < maze.getRowCount(); col++)
            {
                Character ch = 'W';
                if(fields[row][col].getId() == FieldType.PATH) ch = '.';
                else if(fields[row][col].getId() == FieldType.START) ch = 'S';
                else if(fields[row][col].getId() == FieldType.END) ch = 'E';
                System.out.print(ch);
            }
            System.out.println();
        }

        Scene pacman_scene = new Scene(layout);
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
            if (field instanceof StartField) tile.setId(FieldType.START);
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
                System.out.println("x: " + col + "y: "  + row)
        );
    }
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