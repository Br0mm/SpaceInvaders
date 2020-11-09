package project;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.IOException;

public class Controller {
    View a;

    @FXML
    TextField nickTF;

    private boolean gameStopped = false;

    public Scene window() throws IOException {
        a = new View();
        Scene window = new Scene(a.createParent());

        window.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case A:
                    if (Model.motherShipDirection == Model.Direction.RIGHT) Model.motherShipDirection = Model.Direction.NOWHERE;
                    else Model.motherShipDirection = Model.Direction.LEFT;
                    break;
                case D:
                    if (Model.motherShipDirection == Model.Direction.LEFT) Model.motherShipDirection = Model.Direction.NOWHERE;
                    else Model.motherShipDirection = Model.Direction.RIGHT;
                    break;
                case SPACE:
                    attack();
                    break;
                case ESCAPE:
                    if (gameStopped && !Model.gameOver) {
                        View.timeline.play();
                        gameStopped = false;
                    }
                    else {
                        View.timeline.stop();
                        gameStopped = true;
                    }
                    break;
                case ENTER:
                    startGame();
                    break;
            }
        });
        window.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.D)
                Model.motherShipDirection = Model.Direction.NOWHERE;
        });

        return window;
    }

    public void startNewGame() throws IOException {
        Model.nickname = nickTF.getText();
        startGame();
    }

    public void startGame() {
        View.timeline.stop();
        Model.gameOver = false;
        if (View.timeline.getKeyFrames().size() != 0) View.timeline.getKeyFrames().remove(0);
        Model.invadersMovesCounter = 0;
        try {
            Main.mainStage.setScene(window());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        View.timeline.play();
        gameStopped = false;
    }

    public void attack() {
        if (Model.defendersAttack.getTranslateY() == -20) {
            Model.defendersAttack.setTranslateX(Model.motherShip.getTranslateX() + 20);
            Model.defendersAttack.setTranslateY(450);
        }
    }

    public static void gameOver() {
        Model.gameOver = true;
        View.timeline.stop();
        Model.motherShip.setVisible(false);
    }
}
