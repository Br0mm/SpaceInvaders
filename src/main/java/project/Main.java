package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Space Invaders");
        FXMLLoader loader = new FXMLLoader(new File("src/main/resources/Menu.fxml").toURI().toURL());
        loader.setController(new Controller());
        Scene menu = new Scene(loader.load());
        stage.setScene(menu);
        (mainStage = stage).show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
