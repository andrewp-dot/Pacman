import game.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/* Custom imports */
import view.MazePresenterLog;
import view.menu.MainMenu;
import view.menu.Menu;
import view.menu.LevelMenu;
import view.menu.Replays;
import view.settings.Settings;

public class Main extends Application{
    // size of window in pixels
    private final int windowWidth = 500;
    private final int windowHeight = 500;
    Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception {
        // set stage to global window
        window = primaryStage;
        window.setResizable(false);

        // Set title and on close event
        window.setTitle("Maze");
        window.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            close_handler();
        });
        Scene mainMenu = basicMenuSetup();
        window.setScene(mainMenu);
        window.show();
    }

    private void close_handler()
    {
        // add handling for X button -> comunication between scenes / windows
        System.out.println("Bye bye...");
        Game.terminate = true;
        MazePresenterLog.terminate = true;
        window.close();
    }

    /**
     * Set ups standard menu
     */
    private Scene basicMenuSetup()
    {
        Menu mainMenu = new MainMenu(windowWidth,windowHeight,window);
        mainMenu.setTitle("Main Pacmenu");
        mainMenu.addOptions("Play","Settings","Replays","Exit");

        // change this to level setups
        Scene mainMenuScene = mainMenu.createMenuScene();
        LevelMenu levelSetup = new LevelMenu(windowWidth,windowHeight,window,mainMenuScene);
        mainMenu.getButtonByTitle("Play").setOnMouseClicked(mouseEvent -> window.setScene(levelSetup.getScene()));

        Settings settings = new Settings(windowWidth,windowHeight,window,mainMenuScene);
        mainMenu.getButtonByTitle("Settings").setOnMouseClicked(mouseEvent -> window.setScene(settings.getScene()));

        Replays replays = new Replays(windowWidth,windowHeight,window,mainMenuScene);
        mainMenu.getButtonByTitle("Replays").setOnMouseClicked(mouseEvent -> window.setScene(replays.getScene()));
        mainMenu.getButtonByTitle("Exit").setOnMouseClicked(mouseEvent -> close_handler());
        return mainMenuScene;
    }

    public static void main(String[] args){
        launch(args);
    }
}

