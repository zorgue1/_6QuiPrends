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


public class MultiController {

    HelloApplication helloApplication;
    private Scene scene;
    private Stage primaryStage;
    private Parent root;
    private int nombreParticipants = 0;
    private int nombreNomsSaisis = 0;
    @FXML private Button suivant;
    @FXML private Button ok;
    @FXML private TextField nbJoueur;
    @FXML private TextField nomJoueur;
    @FXML private Label afficheJoueur;
    @FXML private Label texte ;
    private List<String> nomsJoueurs = new ArrayList<>();


    public void switchScene(String fxml) throws IOException{
        FXMLLoader sce = new FXMLLoader(getClass().getResource(fxml));
        sce.setLocation(HelloApplication.class.getResource(fxml));
        root = sce.load();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void back(ActionEvent event) throws IOException{
        primaryStage =(Stage)((Node)event.getSource()).getScene().getWindow();
        switchScene("hello-view.fxml");
    }

    public void initialize() {
        texte.setText("Combien de joueurs ? (2 à 10)");
    }
    public void nbjoueur(ActionEvent event) {
        int n = Integer.parseInt(nbJoueur.getText());
        if (n >= 2 && n <= 10) {
            afficheJoueur.setText("Vous serez " + n + " joueur(s) à participer au 6 qui prend !");
            ok.setVisible(false);
            nbJoueur.setVisible(false);
            suivant.setDisable(false);
        } else {
            afficheJoueur.setText("ERREUR : Entrez un nombre de joueur entre 2 et 10.");
        }
        nombreParticipants = n ;
    }

    public void suivant(ActionEvent event) throws IOException {
        texte.setText("Entrer le nom des joueurs.");
        nomJoueur.setEditable(true);
        String nomJoueurActuel = nomJoueur.getText();
        if (!nomJoueurActuel.isEmpty()) {
            nomsJoueurs.add(nomJoueurActuel);
            nombreNomsSaisis++;
            afficheJoueur.setText(nomJoueurActuel + " participe ! Entrez le prochain nom !");
        }
        nomJoueur.clear();

        if (nombreNomsSaisis == nombreParticipants) {
            afficheJoueur.setText(nomJoueurActuel + " participe ! ");
            texte.setText("Vous êtes prêt à jouer !");
        }
    }
}
