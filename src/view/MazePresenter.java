package view;
import game.common.Field;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Custom imports */
import game.MazeConfigure;
import game.common.Maze;
import view.fields.FieldView;

/**
 * TODO:
 * attach Maze presenter to specific maze
 */
public class MazePresenter {

    /**
     *  Default size of field in px
     */
    int sideSize = 25;
    ArrayList<ArrayList<Rectangle>> fields = new ArrayList<>(1);

    //change this values to test
    int maxRows;
    int maxCols;
    int rowNum;
    int columnNum;
    String map;
    // attached maze
    Maze maze;

    //change this to size ( rows:height x cols: width);
    public MazePresenter(String map) {
        this.map = map;
        this.rowNum = 0;
        this.columnNum = 0;
        setupMaze();
    }

    public Scene CreatePacmanScene(){
        //TODO: use 1 root pane and then hbox and grid or whatever
        GridPane layout = new GridPane();
        //TODO: use update
        layout.add(new Text("Score"),  0, 0, GridPane.REMAINING, 1);
        layout.setPadding(new Insets(10,10,10,10));

        //layout.setGridLinesVisible(true);
        addFieldRepresentation();

        for (ArrayList<Rectangle> rectangles : fields)
        {
            for (Rectangle field : rectangles)
            {
                System.out.println(field.getId());
            }
        }

        renderMaze(layout, fields);
        Scene pacman_scene = new Scene(layout);
        pacman_scene.getStylesheets().add("styles/maze.css");
        System.out.println("PacmanWindow created.");
        return pacman_scene;
    }

    public void setMaze(Maze maze)
    {
        this.maze = maze;
    }

    /**
     * Renders pacman maze
     * @param layout - grid pane for pacman maze
     * @param fields - array of fields to be rendered as rectangle
     */
    private void renderMaze(GridPane layout,  ArrayList<ArrayList<Rectangle>> fields)
    {
        int btn_row_num = 0;
        for(int i = 0; i < fields.size(); i++)
        {
            for(Rectangle field: fields.get(i)) {
                GridPane.setConstraints(field,btn_row_num,i);
                btn_row_num += 1;
                layout.add(field,(int)field.getX() ,(int)field.getY() + 4);
            }
            btn_row_num = 0;
        }
    }

    /**
     * Adds field as Rectangle to fields array
     */
    private void addField(Field field)
    {
        if(this.rowNum >= maxRows)
        {
            System.err.println("Max number of rows has been reached.");
            return;
        }

        if(this.fields.size() == 0){
            this.fields.add(rowNum,new ArrayList<>(0));
        }

        else if(this.columnNum >= maxCols )
        {
            this.fields.add(rowNum,new ArrayList<>(0));
            rowNum += 1;
            columnNum = 0;
        }
        Rectangle fieldView = FieldView.getFieldView(field);
        setupField(fieldView);
        this.fields.get(rowNum).add(fieldView);
        columnNum += 1;
    }

    /**
     * Sets basic paramas and mouse events to field
     * @param field - field got by maze
     */
    private void setupField(Rectangle field)
    {
        // setup field
        field.setHeight(sideSize);
        field.setWidth(sideSize);

        field.setOnMouseClicked(mouseEvent ->
                System.out.println("x: "+ field.getX() + "y: " + field.getY() + " id:" + field.getId())
        );
        field.setX(this.columnNum);
        field.setY(this.rowNum);
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
            Integer[] size;
            try
            {
                size = findNumbers(line);
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
                return;
            }

            mazeConfig.startReading(size[0],size[1]);

            while (line != null)
            {
                System.out.println(line);
                line = reader.readLine();
                mazeConfig.processLine(line);
            }
            this.maze = mazeConfig;
            this.maxRows = size[0] + 2;
            this.maxCols = size[1] + 2;
            mazeConfig.stopReading();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addFieldRepresentation()
    {
        System.out.println(maze.getRowCount());
        System.out.println(maze.getColCount());
        for (int col = 0; col < maze.getColCount(); col++)
        {
            for (int row = 0; row < maze.getRowCount(); row++)
            {
                addField(this.maze.getField(col,row));
            }
        }
    }
}