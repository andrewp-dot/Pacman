package view.menu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.ArrayList;

public abstract class Menu {
    protected final int minWidth;
    protected final int minHeight;
    protected final ArrayList<Button> menuOptions = new ArrayList<>();
    protected Text title;

    public Menu(int minWidth, int minHeight)
    {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
    }

    public void addOption(String opt)
    {
        Button btn = new Button(opt);
        this.menuOptions.add(btn);
    }

    /**
     * Add options to menu
     * @param options - variadic String parameter
     */
    public void addOptions(String... options)
    {
        for(String opt: options) addOption(opt);
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

    public abstract Scene createMenuScene();
}
