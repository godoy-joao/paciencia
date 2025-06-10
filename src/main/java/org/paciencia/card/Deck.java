package org.paciencia.card;

import java.util.List;

public class Deck {

    public static List<Card> cards;



    public static void createDeck() {
        for (Suit suit : Suit.values()) {
            for (int i = 1; i < 14; i++) {
                cards.add(new Card(suit, i));
            }
        }
    }


}
