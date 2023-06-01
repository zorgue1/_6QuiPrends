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

    int currEndRow = 7;
    int currEndCol = 4;

    Label endLabel;

    //---------------------------------------------------------------------------------------------

    public PlayerView(Player player, boolean isVisible, int row, int col) {
        this.component = new BorderPane();

        HBox buttonToolbars = new HBox();


        centerArea = new Pane();

        List<Card> cards = player.getDeck().getCards();

//        int row = 5;
//        int col = 3;
        for(Card card: cards) {

            CardView cardView = new CardView(card, cardWidth, cardHeight);
            Pane cardComponent = cardView.getComponent();
            // assign position..

            nodeSetLayoutAt(cardComponent, cardPosForRowCol(row, col));


            centerArea.getChildren().add(cardComponent);
            cardView.getFrontImageView().setVisible(isVisible);
            col++;
        }



        component.setCenter(centerArea);

        component.setBottom(buttonToolbars);
    }

    private void onMouseClickCard(MouseEvent e, CardView cardView) {
        if (e.isSecondaryButtonDown() || e.isShiftDown() || e.isControlDown()) {
            // TODO ... animate move card to end..
            System.out.println("onMouseClickCard..RightButton => move card " + cardView + " to end");

            Pane cardComponent = cardView.getComponent();
            Point2D fromPt = new Point2D(cardComponent.getLayoutX(), cardComponent.getLayoutY());

            Point2D toPt = cardPosForRowCol(currEndRow, currEndCol);
            currEndCol++;
            nodeSetLayoutAt(endLabel, cardPosForRowCol(currEndRow, currEndCol));

            // remove then add cardView, so that it is on top of all others
            centerArea.getChildren().remove(cardComponent);
            centerArea.getChildren().add(cardComponent);

            // animate move card to end
            Point2D translate = toPt.subtract(fromPt);
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setNode(cardComponent);
            translateTransition.setDuration(Duration.millis(500));
            translateTransition.setToX(translate.getX());
            translateTransition.setToY(translate.getY());
            translateTransition.play();

        } else {
            System.out.println("onMouseClickCard => toggle card " + cardView);
            cardView.toggleCard();
        }
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