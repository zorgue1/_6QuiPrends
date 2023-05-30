package com.example._6quiprend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML private Button play;
    @FXML private Button playAlone;
    @FXML private Button multiPlay;

    HelloApplication helloApplication;


    public void play(ActionEvent actionEvent) {

        playAlone.setVisible(true);
        multiPlay.setVisible(true);
        play.setVisible(false);

    }



}