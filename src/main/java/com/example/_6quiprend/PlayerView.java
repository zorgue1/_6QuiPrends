package com.example._6quiprend;

import com.isep._6quiprend.core.*;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class PlayerView {


    private BorderPane component;
    private Pane centerArea;
    private Game game = new Game();

    int cardWidth = 65;
    int cardHeight = 115;

    Insets cardInsets = new Insets(2);
    public static Card selectedCard;

    int currEndRow = 7;
    int currEndCol = 4;

    Label endLabel;

    //---------------------------------------------------------------------------------------------

    public PlayerView(Player player, boolean isVisible, int row, int col) {
        this.component = new BorderPane();

        HBox buttonToolbars = new HBox();


        centerArea = new Pane();

        List<Card> cards = player.getDeck().getCards();

        for(Card card: cards) {

            CardView cardView = new CardView(card, cardWidth, cardHeight);
            Pane cardComponent = cardView.getComponent();
            // assign position..

            nodeSetLayoutAt(cardComponent, cardPosForRowCol(row, col));

            if (isVisible)
                cardComponent.setOnMouseClicked(e -> onMouseClickCard(e, cardView));
            else
                cardView.getFrontImageView().setVisible(isVisible);
            centerArea.getChildren().add(cardComponent);
//            cardView.getFrontImageView().setVisible(isVisible);
            col++;
        }



        component.setCenter(centerArea);

        component.setBottom(buttonToolbars);
    }

    private void onMouseClickCard(MouseEvent e, CardView cardView) {
        System.out.println("onMouseClickCard => toggle card " + cardView);
        cardView.toggleCard();
        selectedCard = cardView.getCard();
    }

    protected static void nodeSetLayoutAt(Node node, Point2D pt) {
        node.setLayoutX(pt.getX());
        node.setLayoutY(pt.getY());
    }
    protected Point2D cardPosForRowCol(int row, int col) {
        return new Point2D(10 + col * (cardInsets.getLeft() + cardWidth + cardInsets.getRight())
                , 10 + row * (cardInsets.getTop() + cardHeight + cardInsets.getBottom()));
    }


    //---------------------------------------------------------------------------------------------

    public Node getComponent() {
        return component;
    }



}