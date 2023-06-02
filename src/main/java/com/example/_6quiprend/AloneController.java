package com.example._6quiprend;

import com.isep._6quiprend.core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

//import static com.example._6quiprend.PlayerView.selectedCard;

public class AloneController {

    private Scene scene;
    private Stage primaryStage;

    private Parent root;

    @FXML Button suivant ;
    @FXML Button suivant2 ;
    @FXML Label texte;
    @FXML TextField nomJoueur;

    @FXML Button choixCarte;
    @FXML Button rules;
    private List<String> nomsJoueurs = new ArrayList<>();
    HelloApplication helloApplication;
    private Game game = new Game();
    private List<Player> players;
    private List<Series> seriesListInTable;
    private String playerName;
    private int seriesNb;
    private int cardNb;
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

    /*public void initialize(){
        texte.setText("Entrer le nom du joueur.");
    }*/

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
        players.add(player);

        game.addAIPlayer();
        Player ai = players.get(1);
        PlayerView aiView = new PlayerView(ai, false,0, 3);


        PlayerView playerView = new PlayerView(player, true,5, 3);



        game.initSeriesOnTable();

//        while(!game.areAllDecksEmpty(players))
//        {
            displayAllSeries();

            displayPlayerView(aiView);
            displayPlayerView(playerView);

//        List<Integer> chosenNumberList = getPlayerCardSelection(event);
//        HashMap<Integer, Player> getPlayerFromChosenCard = game.mapPlayersToChosenCards(chosenNumberList);
//
//        Collections.sort(chosenNumberList);
//        System.out.println("choosenNumberList" + chosenNumberList);


//        }






        Scene scene = new Scene(mainAnchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


//    private List<Integer> getPlayerCardSelection(ActionEvent event) {
//        List<Integer> chosenNumberList = new ArrayList<>();
//
//        for (Player player : players) {
//
//            if (player instanceof AI) {
//                AI ai = (AI) player;
//                Card aiCard = ai.getCard(seriesListInTable);
//
//                if (aiCard != null) {
//                    System.out.println("AI chose the card " + aiCard.toString());
//                } else {
//                    aiCard = ai.getCardTooWeak();
//                    System.out.println("AI chose the card too weak " + aiCard.toString());
//                }
//
//                int numberOfCard = aiCard.getNumber();
//                chosenNumberList.add(numberOfCard);
//                System.out.println("AI has finished choosing.");
//            } else {
//                System.out.println("Which card do you want to play?");
//                int number = btnConfirm(event).getNumber();
//                chosenNumberList.add(number);
//            }
//        }
//
//        return chosenNumberList;
//    }
//    public Card btnConfirm(ActionEvent e){
//        if (selectedCard != null) {
//            System.out.println("Selected card: " + selectedCard);
//            return selectedCard;
//        } else {
//            System.out.println("Aucune carte sélectionnée");
//            return null;
//        }
//    }
    public void displayPlayerView(PlayerView playerView){
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



    public void choixCarte(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Choisir un numéro de carte");
        dialog.setHeaderText(null);
        dialog.setContentText("Quel numéro de carte souhaitez-vous choisir?");

        dialog.showAndWait().ifPresent(numCarte -> {
            try {
                cardNb = Integer.parseInt(numCarte);
                System.out.println("Numéro de carte choisi : " + cardNb);

                TextInputDialog serieDialog = new TextInputDialog();
                serieDialog.setTitle("Choisir une série");
                serieDialog.setHeaderText(null);
                serieDialog.setContentText("Quelle série souhaitez-vous choisir entre 1 et 4?");

                serieDialog.showAndWait().ifPresent(numSerie -> {
                    try {
                        seriesNb = Integer.parseInt(numSerie);
                        System.out.println("Numéro de série choisi : " + seriesNb);
                        // Faites quelque chose avec le numéro de série choisi (stockage, traitement, etc.)
                    } catch (NumberFormatException e) {
                        System.out.println("Veuillez entrer un numéro de série valide");
                    }
                });
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un numéro de carte valide");
            }
        });
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
