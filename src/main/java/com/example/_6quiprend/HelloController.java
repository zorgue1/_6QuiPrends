package com.example._6quiprend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;
    private Scene scene;
    private Stage primaryStage;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML private Button play;
    @FXML private Button playAlone;
    @FXML private Button multiPlay;
    @FXML private Button rules;
    private Parent root;

    HelloApplication helloApplication;


    public void play(ActionEvent actionEvent) {

        playAlone.setVisible(true);
        multiPlay.setVisible(true);
        play.setVisible(false);

    }

    public void playAlone(ActionEvent event) throws IOException {
        FXMLLoader sce = new FXMLLoader(getClass().getResource("alone-view.fxml"));
        primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        sce.setLocation(HelloApplication.class.getResource("alone-view.fxml"));
        root=sce.load();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void rules(ActionEvent event) throws  IOException{

    }


}