package view;

import game.Game;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.settings.Settings;

import java.io.File;

/**
 * Controls actions before and after game
 *
 * @Ondřej Vrána
 */
public class Controller {
    private File currentLevel;
    private Stage stage;

    private Scene levelMenuScene;

    public Controller(Stage stage, Scene levelMenuScene) {
        this.stage = stage;
        this.levelMenuScene = levelMenuScene;
    }

    /**
     * Loads level and starts game
     * @param file
     */
    public void loadAndPlay(File file) {
        this.currentLevel = file;
        Game game = new Game(this, Settings.getContinuesMovement());
        try {
            game.loadMaze(file);

            MazePresenter mazePresenter = new MazePresenter(game, stage);
            game.addObserver(mazePresenter);

            game.play();
        } catch (Exception e) {
            System.out.println("Error occurred while loading level:");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays show result dialog based on result of the game
     * @param wasWon - result of the game
     */
    public void gameEnded(boolean wasWon) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ShowResult showResult = new ShowResult(wasWon);
                ShowResult.Decision decision = showResult.showAndWait();
                if (decision == ShowResult.Decision.Restart) {
                    Task<Void> task = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            loadAndPlay(currentLevel);
                            return null;
                        }
                    };
                    new Thread(task).start();
                } else // next
                {
                    stage.setScene(levelMenuScene);
                }
                System.out.println(decision.name());
            }
        });
    }

}
