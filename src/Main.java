import javafx.application.Application;
import javafx.stage.Stage;

/* Custom imports */
import view.MazePresenter;
import view.menu.MainMenu;

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

        basicMenuSetup();
        window.setScene(MainMenu.createMenu(500,500));
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
    private void basicMenuSetup()
    {
        MainMenu.setTitle("Main PacMenu");
        MainMenu.addOptions("Play","Settings","Exit");
        MainMenu.getButtonByTitle("Play").setOnMouseClicked(mouseEvent -> window.setScene(MazePresenter.PacmanGame()));
    }

    public static void main(String[] args){
        launch(args);
    }
}

