package view.menu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


/**
 * Creates main menu scene
 */
public class MainMenu  extends Menu {
    /*
    private final int minWidth;
    private final int minHeight;
    private final ArrayList<Button> menuOptions = new ArrayList<>();
    private Text title;
    */

    public MainMenu(int minWidth, int minHeight) {
        super(minWidth,minHeight);
    }

    /**
     * Creates main menu scene
     * @return Scene
     */
    @Override
    public Scene createMenuScene()
    {
        VBox layout = new VBox();

        layout.setAlignment(Pos.CENTER);
        layout.setMinSize(this.minHeight,this.minWidth);

        // set title
        if(this.title != null) layout.getChildren().add(title);

        for(Button btn: this.menuOptions )
        {
            layout.getChildren().add(btn);
        }
        Scene mainMenu = new Scene(layout,this.minHeight,this.minWidth);
        mainMenu.getStylesheets().add("styles/mainMenu.css");
        return mainMenu;
    }
}
