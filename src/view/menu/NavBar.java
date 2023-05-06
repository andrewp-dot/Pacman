package view.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class NavBar {
    private Stage window;
    private Scene prevScene;

    private StackPane navbar;
    public NavBar(Stage window,Scene prevScene)
    {
        this.window = window;
        this.prevScene = prevScene;
        this.navbar = createNavBar();
    }

    public StackPane getNavbar() { return this.navbar; }

    /**
     * Creates back button
     * @param displayScene
     * @return
     */
    private Button backButton(Scene displayScene)
    {
        Button goBack = new Button();
        goBack.setAlignment(Pos.TOP_LEFT);
        goBack.setId("backButton");
        goBack.setOnMouseClicked(mouseEvent -> this.window.setScene(displayScene));
        return goBack;
    }

    /**
     * Creates navigation bar
     * @return navbar
     */
    private StackPane createNavBar()
    {
        Button goBackButton = backButton(this.prevScene);
        StackPane navBar = new StackPane(goBackButton);
        navBar.setPadding(new Insets(10,10,10,10));
        navBar.setAlignment(Pos.TOP_LEFT);
        navBar.setId("navBar");
        navBar.getStylesheets().add("styles/navBar.css");
        return navBar;
    }
}
