import javafx.application.Application;
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
        Menu mainMenu = basicMenuSetup();
        basicMenuSetup();

        window.setScene(mainMenu.createMenuScene());
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
    private Menu basicMenuSetup()
    {
        Menu mainMenu = new MainMenu(windowWidth,windowHeight,window);
        mainMenu.setTitle("Main Pacmenu");
        mainMenu.addOptions("Play","Settings","Exit");

        // change this to level setups
        LevelMenu levelSetup = new LevelMenu(windowWidth,windowHeight,window);
        mainMenu.getButtonByTitle("Play").setOnMouseClicked(mouseEvent -> window.setScene(levelSetup.getScene()));
        return mainMenu;
    }

    public static void main(String[] args){
        launch(args);
    }
}

