package com.example._6quiprend;

import com.isep._6quiprend.core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    private Game game = new Game();
    private List<Player> players;
    private List<Series> seriesListInTable;
    private String playerName;
    AnchorPane mainAnchorPane;

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

            playerName = nomJoueurActuel;
            nomsJoueurs.add(nomJoueurActuel);
            texte.setText(nomJoueurActuel + " participe ! vas tu réussir à battre notre IA ?");


        }

        suivant.setVisible(false);

    }

    public void suivant2(ActionEvent event) throws  IOException{

        primaryStage =(Stage)((Node)event.getSource()).getScene().getWindow();
//        switchScene("plateau-view.fxml");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("plateau-view.fxml"));
        mainAnchorPane = (AnchorPane) loader.load();

        players = game.getPlayers();
        seriesListInTable = game.getSeriesListInTable();
        game.shuffleCards();

        Deck deck = game.getPlayerDeck();
        List<Card> retrievedCards = new ArrayList<>();
        RetrievedPack pack = new RetrievedPack(retrievedCards);
        Player player = new Player(playerName, deck, pack);
        PlayerView playerView = new PlayerView(player, true, 5, 3);
        players.add(player);

        game.addAIPlayer();
        Player ai = players.get(1);
        PlayerView aiView = new PlayerView(ai, false, 0, 3);



        game.initSeriesOnTable();

        while(!game.areAllDecksEmpty(players))
        {
            displayAllSeries();


        }


        AnchorPane.setTopAnchor(playerView.getComponent(), 0.0);
        AnchorPane.setBottomAnchor(playerView.getComponent(), 0.0);
        AnchorPane.setLeftAnchor(playerView.getComponent(), 0.0);
        AnchorPane.setRightAnchor(playerView.getComponent(), 0.0);
        mainAnchorPane.getChildren().add(playerView.getComponent());

        AnchorPane.setTopAnchor(aiView.getComponent(), 0.0);
        AnchorPane.setBottomAnchor(aiView.getComponent(), 0.0);
        AnchorPane.setLeftAnchor(aiView.getComponent(), 0.0);
        AnchorPane.setRightAnchor(aiView.getComponent(), 0.0);
        mainAnchorPane.getChildren().add(aiView.getComponent());

        Scene scene = new Scene(mainAnchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public void displayAllSeries(){
        for (Series series : seriesListInTable)
        {
            List<Card> cardsOfSeries = series.getCardsInTable();
            SeriesView seriesView = new SeriesView(cardsOfSeries, series.getPosition());
            AnchorPane.setTopAnchor(seriesView.getComponent(), 0.0);
            AnchorPane.setBottomAnchor(seriesView.getComponent(), 0.0);
            AnchorPane.setLeftAnchor(seriesView.getComponent(), 0.0);
            AnchorPane.setRightAnchor(seriesView.getComponent(), 0.0);
            mainAnchorPane.getChildren().add(seriesView.getComponent());
        }
    }
}
