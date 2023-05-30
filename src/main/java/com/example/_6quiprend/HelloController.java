package com.example._6quiprend;

import com.isep._6quiprend.core.Game;
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

    Game game;

    public void play(ActionEvent actionEvent) {

        playAlone.setVisible(true);
        multiPlay.setVisible(true);
        play.setVisible(false);

    }

    public void switchScene(String fxml) throws IOException{
        FXMLLoader sce = new FXMLLoader(getClass().getResource(fxml));
        sce.setLocation(HelloApplication.class.getResource(fxml));
        root = sce.load();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void playAlone(ActionEvent event) throws IOException {
        /*FXMLLoader sce = new FXMLLoader(getClass().getResource("alone-view.fxml"));
        primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        sce.setLocation(HelloApplication.class.getResource("alone-view.fxml"));
        root=sce.load();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();*/
        primaryStage =(Stage)((Node)event.getSource()).getScene().getWindow();
        switchScene("alone-view.fxml");
    }

    public void multiPlay(ActionEvent event) throws  IOException{
        primaryStage =(Stage)((Node)event.getSource()).getScene().getWindow();
        switchScene("multi-view.fxml");
    }
    public void rules(ActionEvent event) throws  IOException{
        primaryStage =(Stage)((Node)event.getSource()).getScene().getWindow();
        switchScene("rules-view.fxml");
    }

    public void back(ActionEvent event) throws IOException{
        primaryStage =(Stage)((Node)event.getSource()).getScene().getWindow();
        switchScene("hello-view.fxml");
    }

}