package project.kamussearchengine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;
import project.kamussearchengine.Controllers.SearchEngineController;

import java.io.IOException;
import java.util.Objects;

public class DictionarySearchEngine extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Views/LandingView.fxml")));

        Scene scene = new Scene(root, 800, 600, true, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Styles/Landing.css")).toExternalForm());

        stage.setTitle("ISI | Maps Interaktif");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}