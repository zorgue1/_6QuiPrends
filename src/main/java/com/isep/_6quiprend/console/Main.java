package com.isep._6quiprend.console;

import com.isep._6quiprend.core.*;

import java.io.InputStream;
import java.util.*;

public class Main {

    Game game;
    Display display;
    Scanner scanner;


    Card aiCard;
    Series aiSeries;

    Boolean aiCardTooWeak = Boolean.FALSE;
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
        List<Card> allCard = game.getAllCard();
        List<Player> players = game.getPlayers();
        List<Series> seriesListInTable = game.getSeriesListInTable();
        Collections.shuffle(allCard);

        display.printText("Number of players?");
        int nbOfPlayers = scanner.getIntegerInRange(1, 10);

        String answer = scanner.getTextWithPrompt("Do you want to play with an AI?");


        if (answer.equalsIgnoreCase("Yes")) {
            Deck deck = game.getPlayerDeck();
            List<Card> retrievedCards = new ArrayList<>();
            RetrievedPack pack = new RetrievedPack(retrievedCards);
            players.add(new AI("AI", deck, pack));
            display.printText("You decided to add an AI.");
        }

        for (int i = 1; i <= nbOfPlayers; i++) {
            display.printText("Name of player " + i);
            String name = scanner.getText();
            Deck deck = game.getPlayerDeck();
            List<Card> retrievedCards = new ArrayList<>();
            RetrievedPack pack = new RetrievedPack(retrievedCards);
            players.add(new Player(name, deck, pack));
        }

        display.printText("Here is the list of players: " + players.toString());

        for (int i = 1; i <= 4; i++) {
            game.initSeries(i);
        }

        while (!game.areAllDecksEmpty(players)) {
            display.printText("Here are the series on the table:");
            display.displayAllSeries(seriesListInTable);

            List<Integer> chosenNumberList = new ArrayList<>();
            HashMap<Integer, Player> getPlayerFromChosenCard = new HashMap<>();

            for (Player player : players) {
                display.printTextInBlue("It's " + player.getName() + "'s turn");

                if (player instanceof AI) {
                    AI ai = (AI) player;
                    aiCard = ai.getCard(seriesListInTable);

                    if (aiCard != null) {
                        System.out.println("AI chose the card " + aiCard.toString());
                    } else {
                        aiCard = ai.getCardTooWeak();
                        System.out.println("AI chose the card too weak " + aiCard.toString());
                    }

                    int numberOfCard = aiCard.getNumber();
                    chosenNumberList.add(numberOfCard);
                    getPlayerFromChosenCard.put(numberOfCard, player);
                    display.printText("AI has finished choosing.");
                } else {
                    display.printText("Which card do you want to play?");
                    display.printText("Your deck: " + player.getDeck().toString());
                    int number = scanner.getCardNumberInput(player.getDeck());
                    chosenNumberList.add(number);
                    getPlayerFromChosenCard.put(number, player);
                }
            }

            Collections.sort(chosenNumberList);

            for (int number : chosenNumberList) {
                Card playerCard = new Card(number);
                Player currentPlayer = getPlayerFromChosenCard.get(number);

                display.printTextInBlue("It's " + currentPlayer.getName() + "'s turn");

                if (currentPlayer instanceof AI) {
                    AI aiPlayer = (AI) currentPlayer;
                    if (game.getTheSeriesWithSmallestDifference(playerCard) == null)
                        aiCardTooWeak = Boolean.TRUE;
                    else
                        aiCardTooWeak = Boolean.FALSE;

                    if (!aiCardTooWeak) {
                        aiSeries = game.getTheSeriesWithSmallestDifference(playerCard);

                        if (aiSeries.getNbOfCard() == 5) {
                            Series newSeries = Series.newSeries(aiSeries.getPosition(), playerCard);
                            seriesListInTable.set(aiSeries.getPosition() - 1, newSeries);
                            aiPlayer.removeCard(playerCard);
                            List<Card> pack = currentPlayer.getPack().getCards();
                            pack.addAll(aiSeries.getCardsInTable());
                            currentPlayer.setPack(new RetrievedPack(pack));
                            display.printText("This series is full, so AI has retrieved the series " + aiSeries.getPosition() + ". The AI's card becomes the first card of the series.");
                        } else {
                            game.addInSeries(aiSeries, playerCard);
                            aiPlayer.removeCard(playerCard);
                            display.printText("AI chose series " + aiSeries.getPosition() + " for the card " + playerCard.toString());
                        }
                    } else {
                        Series aiSeries = aiPlayer.getSeriesToRetrieve(seriesListInTable);
                        seriesListInTable.set(aiSeries.getPosition() - 1, Series.newSeries(aiSeries.getPosition(), playerCard));
                        aiPlayer.removeCard(playerCard);
                        List<Card> pack = currentPlayer.getPack().getCards();
                        pack.addAll(aiSeries.getCardsInTable());
                        currentPlayer.setPack(new RetrievedPack(pack));
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
                                    Series newSeries = Series.newSeries(chosenSeries.getPosition(), playerCard);
                                    seriesListInTable.set(index - 1, newSeries);
                                    currentPlayer.removeCard(playerCard);
                                    List<Card> pack = currentPlayer.getPack().getCards();
                                    pack.addAll(chosenSeries.getCardsInTable());
                                    currentPlayer.setPack(new RetrievedPack(pack));
                                    isPossible = true;
                                } else {
                                    game.addInSeries(chosenSeries, playerCard);
                                    currentPlayer.removeCard(playerCard);
                                    display.printText("You chose series " + chosenSeries.getPosition() + " for the card " + playerCard.toString());
                                    isPossible = true;
                                }
                            } else {
                                display.printTextInRed("You can't choose this series because the difference with the last card is not the smallest.");
                            }
                        } else if (game.isCardTooWeak(playerCard)) {
                            display.printTextInRed("Your card is too weak, please choose the series you want to take.");
                            int i = scanner.getIntegerInRange(1, seriesListInTable.size());
                            Series takenSeries = seriesListInTable.get(i - 1);
                            seriesListInTable.set(i - 1, Series.newSeries(takenSeries.getPosition(), playerCard));
                            currentPlayer.removeCard(playerCard);
                            List<Card> pack = currentPlayer.getPack().getCards();
                            pack.addAll(takenSeries.getCardsInTable());
                            currentPlayer.setPack(new RetrievedPack(pack));
                            isPossible = true;
                        } else {
                            display.printTextInRed("You can't choose this series because your card is smaller than the last card of the series.");
                        }
                    }
                }
            }
        }

        List<Integer> pointList = new ArrayList<>();
        HashMap<Integer, List<Player>> getPlayerByPoint = new HashMap<>();

        for (Player player : players) {
            int point = player.getPack().getTotalBeefHead();
            pointList.add(point);
            List<Player> playersWithSamePoint = getPlayerByPoint.getOrDefault(point, new ArrayList<>());
            playersWithSamePoint.add(player);
            getPlayerByPoint.put(point, playersWithSamePoint);
        }

        int minPoint = Collections.min(pointList);
        List<Player> winners = getPlayerByPoint.get(minPoint);

        display.printImportantInfo("The winner(s) with the lowest points: " + winners.toString());
    }


}
