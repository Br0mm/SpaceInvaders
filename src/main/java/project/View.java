package project;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class View {
    public static Timeline timeline = new Timeline();

    public Parent createParent() throws IOException {
        FXMLLoader motherShipLoader = new FXMLLoader(new File("src/main/resources/MotherShip.fxml").toURI().toURL());
        motherShipLoader.setController(new Controller());

        Group invadersTeamBody = new Group();
        for (int j = 0; j < 3; j++)
            for(int i = 0; i < 10; i++) {
                Group a = createInvadersTeam();
                a.setTranslateX(40 * i);
                a.setTranslateY(40 * j);
                invadersTeamBody.getChildren().add(a);
            }
        Model.invadersTeam = invadersTeamBody.getChildren();

        Group motherShip = motherShipLoader.load();
        motherShip.setTranslateX(230);
        motherShip.setTranslateY(470);
        Model.motherShip = motherShip;

        Group defendersAttack = createAttack();
        defendersAttack.setTranslateX(Model.motherShip.getTranslateX() + 20);
        defendersAttack.setTranslateY(-20);
        Model.defendersAttack = defendersAttack;

        Group invadersAttack = new Group();
        Model.invadersAttack = invadersAttack.getChildren();

        Pane root = new AnchorPane();
        root.setPrefSize(510, 500);

        KeyFrame frame = new KeyFrame(Duration.seconds(0.04), actionEvent -> {
            try {
                Model.moveMotherShip();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(addBackground(), motherShip, invadersTeamBody, defendersAttack, invadersAttack);
        return root;
    }

    private Group createInvadersTeam() throws IOException {
        FXMLLoader invaderLoader = new FXMLLoader(new File("src/main/resources/Invader.fxml").toURI().toURL());
        return invaderLoader.load();
    }

    public static Group createAttack() throws IOException {
        FXMLLoader attackLoader = new FXMLLoader(new File("src/main/resources/Attack.fxml").toURI().toURL());
        return attackLoader.load();
    }

    public ImageView addBackground() throws MalformedURLException {
        return new ImageView(new Image(new File("background/Background.jpg").toURI().toURL().toString(),
                Model.width, Model.height, false, true));
    }
}
