package com.isep._6quiprend.console;

import com.isep._6quiprend.core.*;

import java.io.InputStream;
import java.util.*;

public class Main {

    Game game;
    Display display;
    Scanner scanner;
    public Main(InputStream is) {
        this.game = new Game();
        this.display = new Display();
        this.scanner = new Scanner(is);
    }

    public static void main(String[] args) {
        Main main = new Main(System.in);
        main.play();
    }

    private void play(){

        List<Card> allCard = game.getAllCard();
        List<Player> players = game.getPlayers();
        List<Series> seriesListInTable = game.getSeriesListInTable();
        Collections.shuffle(allCard);

        display.printText("Number of player?");

        int nbOfPlayer = scanner.getInteger(); //a recuperer à partir du choix d'utilisateur
        //création des joueurs avec leur paquet de jeu
        for (int i = 1; i<=nbOfPlayer; i++)
        {
            display.printText("Name of the player " + i);
            String name = scanner.getText(); //recuperer le nom saisi à partir de l'interface avec un index EX: name_1 pour player_1, name_2 pour player_2 etc
            Deck deck = game.getPlayerDeck();
            List<Card> retrievedCards = new ArrayList<>();
            RetrievedPack pack = new RetrievedPack(retrievedCards);
            players.add(new Player(name, deck, pack));
        }

        display.printText("Here the list of the players " + players.toString());

        //poser 4 cartes sur la table

        for (int i=1;i<=4; i++)
        {
            game.initSeries(i);
        }

        while (!game.areAllDeckEmpty(players))
        {
            display.printText("Here the series on the table ");
            display.displayAllSeries(seriesListInTable);

            List<Integer> choosenNumberList = new ArrayList<>(); //si retourne nombre de la carte choisie
//            HashMap<Card, Player> getPlayerFromChoosenCard = new HashMap<>();
            HashMap<Integer, Player> getPlayerFromChoosenCard = new HashMap<>();
            for (Player player : players)
            {
                display.printText("It's " + player.getName() + "'s turn");
                display.printText("Which card do you want to play?");
                display.printText("Your deck : " + player.getDeck().toString());
                //choisir la carte
                int number = scanner.getInteger(); //peut etre faire méthode pour verifier que le nombre appartient bien a la liste
                choosenNumberList.add(number);
                getPlayerFromChoosenCard.put(number, player); //si renvoie nombre
            }

            Collections.sort(choosenNumberList);

            for (int number : choosenNumberList)
            {
                Card playerCard = new Card(number);


                display.printText("It's " + getPlayerFromChoosenCard.get(number) + "'s turn");

                boolean isPossible = false;
                while (!isPossible)
                {
                    display.printText("Please choose the series you want to deposit for " + playerCard.toString());
                    display.displayAllSeries(seriesListInTable);
                    int index = scanner.getInteger(); //méthode qui verifie
                    //recuperer la serie que le joueur a choisi

                    ///erreuuurr
                    Series choosenSeries = seriesListInTable.get(index-1);
                    Card lastCardInSeries = choosenSeries.getLastCardOf();

                    if (lastCardInSeries.getNumber() < number) //carte joueur + grande oui
                    {

                        if (game.getTheSeriesWithSmallestDifference(playerCard).getPosition() == (choosenSeries.getPosition()))
                        {
                            if (choosenSeries.getNbOfCard() == 5)
                            {
                                display.printText("This series is full so you need to retrieve the card of this series. Therefore, your card becomes the first card of the serie");
                                Series newSeries = Series.newSeries(choosenSeries.getPosition(), playerCard);
                                seriesListInTable.set(index, newSeries); //a modifier car ici utilise direct index tout dépend ce qui retourne
                                game.removeCard(getPlayerFromChoosenCard.get(number), playerCard);
                                isPossible = true; // sortir de la boucle while
                            }
                            else
                            {
                                game.removeCard(getPlayerFromChoosenCard.get(number), playerCard);
                                System.out.println("choosenSeries " + choosenSeries);
                                game.addInSeries(choosenSeries, playerCard);
//                                game.addPack(getPlayerFromChoosenCard.get(number), choosenSeries.getCardsInTable());

                                Player player = getPlayerFromChoosenCard.get(number);
                                List<Card> pack = player.getPack().getCardsInPack();
                                pack.addAll(choosenSeries.getCardsInTable());
                                player.setPack(new RetrievedPack(pack));
                                System.out.println("retieved pack " + pack);
                                display.printText("You choose the series " + choosenSeries.getPosition() + " for the card " + playerCard.toString());
                                isPossible = true;
                            }
                        }
                        else
                            display.printText("You can't choose this series because the difference with the last is not the smallest ");
                    }
                    else if (game.isCardTooWeak(playerCard))
                    {
                        //carte trop faible
                        //choisi le paquet qu'il souhaite recuperer
                        display.printText("Your card is too weak, please choose the series you want to take");
                        int i = scanner.getInteger(); // methode verifie
                        Series takenSeries = seriesListInTable.get(i-1); //choisi serie 1
                        seriesListInTable.set(i-1, Series.newSeries(takenSeries.getPosition(), playerCard)); //nouvelle serie 1 avec la carte joueur
                        game.removeCard(getPlayerFromChoosenCard.get(number), playerCard);
                        Player player = getPlayerFromChoosenCard.get(number);
                        List<Card> pack = player.getPack().getCardsInPack();
                        pack.addAll(takenSeries.getCardsInTable());
                        player.setPack(new RetrievedPack(pack));
                        System.out.println("retieved pack " + pack);
//                        game.addPack(getPlayerFromChoosenCard.get(number), takenSeries.getCardsInTable());
                        isPossible = true;
                    }
                    else
                        display.printText("You can't choose this series because your card is smaller than the last card of the series");
                }
            }

        }

        List<Integer> pointList = new ArrayList<>();
        HashMap<Integer, Player> getPlayerByPoint = new HashMap<>();
        System.out.println("PLayers " + players);
        int j = 0;
        for (Player player : players)
        {
            System.out.println("player " + player);
            int point = player.getPack().getTotalBeefHead();
            System.out.println("point " + point);
            pointList.add(point);
            getPlayerByPoint.put(point, player);
            System.out.println("getPlayerByPoint " + getPlayerByPoint);
        }

        int minPoint = Collections.min(pointList);
        //annoncer le gagnant


        display.printText("POINTS : " + getPlayerByPoint);
        display.printText("The winner is " + getPlayerByPoint.get(minPoint));
    }

}
