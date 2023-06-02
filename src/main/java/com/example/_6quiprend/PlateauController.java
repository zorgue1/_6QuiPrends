package com.example._6quiprend;

import com.isep._6quiprend.core.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class PlateauController {


    private Scene scene;
    private Stage primaryStage;
    private Parent root;
    @FXML HBox hBox;

    @FXML Button rules;
    @FXML Button back;

    @FXML Button choixCarte;




    public void choixCarte(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Choisir un numéro de carte");
        dialog.setHeaderText(null);
        dialog.setContentText("Quel numéro de carte souhaitez-vous choisir?");

        dialog.showAndWait().ifPresent(numCarte -> {
            try {
                int numeroCarte = Integer.parseInt(numCarte);
                System.out.println("Numéro de carte choisi : " + numeroCarte);

                TextInputDialog serieDialog = new TextInputDialog();
                serieDialog.setTitle("Choisir une série");
                serieDialog.setHeaderText(null);
                serieDialog.setContentText("Quelle série souhaitez-vous choisir entre 1 et 4?");

                serieDialog.showAndWait().ifPresent(numSerie -> {
                    try {
                        int numeroSerie = Integer.parseInt(numSerie);
                        System.out.println("Numéro de série choisi : " + numeroSerie);
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


    public void switchScene(String fxml) throws IOException {
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
