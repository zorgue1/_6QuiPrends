package com.example._6quiprend;

import com.isep._6quiprend.core.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    Stage stage;
    Game game;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("6 Qui Prend !");
        stage.setScene(scene);
        //stage.setFullScreen(true);
        stage.show();
    }





    public static void main(String[] args) {
        launch();
    }
}