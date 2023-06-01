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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class MultiController {

    HelloApplication helloApplication;
    private Scene scene;
    private Stage primaryStage;
    private Parent root;
    private int nombreParticipants = 0;
    private int nombreNomsSaisis = 0;
    @FXML private Button suivant;
    @FXML private Button suivant2;
    @FXML private Button ok;
    @FXML private TextField nbJoueur;
    @FXML private TextField nomJoueur;
    @FXML private Label afficheJoueur;
    @FXML private Label texte ;
    private List<String> nomsJoueurs = new ArrayList<>();


    //PLAY()
    Game game =new Game();
    List<Player> players = new ArrayList<>();
    List<Series> seriesListInTable = new ArrayList<>();
    //PLAY()



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
        suivant2.setVisible(false);
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
            suivant.setVisible(false);
            suivant2.setVisible(true);
        }
    }

    public void suivant2(ActionEvent event) throws  IOException{

        primaryStage =(Stage)((Node)event.getSource()).getScene().getWindow();
        switchScene("plateau-view.fxml");
    }


    /*private void addHumanPlayers() {
        for (int i = 1; i <= nomsJoueurs.size(); i++) {
            String name = nomsJoueurs.get(i);
            Deck deck = game.getPlayerDeck();
            List<Card> retrievedCards = new ArrayList<>();
            RetrievedPack pack = new RetrievedPack(retrievedCards);
            players.add(new Player(name, deck, pack));
        }
    }


    private void play() {
//        List<Card> allCard = game.getAllCard();
        players = game.getPlayers();
        seriesListInTable = game.getSeriesListInTable();

        game.shuffleCards();

        int nbOfPlayers = nombreParticipants;
        //boolean playWithAI = getPlayWithAI();

        *//*if (playWithAI) {
            game.addAIPlayer();
            display.printText("You decided to add an AI.");
        }*//*

        addHumanPlayers();

        //display.printText("Here is the list of players: " + players.toString());

        game.initSeriesOnTable();

        while (!game.areAllDecksEmpty(players)) {
            *//*display.printText("Here are the series on the table:");
            display.displayAllSeries(seriesListInTable);*//* // TODO: methode à créé pour afficher les 4 séries

            List<Integer> chosenNumberList = getPlayerCardSelection();
            HashMap<Integer, Player> getPlayerFromChosenCard = game.mapPlayersToChosenCards(chosenNumberList);

            Collections.sort(chosenNumberList);

            for (int number : chosenNumberList) {
                Card playerCard = new Card(number);
                Player currentPlayer = getPlayerFromChosenCard.get(number);

                display.printTextInBlue("It's " + currentPlayer.getName() + "'s turn");

                if (currentPlayer instanceof AI) {
                    AI aiPlayer = (AI) currentPlayer;
                    boolean aiCardTooWeak = false;

                    if (game.getTheSeriesWithSmallestDifference(playerCard) == null) {
                        aiCardTooWeak = true;
                    }

                    if (!aiCardTooWeak) {
                        Series aiSeries = game.getTheSeriesWithSmallestDifference(playerCard);

                        if (aiSeries.getNbOfCard() == 5) {
                            game.processForFullSeries(aiPlayer, aiSeries,playerCard);
                            display.printText("This series is full, so AI has retrieved the series " + aiSeries.getPosition() + ". The AI's card becomes the first card of the series.");
                        } else {
                            game.normalProcess(aiPlayer, aiSeries, playerCard);
                            display.printText("AI chose series " + aiSeries.getPosition() + " for the card " + playerCard.toString());
                        }
                    } else {
                        Series aiSeries = aiPlayer.getSeriesToRetrieve(seriesListInTable);
                        game.processForCardTooWeak(aiSeries.getPosition(), aiPlayer, playerCard);
                        display.printTextInRed("AI's card is too weak. It retrieved series " + aiSeries.getPosition() + ".");
                    }
                } else {
                    boolean isPossible = false;
                    while (!isPossible) {
                        display.printText("Please choose the series you want to deposit " + playerCard.toString() + " in:");
                        display.displayAllSeries(seriesListInTable);
                        int index = scanner.getIntegerInRange(1, seriesListInTable.size());
                        Series chosenSeries = seriesListInTable.get(index - 1);
                        Card lastCardInSeries = chosenSeries.getLastCardOf();

                        if (lastCardInSeries.getNumber() < number) {
                            if (game.getTheSeriesWithSmallestDifference(playerCard).getPosition() == chosenSeries.getPosition()) {
                                if (chosenSeries.getNbOfCard() == 5) {
                                    display.printText("This series is full, so you need to retrieve the cards of this series. Therefore, your card becomes the first card of the series.");
                                    game.processForFullSeries(currentPlayer, chosenSeries, playerCard);
                                    isPossible = true;
                                } else {
                                    game.normalProcess(currentPlayer, chosenSeries, playerCard);
                                    display.printText("You chose series " + chosenSeries.getPosition() + " for the card " + playerCard.toString());
                                    isPossible = true;
                                }
                            } else {
                                display.printTextInRed("You can't choose this series because the difference with the last card is not the smallest.");
                            }
                        } else if (game.isCardTooWeak(playerCard)) {
                            display.printTextInRed("Your card is too weak, please choose the series you want to take.");
                            int i = scanner.getIntegerInRange(1, seriesListInTable.size());
                            game.processForCardTooWeak(i, currentPlayer, playerCard);
                            isPossible = true;
                        } else {
                            display.printTextInRed("You can't choose this series because your card is smaller than the last card of the series.");
                        }
                    }
                }
            }
        }

        List<Player> winners = game.determineWinner();
        display.printImportantInfo("The winner(s) with the lowest points: " + winners.toString());
    }


    private List<Integer> getPlayerCardSelection() {
        List<Integer> chosenNumberList = new ArrayList<>();

        for (Player player : players) {
            display.printTextInBlue("It's " + player.getName() + "'s turn");

            if (player instanceof AI) {
                AI ai = (AI) player;
                Card aiCard = ai.getCard(seriesListInTable);

                if (aiCard != null) {
                    System.out.println("AI chose the card " + aiCard.toString());
                } else {
                    aiCard = ai.getCardTooWeak();
                    System.out.println("AI chose the card too weak " + aiCard.toString());
                }

                int numberOfCard = aiCard.getNumber();
                chosenNumberList.add(numberOfCard);
                display.printText("AI has finished choosing.");
            } else {
                display.printText("Which card do you want to play?");
                display.printText("Your deck: " + player.getDeck().toString());
                int number = scanner.getCardNumberInput(player.getDeck());
                chosenNumberList.add(number);
            }
        }

        return chosenNumberList;
    }*/




}
