package project.kamussearchengine.Controllers;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class LandingController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private StackPane stackPane;

    @FXML
    private Button startButton;

    @FXML
    private void loadMain(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/kamussearchengine/Views/SearchEngineView.fxml")));
            Scene mainPageScene = new Scene(root);

            Stage stage = (Stage) rootPane.getScene().getWindow();
            double prevWidth = stage.getWidth();
            double prevHeight = stage.getHeight();

            stage.setScene(mainPageScene);
            stage.setWidth(prevWidth);
            stage.setHeight(prevHeight);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

}