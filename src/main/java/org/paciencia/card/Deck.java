package org.paciencia.card;

import java.util.*;

public class Deck {

    public static List<Card> cards;

    public static List<List<Card>> columns;
    public static List<List<Card>> foundations;
    public static List<Card> pile;
    public static List<Card> waste;

    public static void createDeck() {
        cards = new ArrayList<>();
        columns = new ArrayList<>();
        foundations = new ArrayList<>();
        pile = new ArrayList<>();
        waste = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (int i = 1; i < 14; i++) {
                cards.add(new Card(suit, i));
            }
        }
        Collections.shuffle(cards);
        distribute();
    }

    public static void distribute() {
        columns.clear();
        pile.clear();
        int index = 0;

        for (int i = 1; i <= 7; i++) {
            List<Card> coluna = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                coluna.add(cards.get(index++));
            }
            columns.add(coluna);
        }

        for (int i = index; i < cards.size(); i++) {
            pile.add(cards.get(i));
        }
    }


}
