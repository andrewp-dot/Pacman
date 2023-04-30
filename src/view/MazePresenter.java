package view;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

/**
 * Creates graphical maze
 */
public class MazePresenter {
    public static Scene PacmanGame(){
        ArrayList<ArrayList<Rectangle>> fields = new ArrayList<>(2);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setGridLinesVisible(true);

        // test field to arr
        Rectangle test_field = new Rectangle();
        test_field.setHeight(50);
        test_field.setWidth(50);
        test_field.setFill(Color.rgb(200,100,200));
        test_field.setOnMouseClicked(mouseEvent ->  test_field.setFill(Color.rgb(100,200,100)));

        int btn_row_num = 0;
        for(int i = 0; i < fields.size(); i++)
        {
            for(Rectangle field: fields.get(i)) {
                GridPane.setConstraints(field,btn_row_num,i);
                btn_row_num += 1;
                layout.getChildren().add(field);
            }
            btn_row_num = 0;
        }

        layout.getChildren().add(test_field);

        Scene pacman_scene = new Scene(layout,300,300);
        System.out.println("PacmanWindow created.");
        return pacman_scene;
    }
}