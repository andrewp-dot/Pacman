package view.menu;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* javafx */
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import view.Controller;
import view.MazePresenter;

/**
 * Creates and displays level menu
 *
 * @author Adrián Ponechal
 */
public class LevelMenu extends Menu {
    private final Scene scene;
    private final Scene mainMenu;
    private final ArrayList<String> maps = new ArrayList<>(0);

    public LevelMenu(int minWidth, int minHeight, Stage stage, Scene mainMenu) {
        super(minWidth, minHeight, stage);
        this.loadMaps();
        this.mainMenu = mainMenu;
        this.scene = createMenuScene();
    }

    /**
     * Gets level memnu scene
     * @return scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Gets map filenames
     * @return list of filenames
     */
    public List<String> getMaps() {
        return this.maps;
    }

    @Override
    public Scene createMenuScene() {
        BorderPane root = new BorderPane();
        NavBar navbar = new NavBar(this.window,mainMenu);
        root.setTop(navbar.getNavbar());

        ScrollPane scrollPane = createLevelScrollPane();
        root.setCenter(scrollPane);
        root.setId("root");

        Scene levelMenu = new Scene(root, this.minHeight, this.minWidth);
        levelMenu.getStylesheets().add("styles/levelMenu.css");
        return levelMenu;
    }

    /**
     * Creates scrollable menu for level choose
     * @return scroll pane
     */
    private ScrollPane createLevelScrollPane() {
        VBox layout = new VBox();
        layout.setMaxSize(this.minHeight, this.minWidth);
        layout.setAlignment(Pos.CENTER);
        layout.setId("vboxLevelMenu");

        if (this.title != null) layout.getChildren().add(title);

        addButtonsToLayout(layout);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setId("root");

        layout.setMaxHeight(Double.MAX_VALUE);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return scrollPane;
    }

    /**
     * Loads maps into maps array
     */
    private void loadMaps() {
        File mapFolder = new File("data");
        File[] listOfFiles = mapFolder.listFiles();

        if (listOfFiles == null) return;
        this.parseMaps(listOfFiles);

        // add options
        this.sortMaps();
        this.buttonsSettings();
    }

    /**
     * Filters maps from files in "maps" folder
     *
     * @param listOfFiles - files in "maps" folder
     */
    private void parseMaps(File[] listOfFiles) {
        for (File listOfFile : listOfFiles) {
            String fileName = listOfFile.getName();
            if (fileName.matches("^map[0-9]*.txt$")) {
                maps.add(fileName);
            }
        }
    }

    /**
     * Print options of menu - debugging function
     */
    private void printOptions() {
        for (String map : maps) {
            System.out.println(map);
        }
    }

    /**
     * Sorts maps based on map number
     */
    private void sortMaps() {
        this.maps.sort((s1, s2) -> {
            final Pattern lvlNums = Pattern.compile("\\d+");
            Matcher n1 = lvlNums.matcher(s1);
            Matcher n2 = lvlNums.matcher(s2);

            // exception here and exit
            if (n1.find() && n2.find()) {
                return Integer.parseInt(n1.group()) - Integer.parseInt(n2.group());
            } else return 0;

        });
    }

    /**
     * Sets buttons events and id's, id is equal to index in maps array
     */
    private void buttonsSettings() {
        for (int i = 0; i < maps.size(); i++) {
            this.addOption("Level " + (i + 1));
            Button btn = this.getButtonByTitle("Level " + (i + 1));
            btn.setId(String.valueOf(i));
            btn.setOnMouseClicked(mouseEvent -> createLevel(btn));
        }
    }

    /**
     * Adds button views to layout
     *
     * @param layout - pane, where buttons are added
     */
    private void addButtonsToLayout(Pane layout) {
        for (Button btn : this.menuOptions) {
            layout.getChildren().add(btn);
        }
    }

    /**
     * Creates maze of chosen level from menu
     *
     * @param btn - button used for level number by its ID
     */
    private void createLevel(Button btn) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String map = maps.get(Integer.parseInt(btn.getId()));
                File file = new File("data/" + map);
                Controller controller = new Controller(window, scene);
                controller.loadAndPlay(file);

                return null;
            }
        };
        new Thread(task).start();
    }

}


