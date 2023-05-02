import javafx.application.Application;
import javafx.stage.Stage;

/* Custom imports */
import view.MazePresenter;
import view.menu.Menu;
import view.LevelMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Menu mainMenu = basicMenuSetup();
        basicMenuSetup();

        //window.setScene(mainMenu.createMenuScene());
        System.out.println(primaryStage.getWidth());

        LevelMenu lvl = new LevelMenu(windowWidth,windowHeight);
        window.setScene(lvl.createMenuScene());

        window.show();
    }

    private void close_handler() {
        // add handling for X button -> comunication between scenes / windows
        System.out.println("Bye bye...");
        window.close();
    }

    /**
     * Set ups standard menu
     */
    private Menu basicMenuSetup()
    {
        Menu mainMenu = new Menu(windowWidth,windowHeight);
        mainMenu.setTitle("Main Pacmenu");
        mainMenu.addOptions("Play","Settings","Exit");

        // change this to level setups
        MazePresenter test = new MazePresenter(2,3);
        mainMenu.getButtonByTitle("Play").setOnMouseClicked(mouseEvent -> window.setScene(test.CreatePacmanScene()));
        return mainMenu;
    }

    public static void main(String[] args){
        launch(args);
    }
}

