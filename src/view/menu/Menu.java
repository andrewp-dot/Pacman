package view.menu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

// NOTE: mozne jedna trieda Menu pre sub menus

/**
 * Creates main menu scene
 */
public class Menu  {
    final int minWidth;
    final int minHeight;
    private final ArrayList<Button> menuOptions = new ArrayList<>();
    private Text title;

    public Menu(int minWidth, int minHeight) {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
    }

    /**
     * Add options to menu
     * @param options - variadic String parameter
     */
    public void addOptions(String... options)
    {
        for(String opt: options) {
            addOption(opt);
        }
    }

    public void addOption(String opt)
    {
        Button btn = new Button(opt);
        this.menuOptions.add(btn);
    }

    public void setTitle(String newTitle)
    {
        this.title = new Text(newTitle);
        this.title.setId("menuTitle");
    }
    public Button getButtonByTitle(String title) {
        Button button = null;
        for(Button btn: this.menuOptions)
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
    public Scene createMenuScene(){
        ScrollPane scroll = new ScrollPane();
        VBox layout = new VBox(scroll);

        layout.setAlignment(Pos.CENTER);
        layout.setMinSize(this.minHeight,this.minWidth);
        // set title
        System.out.println("HERE");
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