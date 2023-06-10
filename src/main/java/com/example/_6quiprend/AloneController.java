package com.example._6quiprend;

import com.isep._6quiprend.core.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

//import static com.example._6quiprend.PlayerView.selectedCard;

public class AloneController {

    private Scene scene;
    private Stage primaryStage;

    private Parent root;

    @FXML
    Button suivant;
    @FXML
    Button suivant2;
    @FXML
    Label texte;
    @FXML
    TextField nomJoueur;

    @FXML
    Button choixCarte;
    @FXML
    Button choixSerie;
    @FXML
    Button rules;
    private List<String> nomsJoueurs = new ArrayList<>();
    HelloApplication helloApplication;
    private Game game = new Game();
    private List<Player> players;
    private List<Series> seriesListInTable;
    private String playerName;
    private int seriesNb;
    private int cardNb;
    @FXML
    AnchorPane mainAnchorPane;
    @FXML
    BorderPane mainBorderPane;

    @FXML
    private ImageView imageView;


    @FXML
    Button back;

    private PlayerView playerView;
    private PlayerView aiView;

    private Player player;
    private Player ai;

    public void switchScene(String fxml) throws IOException {
        FXMLLoader sce = new FXMLLoader(getClass().getResource(fxml));
        sce.setLocation(HelloApplication.class.getResource(fxml));
        root = sce.load();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void back(ActionEvent event) throws IOException {
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        switchScene("hello-view.fxml");
    }

    /*public void initialize(){
        texte.setText("Entrer le nom du joueur.");
    }*/

    public void suivant(ActionEvent event) throws IOException, InterruptedException {
        String nomJoueurActuel = nomJoueur.getText();
        if (!nomJoueurActuel.isEmpty()) {

            playerName = nomJoueurActuel;
            nomsJoueurs.add(nomJoueurActuel);
            texte.setText(" Hello " + nomJoueurActuel + ", you will begin the game 6 qui prend ! ");
            texte.setLayoutX(260);
            texte.setLayoutY(100);
            texte.setStyle("-fx-font-size: 35px;");
            texte.setTextFill(Color.WHITE);


        }

        suivant.setVisible(false);

    }

    public void suivant2(ActionEvent event) throws IOException {

        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        switchScene("plateau-view.fxml");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("plateau-view.fxml"));
        mainAnchorPane = (AnchorPane) loader.load();




        Scene scene = new Scene(mainAnchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();

        players = game.getPlayers();
        seriesListInTable = game.getSeriesListInTable();
        game.shuffleCards();

        Deck deck = game.getPlayerDeck();
        List<Card> retrievedCards = new ArrayList<>();
        RetrievedPack pack = new RetrievedPack(retrievedCards);
        player = new Player(playerName, deck, pack);
        players.add(player);

        playerView = new PlayerView(player, true, 6,3);
        game.addAIPlayer();
        ai = players.get(1);
        aiView = new PlayerView(ai, false, 0, 3);

        game.initSeriesOnTable();

        while(!game.areAllDecksEmpty(players)) {

            mainAnchorPane.getChildren().clear();

            setupUI();
            displayAllSeries();
            displayPlayerView(ai, false, 0,3);
            displayPlayerView(player, true, 5, 3);

            choixCarte(event);

            List<Integer> chosenNumberList = getPlayerCardSelection();
            HashMap<Integer, Player> getPlayerFromChosenCard = game.mapPlayersToChosenCards(chosenNumberList);

            Collections.sort(chosenNumberList);

            for (int number : chosenNumberList) {
                Card playerCard = new Card(number);
                Player currentPlayer = getPlayerFromChosenCard.get(number);

                showInfoPopup("Information", "It's " + currentPlayer.getName() + " turn");
                if (currentPlayer instanceof AI) {
                    AI aiPlayer = (AI) currentPlayer;
                    boolean aiCardTooWeak = false;

                    if (game.getTheSeriesWithSmallestDifference(playerCard) == null) {
                        aiCardTooWeak = true;
                    }

                    if (!aiCardTooWeak) {
                        Series aiSeries = game.getTheSeriesWithSmallestDifference(playerCard);

                        if (aiSeries.getNbOfCard() == 5) {
                            game.processForFullSeries(aiPlayer, aiSeries, playerCard);
//                            showPopup("You can't choose this series because the difference with the last card is not the smallest.");
                            showInfoPopup("AI","This series is full, so AI has retrieved the series " + aiSeries.getPosition() + ". The AI's card becomes the first card of the series.");
                        } else {
                            game.normalProcess(aiPlayer, aiSeries, playerCard);
                            showInfoPopup("AI", "AI chose series " + aiSeries.getPosition() + " for the card " + playerCard.toString());
                        }
                    } else {
                        Series aiSeries = aiPlayer.getSeriesToRetrieve(seriesListInTable);
                        game.processForCardTooWeak(aiSeries.getPosition(), aiPlayer, playerCard);
//                        showPopup("You can't choose this series because the difference with the last card is not the smallest.");
                        showInfoPopup("AI", "AI's card is too weak. It retrieved series " + aiSeries.getPosition() + ".");
                    }
                    mainAnchorPane.getChildren().clear();
                    setupUI();
                    displayAllSeries();
                    displayPlayerView(ai, false, 0,3);
                    displayPlayerView(player, true, 5, 3);
                } else {
                    boolean isPossible = false;
                    while (!isPossible) {
                        choixSerie(event);
                        int index = seriesNb;
                        Series chosenSeries = seriesListInTable.get(index - 1);
                        Card lastCardInSeries = chosenSeries.getLastCardOf();

                        if (lastCardInSeries.getNumber() < number) {
                            if (game.getTheSeriesWithSmallestDifference(playerCard).getPosition() == chosenSeries.getPosition()) {
                                if (chosenSeries.getNbOfCard() == 5) {
                                    showErrorPopup("This series is full, so you need to retrieve the cards of this series. Therefore, your card becomes the first card of the series.");
//                                    System.out.println("This series is full, so you need to retrieve the cards of this series. Therefore, your card becomes the first card of the series.");
                                    game.processForFullSeries(currentPlayer, chosenSeries, playerCard);
                                    isPossible = true;
                                } else {
                                    game.normalProcess(currentPlayer, chosenSeries, playerCard);
                                    showInfoPopup(currentPlayer.getName(),"You chose series " + chosenSeries.getPosition() + " for the card " + playerCard.toString());
                                    isPossible = true;
                                }
                            } else {
//                                showPopup("You can't choose this series because the difference with the last card is not the smallest.");
                                showErrorPopup("You can't choose this series because the difference with the last card is not the smallest.");
                            }
                        } else if (game.isCardTooWeak(playerCard)) {
//                            showPopup("You can't choose this series because the difference with the last card is not the smallest.");
                            showErrorPopup("Your card is too weak, please choose the series you want to take.");
                            choixSerie(event);
                            int i = seriesNb;
                            game.processForCardTooWeak(i, currentPlayer, playerCard);
                            System.out.println("series " + seriesListInTable.get(0).getCardsInTable());
                            System.out.println("series " + seriesListInTable.get(1).getCardsInTable());
                            System.out.println("series " + seriesListInTable.get(2).getCardsInTable());
                            System.out.println("series " + seriesListInTable.get(3).getCardsInTable());
                            mainAnchorPane.getChildren().clear();
                            setupUI();
                            displayAllSeries();
                            displayPlayerView(ai, false, 0,3);
                            displayPlayerView(player, true, 5, 3);
                            isPossible = true;
                        } else {
                            showErrorPopup("You can't choose this series because the difference with the last card is not the smallest.");
//                            System.out.println("You can't choose this series because your card is smaller than the last card of the series.");
                        }

                        mainAnchorPane.getChildren().clear();
                        setupUI();
                        displayAllSeries();
                        displayPlayerView(ai, false, 0,3);
                        displayPlayerView(player, true, 5, 3);
                    }
                }
            }
        }


        List<Player> winners = game.determineWinner();
        showInfoPopup("Winner", "The winner(s) with the lowest points: " + winners.toString());
        //TODO: page fin du jeu

    }

    
    private void showInfoPopup(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
    private void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
    private void setupUI() {

        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("/com/example/_6quiprend/image/tapis.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );
        mainAnchorPane.setBackground(new Background(backgroundImage));

        BorderPane mainBorderPane = new BorderPane();
        mainBorderPane.setLayoutX(228);
        mainBorderPane.setLayoutY(445);
        mainBorderPane.setPrefHeight(200);
        mainBorderPane.setPrefWidth(200);

        Button choixCarte = new Button("Card Choice");
        choixCarte.setOnAction(this::choixCarte);
        BorderPane.setAlignment(choixCarte, Pos.CENTER_LEFT);
        choixCarte.setLayoutX(73);
        choixCarte.setLayoutY(622);
        choixCarte.setStyle("-fx-background-color: white;");
        choixCarte.setTextFill(Color.valueOf("#542183"));

        Button choixSerie = new Button("Series Choice");
        choixSerie.setOnAction(this::choixSerie);
        BorderPane.setAlignment(choixSerie, Pos.CENTER_RIGHT);
        choixSerie.setLayoutX(69);
        choixSerie.setLayoutY(661);
        choixSerie.setStyle("-fx-background-color: white;");
        choixSerie.setTextFill(Color.valueOf("#542183"));

        Button back = new Button("RETURN HOME");
        back.setLayoutX(68);
        back.setLayoutY(14);
        back.setOnAction(event -> {
            try {
                back(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        back.setStyle("-fx-background-color: white;");
        back.setTextFill(Color.valueOf("#542183"));

        Button rules = new Button("RULES");
        rules.setLayoutX(91);
        rules.setLayoutY(57);
        rules.setOnAction(event -> {
            try {
                rules(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        rules.setStyle("-fx-background-color: white;");
        rules.setTextFill(Color.valueOf("#542183"));

        Label indexLabel1 = new Label("1");
        indexLabel1.setLayoutX(280);
        indexLabel1.setLayoutY(150);
        indexLabel1.setStyle("-fx-font-size: 35px;");
        indexLabel1.setTextFill(Color.WHITE);

        Label indexLabel2 = new Label("2");
        indexLabel2.setLayoutX(280);
        indexLabel2.setLayoutY(270);
        indexLabel2.setStyle("-fx-font-size: 35px;");
        indexLabel2.setTextFill(Color.WHITE);

        Label indexLabel3 = new Label("3");
        indexLabel3.setLayoutX(280);
        indexLabel3.setLayoutY(390);
        indexLabel3.setStyle("-fx-font-size: 35px;");
        indexLabel3.setTextFill(Color.WHITE);

        Label indexLabel4 = new Label("4");
        indexLabel4.setLayoutX(280);
        indexLabel4.setLayoutY(510);
        indexLabel4.setStyle("-fx-font-size: 35px;");
        indexLabel4.setTextFill(Color.WHITE);

        Label score = new Label(player.getName() +"'s score : " + player.getPack().getTotalBeefHead());
        score.setLayoutX(980);
        score.setLayoutY(630);
        score.setStyle("-fx-font-size: 20px;");
        score.setTextFill(Color.WHITE);

        Label aiScore = new Label("AI's score : " + ai.getPack().getTotalBeefHead());
        aiScore.setLayoutX(980);
        aiScore.setLayoutY(57);
        aiScore.setStyle("-fx-font-size: 20px;");
        aiScore.setTextFill(Color.WHITE);

        mainAnchorPane.getChildren().addAll(mainBorderPane, back, rules, choixCarte, choixSerie, indexLabel1, indexLabel2, indexLabel3, indexLabel4, aiScore, score);
    }

    private List<Integer> getPlayerCardSelection() {
        List<Integer> chosenNumberList = new ArrayList<>();

        for (Player player : players) {
            if (player instanceof AI) {
                AI ai = (AI) player;
                Card aiCard = ai.getCard(seriesListInTable);

                if (aiCard != null) {
                    showInfoPopup("AI","AI chose the card " + aiCard.toString());
                } else {
                    aiCard = ai.getCardTooWeak();
                    showInfoPopup("AI","AI chose the card too weak " + aiCard.toString());
                }

                int numberOfCard = aiCard.getNumber();
                chosenNumberList.add(numberOfCard);
            } else {
                chosenNumberList.add(cardNb);
            }
        }

        return chosenNumberList;
    }
    public void displayPlayerView(Player player, boolean visibility, int row, int col){
        playerView = new PlayerView(player, visibility, row, col);
        AnchorPane.setTopAnchor(playerView.getComponent(), 0.0);
        AnchorPane.setBottomAnchor(playerView.getComponent(), 0.0);
        AnchorPane.setLeftAnchor(playerView.getComponent(), 0.0);
        AnchorPane.setRightAnchor(playerView.getComponent(), 0.0);
        mainAnchorPane.getChildren().add(playerView.getComponent());
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


    public int getCardNumberInput(Deck deck) {
        int number;
        boolean validInput;
        do {
            validInput = true;
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Choose a card number");
            dialog.setHeaderText(null);
            dialog.setContentText("What card number would you like to choose?");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    number = Integer.parseInt(result.get());
                    if (!isValidCardNumberInput(number, deck)) {
                        showErrorPopup("Error: Enter a card that you have");
                        validInput = false;
                    }
                } catch (NumberFormatException e) {
                    showErrorPopup("Error: Enter a valid integer");
                    validInput = false;
                    number = -1;
                }
            } else {
                throw new IllegalStateException("Card number input dialog was closed.");
            }
        } while (!validInput);
        return number;
    }

    private boolean isValidCardNumberInput(int number, Deck deck) {
        return deck.hasCardWithNumber(number);
    }
    public void choixCarte(ActionEvent event) {
        cardNb = getCardNumberInput(player.getDeck());
    }


    public void choixSerie(ActionEvent event) {
        seriesNb = getIntegerInRange(1,4);
    }

    public boolean isValidIntegerInRange(int number, int min, int max) {
        return number >= min && number <= max;
    }
    public int getIntegerInRange(int min, int max) {
        Card card = new Card(cardNb);
        int number;
        boolean validInput;
        do {
            validInput = true;
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Enter a number within the range");
            dialog.setHeaderText(null);
            dialog.setContentText("What series number would you like to choose? For the card " + card);

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    number = Integer.parseInt(result.get());
                    if (!isValidIntegerInRange(number, min, max)) {
                        showErrorPopup("Error: Enter a number within the range (" + min + " - " + max + ")");
                        validInput = false;
                    }
                } catch (NumberFormatException e) {
                    showErrorPopup("Error: Enter a valid integer");
                    validInput = false;
                    number = min - 1;
                }
            } else {
                throw new IllegalStateException("Number input dialog was closed.");
            }
        } while (!validInput);
        return number;
    }


    public void rules(ActionEvent event) throws  IOException{

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText( "Nombre de joueurs : 2 à 10 joueurs\n" +
                "Principe : Les cartes ont deux valeurs :\n" +
                "– Un numéro allant de 1 à 104.\n" +
                "– Des symboles Têtes de bœufs (points de pénalité) allant de 1 à 7" +
                "But : Récolter le moins possible de têtes de bœufs.\n" +
                "Gagnant : Le gagnant est celui ayant comptabilisé le moins de têtes en fin de partie." +
                "Tous les joueurs prennent une carte de leur jeu pour la déposer face cachée devant eux sur la table.\n" +
                "Quand tout le monde a posé, on retourne les cartes.\n" +
                "Celui qui a déposé la carte la plus faible est le premier à jouer. Il pose alors sa carte dans la rangée de son choix.\n" +
                "Puis vient le tour de celui ayant posé la 2e carte la plus faible jusqu’à ce que tout le monde ait posé.\n" +
                "Les cartes d’une série sont toujours déposées les unes à côté des autres.\n" +
                "Répéter ce processus jusqu’à ce que les 10 cartes de chaque joueur soient épuisées.\n" +
                "\n" +
                "Disposition des cartes :\n" +
                "Chaque carte jouée ne peut convenir qu’à une seule série :\n" +
                "\n" +
                "Valeurs croissantes : Les cartes d’une série doivent toujours se succéder dans l’ordre croissant de leurs valeurs. On pose donc toujours une carte de plus forte valeur que la précédente.\n" +
                "La plus petite différence : Si vous avez le choix entre plusieurs séries : sachez qu’une carte doit toujours être déposée dans la série où la différence entre la dernière carte déposée et\n" +
                "la nouvelle est la plus faible.\n" +
                "Exemple : Vous avez un 22 : Vous devrez le poser après le 20 (différence de 2) et non après le 17 (différence de 5)."+
                "Série terminée : Lorsqu’une série est terminée (qu’elle comporte 5 cartes) : Alors, le joueur qui joue dans l’une de ces séries doit ramasser les 5 cartes de la série (sauf celle qu’il a posée qui forme le début d’une nouvelle série).\n" +
                "Carte trop faible : Si un joueur possède une carte si faible qu’elle ne peut entrer dans aucune des séries, alors il doit ramasser toutes les cartes d’une série de son choix. Sa carte faible représente alors la première carte d’une nouvelle série. (La série ramassée sera celle ayant le moins de TdB. Ces têtes sont des points négatifs en fin de partie)."+
                "Les Têtes de Bœufs (TdB) sont des points négatifs (le joueur qui en possède le moins gagne la partie).\n" +
                "Chaque carte, en plus de sa valeur présente un ou plusieurs symboles TdB. Chaque symbole TdB = 1 point négatif.\n" +
                "Les cartes :\n" +
                "Qui finissent par 5 possèdent 2 TdB\n" +
                "Qui finissent par 0 possèdent 3 TdB\n" +
                "Formant un doublet (11, 22, etc.) possèdent 5 TdB\n"+
                "Pile de TdB : Doit être posée devant vous sur la table. Les cartes ramassées iront dans cette pile et NE SONT PAS intégrées à votre main !"+
                "Lors que les joueurs ont joué leurs 10 cartes (ils n’ont plus de cartes en main), la manche prend fin.\n" +
                "Chaque joueur compte alors ses points négatifs dans sa pile de TdB.\n" +
                "On note le résultat de chaque joueur sur une feuille de papier et on commence une nouvelle manche.\n" +
                "On joue plusieurs manches jusqu’à ce que l’un des joueurs ait réuni en tout plus de 66 têtes de bœuf. Le vainqueur de la partie est alors le joueur qui a le moins de têtes de bœuf. Avant le début du jeu, il est bien sûr possible de convenir d’un autre total de points ou d’un nombre de manches maximum.");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");

        alert.showAndWait();
    }



}
