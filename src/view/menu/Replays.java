package view.menu;

import changelog.Changelog;
import game.Game;
import game.MazeClass;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.MazePresenterLog;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Adrián Ponechal
 *
 * Creates replays and displays menu of replays
 */
public class Replays extends Menu {
    private Scene prevMenu;
    private ArrayList<Button> replays;
    private Scene scene;
    private MazeClass maze;

    public Replays(int minWidth, int minHeight, Stage window, Scene prevMenu) {
        super(minWidth, minHeight, window);
        this.prevMenu = prevMenu;
        this.replays = new ArrayList<>();
        this.scene = createMenuScene();
    }

    @Override
    public Scene createMenuScene() {
        BorderPane root = new BorderPane();
        NavBar navbar = new NavBar(this.window, prevMenu);
        root.setTop(navbar.getNavbar());

        ScrollPane scrollPane = createReplaysScrollPane();
        root.setCenter(scrollPane);
        root.setId("rootScrollPane");

        Scene levelMenu = new Scene(root, this.minHeight, this.minWidth);
        levelMenu.getStylesheets().add("/styles/replayMenu.css");
        return levelMenu;
    }

    /**
     * Gets scene of replay menu
     *
     * @return scene
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Creates scroll pane for replays
     *
     * @return scrollPane
     */
    private ScrollPane createReplaysScrollPane() {
        VBox layout = new VBox();
        layout.setMaxSize(this.minHeight, this.minWidth);
        layout.setAlignment(Pos.CENTER);
        layout.setId("root");

        if (this.title != null) layout.getChildren().add(title);

        loadReplays();
        addReplays(layout);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setId("scrollRoot");

        layout.setMaxHeight(Double.MAX_VALUE);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return scrollPane;
    }

    /**
     * Loads all replays from folder
     */
    private void loadReplays() {
        File replays = new File("src", "replays");
        String[] replayNames = replays.list();
        if (replayNames == null) {
            return;
        }
        for (String name : replayNames) {
            Button btn = new Button(name);

            btn.setOnMouseClicked(mouseEvent -> {
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        File file = new File("src/replays/" + name);
                        Changelog changelog = new Changelog();
                        try {
                            changelog.parse(file);
                        } catch (Exception e) {
                            System.out.println("Error while parsing changelog:");
                            e.printStackTrace();
                            // TODO alert window
                            return null;
                        }
                        maze = changelog.maze;
                        MazePresenterLog mazePresenterLog = new MazePresenterLog(changelog, window, scene);
                        Game.addObserver(mazePresenterLog, maze);
                        return null;
                    }
                };
                new Thread(task).start();
            });

            btn.setId("replayButton");
            this.replays.add(btn);
        }
    }

    /**
     * Adds replays to layout
     *
     * @param layout space to add replay buttonsn
     */
    private void addReplays(VBox layout) {
        if (replays.isEmpty()) {
            Text nothing = new Text("No replays has been added yet.");
            nothing.setId("anyReplaysInfo");
            nothing.setFill(Color.WHITE);
            layout.getChildren().add(nothing);
            return;
        }
        for (Button replay : replays) layout.getChildren().add(replay);
    }

}
