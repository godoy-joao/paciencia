package org.paciencia.game;

import org.paciencia.card.Card;
import org.paciencia.card.Deck;
import org.paciencia.control.Controller;
import org.paciencia.control.ControllerOld;
import org.paciencia.util.LinkedList;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Render {

    public static boolean hasChanges;

    public static void render(Graphics2D g) {
        for (int i = 0; i < Deck.columns.length; i++) {
            LinkedList column = Deck.columns[i];
            for (int j = 0; j < column.size(); j++) {
                Card card = column.get(j);
                int cardX = 180 + (i * 140);
                int cardY = 220 + (j * 30);
                card.setBounds(cardX, cardY, 100, 140);
                BufferedImage cardImage = (card.isFaceUp()) ? card.getFace() : Card.getBack();
                g.drawImage(cardImage, cardX, cardY, null);
            }
        }
        if (!Deck.pile.isEmpty()) {
            int cardsToShow = Math.min(3, Deck.pile.size());
            for (int i = 0; i < cardsToShow; i++) {
                int cardX = 160 + (i * 50);
                int cardY = 40;
                g.drawImage(Card.getBack(), cardX, cardY, null);
            }
        }

        if (!Deck.waste.isEmpty()) {
            int cardsToShow = Math.min(3, Deck.waste.size());
            for (int i = cardsToShow; i > 0; i--) {
                int cardX = 530 - (i * 50);
                int cardY = 40;
                int cardIndex = Deck.waste.size() - i;
                Card card = Deck.waste.get(cardIndex);
                g.drawImage(card.getFace(), cardX, cardY, null);
            }
        }
        for (int i = 0; i < Deck.foundations.length; i++) {
            int cardX = 680 + (i * 120);
            int cardY = 40;
            if (Deck.foundations[i].isEmpty()) {
                g.drawImage(Card.getBackLowOpacity(), cardX, cardY, null);
            } else {
                g.drawImage(Deck.foundations[i].get().getFace(), cardX, cardY, null);
            }
        }

        hasChanges = false;

        drawOutline(g);
    }

    private static void drawOutline(Graphics2D g) {
        if (Controller.getSelectedCard() != null) {
            g.setColor(Color.BLACK);
            Rectangle rect = Controller.getSelectedCard().getBounds();
            g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(rect);
        }
    }

}
