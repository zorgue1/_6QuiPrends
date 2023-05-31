package com.example._6quiprend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AloneController {

    private Scene scene;
    private Stage primaryStage;

    private Parent root;

    @FXML Button suivant ;
    @FXML Button suivant2 ;
    @FXML Label texte;
    @FXML TextField nomJoueur;
    private List<String> nomsJoueurs = new ArrayList<>();
    HelloApplication helloApplication;

    public void switchScene(String fxml) throws IOException{
        FXMLLoader sce = new FXMLLoader(getClass().getResource(fxml));
        sce.setLocation(HelloApplication.class.getResource(fxml));
        root = sce.load();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void back(ActionEvent event) throws IOException {
        primaryStage =(Stage)((Node)event.getSource()).getScene().getWindow();
        switchScene("hello-view.fxml");
    }

    public void initialize(){
        texte.setText("Entrer le nom du joueur.");
    }

    public void suivant(ActionEvent event) throws IOException, InterruptedException {
        String nomJoueurActuel = nomJoueur.getText();
        if (!nomJoueurActuel.isEmpty()) {
            nomsJoueurs.add(nomJoueurActuel);
            texte.setText(nomJoueurActuel + " participe ! vas tu réussir à battre notre IA ?");


        }

        suivant.setVisible(false);

    }

    public void suivant2(ActionEvent event) throws  IOException{
        primaryStage =(Stage)((Node)event.getSource()).getScene().getWindow();
        switchScene("plateau-view.fxml");
    }

}
