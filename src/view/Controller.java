package view;

import game.Game;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.settings.Settings;

import java.io.File;

public class Controller {
    private File currentLevel;
    private Stage stage;

    private Scene levelMenuScene;

    public Controller(Stage stage, Scene levelMenuScene) {
        this.stage = stage;
        this.levelMenuScene = levelMenuScene;
    }

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
            // TODO make into a pop-up window
            // TODO decide what to do next, return back to levelMenu
        }
    }

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
