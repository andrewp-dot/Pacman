package view;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

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

    //change this to size ( rows:height x cols: width);
    public MazePresenter(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        this.rowNum = 0;
        this.columnNum = 0;
    }

    public Scene CreatePacmanScene(){


        GridPane layout = new GridPane();
        layout.add(new Text("Score"), 0, 2);
        layout.setPadding(new Insets(10,10,10,10));
        layout.setGridLinesVisible(true);

        this.addField();
        this.addField();
        this.addField();
        this.addField();

        this.addField();
        this.addField();

        renderMaze(layout, fields);
        Scene pacman_scene = new Scene(layout);
        System.out.println("PacmanWindow created.");
        return pacman_scene;
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
    private void addField()
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
        Rectangle test_field = new Rectangle();
        setupField(test_field);
        this.fields.get(rowNum).add(test_field);
        columnNum += 1;
    }

    /**
     * Sets basic paramas and mouse events to field
     * @param field
     * @type type - type of field to select settings which are typical for the type
     */
    private void setupField(Rectangle field)
    {
        // setup field
        field.setHeight(sideSize);
        field.setWidth(sideSize);

        // mouse event
        field.setOnMouseEntered(mouseEvent ->  field.setFill(Color.rgb(210,210,210)));
        field.setOnMouseExited(mouseEvent -> field.setFill(Color.rgb(150,150,150)));

        field.setOnMouseClicked(mouseEvent ->
                System.out.println("x: "+ field.getX() + "y: " + field.getY())
        );
        field.setX(this.columnNum);
        field.setY(this.rowNum);
    }
}