import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/* Custom imports */
import view.menu.MainMenu;
import view.menu.Menu;
import view.menu.LevelMenu;

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
        window.close();
    }

    /**
     * Set ups standard menu
     */
    private Scene basicMenuSetup()
    {
        Menu mainMenu = new MainMenu(windowWidth,windowHeight,window);
        mainMenu.setTitle("Main Pacmenu");
        mainMenu.addOptions("Play","Settings","Exit");

        // change this to level setups
        Scene mainMenuScene = mainMenu.createMenuScene();
        LevelMenu levelSetup = new LevelMenu(windowWidth,windowHeight,window,mainMenuScene);
        mainMenu.getButtonByTitle("Play").setOnMouseClicked(mouseEvent -> window.setScene(levelSetup.getScene()));
        return mainMenuScene;
    }

    public static void main(String[] args){
        launch(args);
    }
}

