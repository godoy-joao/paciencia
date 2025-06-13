package org.paciencia.control;

import org.paciencia.card.*;

import java.awt.*;

public class Controller {

    public static Card selectedCard;
    public static Card target;

    public Card getSelectedCard(Point point) {
        if (selectedCard == null) {
            selectedCard = getTarget(point);
            return selectedCard;
        }


        return null;
    }

    public Card getTarget(Point point) {
        for (Card card : Deck.cards) {
            if (card.contains(point) && card.isFaceUp())
                return card;
        }
        return null;
    }

}
