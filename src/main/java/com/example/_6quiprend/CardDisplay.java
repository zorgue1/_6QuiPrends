package com.example._6quiprend;

import com.isep._6quiprend.core.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardDisplay extends Application {
    Game game = new Game();

    public void start(Stage primaryStage) throws Exception {
        BorderPane mainBorderPanel = new BorderPane();

        Deck deck = game.getPlayerDeck();
        List<Card> retrievedCards = new ArrayList<>();
        RetrievedPack pack = new RetrievedPack(retrievedCards);
        Player player = new Player("Test", deck, pack);
        PlayerView playerView = new PlayerView(player);
        mainBorderPanel.setCenter(playerView.getComponent());

        Scene scene = new Scene(mainBorderPanel, 1400, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
