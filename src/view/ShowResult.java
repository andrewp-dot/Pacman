package view;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class ShowResult {
    Dialog<Decision> dialog;

    enum Decision{
        Restart,
        Next,
    }

    public ShowResult(boolean hasWon){
        dialog = new Dialog<>();
        if (hasWon){
            dialog.setTitle("You have won!!!");
        }
        else{
            dialog.setTitle("You have lost!!!");
        }
        GridPane options = new GridPane();
        options.add(restartButton(), 0, 0);
        options.add(nextButton(), 0, 1);
        dialog.getDialogPane().setContent(options);
    }

    private Button restartButton(){
        Button btn = new Button("Restart");
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dialog.setResult(Decision.Restart);
            }
        });
        return btn;
    }

    private Button nextButton(){
        Button btn = new Button("Next");
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dialog.setResult(Decision.Next);
            }
        });
        return btn;
    }

    public Decision showAndWait(){
        Optional<Decision> result = dialog.showAndWait();
        return result.orElse(Decision.Next);
    }

}
