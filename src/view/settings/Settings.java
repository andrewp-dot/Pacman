package view.settings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.menu.NavBar;

public class Settings {
    private final Scene settingsScene;
    private final Scene mainMenu;
    private final Stage window;
    private final int minWidth;
    private final int minHeight;

    private boolean useAutoMove;

    /**
     * Constructor
     * @param minWidth scene width
     * @param minHeight scene height
     * @param stage stage, where it is displayed
     * @param mainMenu menu to go back to
     */
    public Settings(int minWidth, int minHeight, Stage stage, Scene mainMenu)
    {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.window = stage;
        this.mainMenu = mainMenu;
        this.settingsScene = createSettingsScene();
    }

    /**
     * Gets value of mouse move
     * @return true if its enabled
     */
    public boolean isAutoMoveEnabled() { return this.useAutoMove; }

    /**
     * Gets settings scene
     * @return created settings scene
     */
    public Scene getScene() { return this.settingsScene; }

    /**
     * Creates settings scene
     * @return settings scene
     */
    private Scene createSettingsScene()
    {
        VBox root = new VBox();
        root.setPadding(new Insets(0,20,10,20));
        root.setId("root");
        root.getChildren().add(new NavBar(this.window,this.mainMenu).getNavbar());
        root.getChildren().addAll(createOption("Auto move"));

        Scene settings = new Scene(root,minWidth,minHeight);
        settings.getStylesheets().add("styles/settings.css");
        return settings;
    }

    /**
     * Creates option of settings
     * @param labelText description option
     * @return settings option view
     */
    private HBox createOption(String labelText)
    {
        HBox opt = new HBox();
        opt.setId("option");

        Label description = new Label(labelText);

        //maybe use something different from checkbox
        CheckBox checkBox = new CheckBox();
        description.setLabelFor(checkBox);
        checkBox.setPrefWidth(25);
        // initial
        this.useAutoMove = checkBox.isSelected();
        checkBox.setOnMouseClicked( mouseEvent -> this.useAutoMove = checkBox.isSelected());

        checkBox.setAlignment(Pos.CENTER_RIGHT);

        HBox.setHgrow(description, Priority.ALWAYS);
        HBox.setHgrow(checkBox, Priority.ALWAYS);
        description.setMaxWidth(Double.MAX_VALUE);

        opt.getChildren().addAll(description,checkBox);
        return opt;
    }
}
