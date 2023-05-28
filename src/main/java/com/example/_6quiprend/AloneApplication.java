package com.example._6quiprend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AloneApplication extends Parent {

    public AloneApplication(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("alone-view.fxml"));
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
