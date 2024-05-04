package test;

import controllers.Front.Animal.GoogleDriveService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load your JavaFX scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/indexFront.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();



    }

    public static void main(String[] args) {
        launch();
    }
}
