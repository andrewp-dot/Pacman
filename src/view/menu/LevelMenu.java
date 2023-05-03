package view.menu;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* javafx */
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.MazePresenter;
public class LevelMenu extends Menu {
    Scene scene;
    ArrayList<String> maps = new ArrayList<>(0);
    public LevelMenu(int minWidth, int minHeight, Stage stage)
    {
        super(minWidth,minHeight, stage);
        this.loadMaps();
        this.scene = createMenuScene();
    }

    public Scene getScene() { return scene; }

    public List<String> getMaps() { return this.maps; }

    @Override
    public Scene createMenuScene()
    {
        VBox layout = new VBox();
        layout.setMaxSize(this.minHeight,this.minWidth);
        layout.setAlignment(Pos.CENTER);
        layout.setId("vbox");

        // set title
        if(this.title != null) layout.getChildren().add(title);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        layout.setMaxHeight(Double.MAX_VALUE);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        addButtonsToLayout(layout);

        Scene levelMenu = new Scene(scrollPane,this.minHeight,this.minWidth);
        levelMenu.getStylesheets().add("styles/levelMenu.css");
        return levelMenu;
    }

    /**
     * Loads maps into maps array
     */
    private void loadMaps()
    {
        File src = new File("src");
        File mapFolder = new File(src,"maps");
        File[] listOfFiles = mapFolder.listFiles();

        if(listOfFiles == null) return;
        this.parseMaps(listOfFiles);

        // add options
        this.sortMaps();
        this.buttonsSettings();
    }

    /**
     * Filters maps from files in "maps" folder
     * @param listOfFiles - files in "maps" folder
     */
    private void parseMaps(File[] listOfFiles)
    {
        for (File listOfFile : listOfFiles)
        {
            String fileName = listOfFile.getName();
            if (fileName.matches("^map[0-9]*.txt$"))
            {
                maps.add(fileName);
            }
        }
    }

    /**
     * Print options of menu
     */
    private void printOptions()
    {
        for(String map: maps)
        {
            System.out.println(map);
        }
    }

    /**
     * Sorts maps based on map number
     */
    private void sortMaps()
    {
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
     * Gets map file name based on number of level
     * @param lvlNum - number of level
     * @return - name of map to be loaded
     */

    /**
     * Sets buttons events and id's, id is equal to index in maps array
     */
    private void buttonsSettings()
    {
        for(int i = 0; i < maps.size(); i++)
        {
            this.addOption("Level " + (i + 1));
            Button btn =  this.getButtonByTitle("Level " + (i + 1));
            btn.setId(String.valueOf(i));
            btn.setOnMouseClicked(mouseEvent -> this.window.setScene(createLevel(btn)));
        }
    }

    private void addButtonsToLayout(Pane layout)
    {
        for(Button btn: this.menuOptions )
        {
            layout.getChildren().add(btn);
        }
    }

    private Scene createLevel(Button btn)
    {
        MazePresenter maze = new MazePresenter(maps.get(Integer.parseInt(btn.getId())));
        return maze.CreatePacmanScene();
    }

}


