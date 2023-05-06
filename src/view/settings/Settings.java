package view.settings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.menu.NavBar;

public class Settings {
    private final Scene settingsScene;
    private final Scene mainMenu;
    private final Stage window;
    private final int minWidth;
    private final int minHeight;

    public Settings(int minWidth, int minHeight, Stage stage, Scene mainMenu)
    {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.window = stage;
        this.mainMenu = mainMenu;
        this.settingsScene = createSettingsScene();
    }

    public Scene getScene() { return this.settingsScene; }
    private Scene createSettingsScene()
    {
        VBox root = new VBox();
        root.setPadding(new Insets(0,20,10,20));
        root.setId("root");
        root.getChildren().add(new NavBar(this.window,this.mainMenu).getNavbar());
        root.getChildren().addAll(createOption("Move by mouse"));

        Scene settings = new Scene(root,minWidth,minHeight);
        settings.getStylesheets().add("styles/settings.css");
        return settings;
    }

    private HBox createOption(String labelText)
    {
        HBox opt = new HBox();
        opt.setId("option");

        Label description = new Label(labelText);

        //maybe use something different from checkbox
        CheckBox checkBox = new CheckBox();
        description.setLabelFor(checkBox);
        checkBox.setPrefWidth(25);

        checkBox.setAlignment(Pos.CENTER_RIGHT);

        HBox.setHgrow(description, Priority.ALWAYS);
        HBox.setHgrow(checkBox, Priority.ALWAYS);
        description.setMaxWidth(Double.MAX_VALUE);

        opt.getChildren().addAll(description,checkBox);
        return opt;
    }
}
