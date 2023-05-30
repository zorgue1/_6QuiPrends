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

    Boolean aiTooWeak;
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

        display.printText("Do you want to play with an AI ?");
         String answer = scanner.getText();

         if (answer.equals("Yes"))
         {
             Deck deck = game.getPlayerDeck();
             List<Card> retrievedCards = new ArrayList<>();
             RetrievedPack pack = new RetrievedPack(retrievedCards);
             players.add(new AI("AI", deck, pack));
             display.printText("You decide to add an AI");
         }
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
                display.printTextInBlue("It's " + player.getName() + "'s turn");
                if (player instanceof AI) {
                    AI ai = (AI) player;
                    LinkedHashMap<Card, Series> hashMap = ai.chooseCard(seriesListInTable);
                    if (hashMap.size() > 0){
                        Random rd = new Random();
                        int rdValue = rd.nextInt(hashMap.size());
                        aiCard = ai.getCardFromHashMap(hashMap, rdValue);
                        aiSeries = hashMap.get(aiCard);
                    }
                    else
                    {
                        aiCard = ai.getCardTooWeak();
                        aiSeries = ai.getSeriesToRetrieve(seriesListInTable);
                        aiTooWeak = Boolean.TRUE;
                    }
                    int numberOfCard = aiCard.getNumber();
                    choosenNumberList.add(numberOfCard);
                   display.printText("AI has finished to choose");
                }
                else{
                    display.printText("Which card do you want to play?");
                    display.printText("Your deck : " + player.getDeck().toString());
                    //choisir la carte
                    int number = scanner.getInteger(); //peut etre faire méthode pour verifier que le nombre appartient bien a la liste
                    choosenNumberList.add(number);
                    getPlayerFromChoosenCard.put(number, player); //si renvoie nombre
                }
            }

            Collections.sort(choosenNumberList);

            for (int number : choosenNumberList)
            {
                Card playerCard = new Card(number);

                if (number == aiCard.getNumber())
                {
                    if (!aiTooWeak)
                    {
                        if (aiSeries.getNbOfCard() == 5)
                        {
                            Series newSeries = Series.newSeries(aiSeries.getPosition(), playerCard);
                            seriesListInTable.set(aiSeries.getPosition(), newSeries); //a modifier car ici utilise direct index tout dépend ce qui retourne
                            game.removeCard(getPlayerFromChoosenCard.get(number), playerCard);
                            Player player = getPlayerFromChoosenCard.get(number);
                            List<Card> pack = player.getPack().getCards();
                            pack.addAll(aiSeries.getCardsInTable());
                            player.setPack(new RetrievedPack(pack));
                            display.printText("This series is full so AI has retrieved the series " + aiSeries.getPosition() + ". Its card becomes the first card of the serie");
                        }
                        else
                        {
                            game.removeCard(getPlayerFromChoosenCard.get(number), playerCard);
                            game.addInSeries(aiSeries, playerCard);
//                                game.addPack(getPlayerFromChoosenCard.get(number), choosenSeries.getCardsInTable());
                            display.printText("AI choose the series " + aiSeries.getPosition() + " for the card " + playerCard.toString());
                        }
                    }
                    else
                    {
                        seriesListInTable.set(aiSeries.getPosition(), Series.newSeries(aiSeries.getPosition(), playerCard)); //nouvelle serie 1 avec la carte joueur
                        game.removeCard(getPlayerFromChoosenCard.get(number), playerCard);
                        Player player = getPlayerFromChoosenCard.get(number);
                        List<Card> pack = player.getPack().getCards();
                        pack.addAll(aiSeries.getCardsInTable());
                        player.setPack(new RetrievedPack(pack));
                        display.printTextInRed("AI card is too weak. He retrieved the series " + aiSeries.getPosition());
                    }

                }

                display.printTextInBlue("It's " + getPlayerFromChoosenCard.get(number) + "'s turn");

                boolean isPossible = false;
                while (!isPossible)
                {
                    display.printText("Please choose the series you want to deposit for " + playerCard.toString());
                    display.displayAllSeries(seriesListInTable);
                    int index = scanner.getInteger(); //méthode qui verifie
                    //recuperer la serie que le joueur a choisi

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
                                Player player = getPlayerFromChoosenCard.get(number);
                                List<Card> pack = player.getPack().getCards();
                                pack.addAll(choosenSeries.getCardsInTable());
                                player.setPack(new RetrievedPack(pack));
                                isPossible = true; // sortir de la boucle while
                            }
                            else
                            {
                                game.removeCard(getPlayerFromChoosenCard.get(number), playerCard);
                                game.addInSeries(choosenSeries, playerCard);
//                                game.addPack(getPlayerFromChoosenCard.get(number), choosenSeries.getCardsInTable());
                                display.printText("You choose the series " + choosenSeries.getPosition() + " for the card " + playerCard.toString());
                                isPossible = true;
                            }
                        }
                        else
                            display.printTextInRed("You can't choose this series because the difference with the last is not the smallest ");
                    }
                    else if (game.isCardTooWeak(playerCard))
                    {
                        //carte trop faible
                        //choisi le paquet qu'il souhaite recuperer
                        display.printTextInRed("Your card is too weak, please choose the series you want to take");
                        int i = scanner.getInteger(); // methode verifie
                        Series takenSeries = seriesListInTable.get(i-1); //choisi serie 1
                        seriesListInTable.set(i-1, Series.newSeries(takenSeries.getPosition(), playerCard)); //nouvelle serie 1 avec la carte joueur
                        game.removeCard(getPlayerFromChoosenCard.get(number), playerCard);
                        Player player = getPlayerFromChoosenCard.get(number);
                        List<Card> pack = player.getPack().getCards();
                        pack.addAll(takenSeries.getCardsInTable());
                        player.setPack(new RetrievedPack(pack));
//                        game.addPack(getPlayerFromChoosenCard.get(number), takenSeries.getCardsInTable());
                        isPossible = true;
                    }
                    else
                        display.printTextInRed("You can't choose this series because your card is smaller than the last card of the series");
                }
            }

        }

        List<Integer> pointList = new ArrayList<>();
        HashMap<Integer, Player> getPlayerByPoint = new HashMap<>();

        int j = 0;
        for (Player player : players)
        {
            int point = player.getPack().getTotalBeefHead();
            pointList.add(point);
            getPlayerByPoint.put(point, player);
        }

        int minPoint = Collections.min(pointList);
        //annoncer le gagnant

        display.printImportantInfo("The winner is " + getPlayerByPoint.get(minPoint));
    }

}
