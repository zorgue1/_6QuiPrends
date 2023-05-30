package com.example._6quiprend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;


public class MultiController {

    HelloApplication helloApplication;
    @FXML private Button suivant;
    @FXML private Button ok;
    @FXML private TextField nbJoueur;
    @FXML private Label afficheJoueur;

    public void nbjoueur(ActionEvent event) {
        int n = Integer.parseInt(nbJoueur.getText());
        if (n >= 2 && n <= 10) {
            afficheJoueur.setText("Vous serez " + n + " joueur(s) à participer au 6 qui prend !");
            ok.setVisible(false);
            suivant.setDisable(false);
        } else {
            afficheJoueur.setText("ERREUR : Entrez un nombre de joueur entre 2 et 10.");
        }
    }







}
