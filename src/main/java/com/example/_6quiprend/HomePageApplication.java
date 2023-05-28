package com.example._6quiprend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomePageApplication extends Parent {



    public HomePageApplication(Stage stage) throws IOException {

    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("6 Qui Prend !");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}
