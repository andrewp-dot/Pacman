import javafx.application.Application;
import javafx.stage.Stage;

/* Custom imports */
import view.MazePresenter;
import view.menu.Menu;

public class Main extends Application{

    Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception {
        // set stage to global window
        window = primaryStage;

        // Set title and on close event
        window.setTitle("Maze");
        window.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            close_handler();
        });
        Menu mainMenu = basicMenuSetup();
        basicMenuSetup();

        window.setScene(mainMenu.createMenuScene());
        System.out.println(primaryStage.getWidth());

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
        Menu mainMenu = new Menu(500,500);
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

