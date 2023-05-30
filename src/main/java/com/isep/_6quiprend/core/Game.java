package com.isep._6quiprend.core;

import java.util.*;

public class Game {

    private static int NBOFCARDBYDECK = 3;
    private List<Series> seriesListInTable = new ArrayList<>();
    private final List<Player> players;

    private List<Card> allCard;
    public Game(){
        this.allCard = Deck.createCards();
        this.players = new ArrayList<>();
    }

    public List<Series> getSeriesListInTable(){
        return this.seriesListInTable;
    }
    public List<Player> getPlayers(){
        return this.players;
    }

    public List<Card> getAllCard(){
        return this.allCard;
    }
    public void addPack(Player player, List<Card> cards){
        player.setPack(new RetrievedPack(cards));
    }
    public boolean areAllDeckEmpty(List<Player> players)
    {
        List<Boolean> stateList = new ArrayList<>();
        for (Player player : players)
        {
            Boolean isEmpty = player.getDeck().getCards().isEmpty();
            stateList.add(isEmpty);
        }
        if (stateList.indexOf(Boolean.FALSE) == -1)
            return true;
        else
            return false;
    }
    public void removeCard(Player player, Card card){
        List<Card> cards = player.getDeck().getCards();
        cards.remove(card);
        player.setDeck(new Deck(cards));
    }
    public void addInSeries(Series series, Card card){
        List<Card> cards = series.getCardsInTable();
        cards.add(card);
        series.setCardsInTable(cards);
    }
    public boolean isCardTooWeak(Card card){
        List<Boolean> list = new ArrayList<>();
        for (Series series : seriesListInTable)
        {
            list.add(series.getLastCardOf().getNumber() < card.getNumber());
        }
        if (list.indexOf(Boolean.TRUE) == -1)
            return true;
        else
            return false;

    }
    public Series getTheSeriesWithSmallestDifference(Card card){
        List<Integer> diffList = new ArrayList<>();
        List<Integer> positiveList = new ArrayList<>();
        for (Series series : this.seriesListInTable)
        {
            int difference = series.getDifferenceBetweenLastAndNew(card);
            diffList.add(difference);
            if (difference > 0)
                positiveList.add(difference);
        }
        int min = Collections.min(positiveList);

        int index = diffList.indexOf(min);
        return this.seriesListInTable.get(index);

    }



    public Deck getPlayerDeck(){
        List<Card> cardList = new ArrayList<>();
        for (int i = 0; i< NBOFCARDBYDECK; i++)
        {
            cardList.add(this.allCard.get(i));
            this.allCard.remove(this.allCard.get(i));
        }
        return new Deck(cardList);
    }

    public void initSeries(int position){
        List<Card> cardList = new ArrayList<>();
        cardList.add(this.allCard.get(0));
        this.allCard.remove(this.allCard.get(0));
        Series series = new Series(position, cardList);
        this.seriesListInTable.add(series);
    }

    public static void rules(){
        String rules = "Nombre de joueurs : 2 à 10 joueurs\n" +
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
                "On joue plusieurs manches jusqu’à ce que l’un des joueurs ait réuni en tout plus de 66 têtes de bœuf. Le vainqueur de la partie est alors le joueur qui a le moins de têtes de bœuf. Avant le début du jeu, il est bien sûr possible de convenir d’un autre total de points ou d’un nombre de manches maximum." ;
    }

}
