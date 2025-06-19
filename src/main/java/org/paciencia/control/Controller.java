package org.paciencia.control;

import org.paciencia.card.*;
import org.paciencia.game.Render;
import org.paciencia.game.Solitaire;

import java.awt.*;

public class Controller {

    public static Card selectedCard;
    public static final Rectangle wasteRect = new Rectangle(380, 40, 200, 140);
    public static final Rectangle pileRect = new Rectangle(160, 40, 200, 140);
    public static final Rectangle[] foundationRects = {
            new Rectangle(680, 40, 100, 140),
            new Rectangle(800, 40, 100, 140),
            new Rectangle(920, 40, 100, 140),
            new Rectangle(1020, 40, 100, 140)
    };

    public static final Rectangle[] columnRects = {
            new Rectangle(160, 220, 100, Solitaire.HEIGHT - 221),
            new Rectangle(300, 220, 100, Solitaire.HEIGHT - 221),
            new Rectangle(440, 220, 100, Solitaire.HEIGHT - 221),
            new Rectangle(580, 220, 100, Solitaire.HEIGHT - 221),
            new Rectangle(720, 220, 100, Solitaire.HEIGHT - 221),
            new Rectangle(860, 220, 100, Solitaire.HEIGHT - 221),
            new Rectangle(1020, 220, 100, Solitaire.HEIGHT - 221),
    };

    public static Card selectCard(Point point) {
        Card card = null;
        if (selectedCard == null) {

        }
        for (int i = 0; i < Deck.cards.size(); i++) {
            card = Deck.cards.get(i);
            if (card.contains(point) && card.isFaceUp()) {
                selectedCard = card;
                Render.hasChanges = true;
            }
        }
        return card;
    }

    public static void move(Point point) {
        Card target = selectCard(point);


    }

}
