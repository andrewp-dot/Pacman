package view.menu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

// NOTE: mozne jedna trieda Menu pre sub menus

/**
 * Creates main menu scene
 */
public class MainMenu  {
    private final static ArrayList<Button> menuOptions = new ArrayList<>();
    private static Text title;

    /**
     * Add options to menu
     * @param options - variadic String parameter
     */
    public static void addOptions(String... options)
    {
        for(String opt: options) {
            Button btn = new Button(opt);
            menuOptions.add(btn);
        }
    }

    public static void setTitle(String newTitle)
    {
        title = new Text(newTitle);
        title.setId("menuTitle");
    }
    public static Button getButtonByTitle(String title) {
        Button button = null;
        for(Button btn: menuOptions)
        {
            if(btn.getText().equals(title))
            {
                button = btn;
                break;
            }
        }
        return button;
    }

    /**
     * Creates main menu scene
     * @return Scene
     */
    public static Scene createMenu(int minHeight,int minWidth){
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setMinSize(minHeight,minWidth);
        // set title
        layout.getChildren().add(title);

        for(Button btn: menuOptions )
        {
            layout.getChildren().add(btn);
        }
        Scene mainMenu = new Scene(layout,minHeight,minWidth);
        mainMenu.getStylesheets().add("styles/mainMenu.css");
        return mainMenu;
    }


}