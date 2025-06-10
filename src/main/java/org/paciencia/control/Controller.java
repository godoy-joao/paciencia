package org.paciencia.control;

import org.paciencia.card.*;

import java.awt.*;

public class Controller {


    public Card getClickedCard(Point point) {
        for (Card card : Deck.cards) {
return card;
        }
        return null;
    }

}
