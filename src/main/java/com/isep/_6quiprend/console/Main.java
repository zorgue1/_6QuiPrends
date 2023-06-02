package com.isep._6quiprend.console;

import com.isep._6quiprend.core.*;

import java.io.InputStream;
import java.util.*;

public class Main {

    private Game game;
    private Display display;
    private Scanner scanner;

    private List<Player> players;
    private List<Series> seriesListInTable;

    public Main(InputStream is) {
        this.game = new Game();
        this.display = new Display();
        this.scanner = new Scanner(is);
    }

    public static void main(String[] args) {
        Main main = new Main(System.in);
        main.play();
    }

    private void play() {
//        List<Card> allCard = game.getAllCard();
        players = game.getPlayers();
        seriesListInTable = game.getSeriesListInTable();

        game.shuffleCards();

        int nbOfPlayers = getNumberOfPlayers();
        boolean playWithAI = getPlayWithAI();

        if (playWithAI) {
            game.addAIPlayer();
            display.printText("You decided to add an AI.");
        }

        addHumanPlayers(nbOfPlayers);

        display.printText("Here is the list of players: " + players.toString());

        game.initSeriesOnTable();

        while (!game.areAllDecksEmpty(players)) {
            display.printText("Here are the series on the table:");
            display.displayAllSeries(seriesListInTable);

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

    private int getNumberOfPlayers() {
        display.printText("Number of players?");
        return scanner.getIntegerInRange(1, 10);
    }

    private boolean getPlayWithAI() {
        String answer = scanner.getTextWithPrompt("Do you want to play with an AI?");
        return answer.equalsIgnoreCase("Yes");
    }



    private void addHumanPlayers(int numberOfPlayers) {
        for (int i = 1; i <= numberOfPlayers; i++) {
            display.printText("Name of player " + i);
            String name = scanner.getText();
            Deck deck = game.getPlayerDeck();
            List<Card> retrievedCards = new ArrayList<>();
            RetrievedPack pack = new RetrievedPack(retrievedCards);
            players.add(new Player(name, deck, pack));
        }
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
    }






}
