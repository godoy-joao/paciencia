package org.paciencia.game;

import org.paciencia.card.Card;
import org.paciencia.card.Deck;
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
                int cardY = 220 + (j*30);
                card.setBounds(cardX, cardY, 100, 140);
                BufferedImage cardImage = (card.isFaceUp()) ? card.getFace() : Card.getBack();
                g.drawImage(cardImage, cardX, cardY, null);
            }
        }
        if (Deck.pile.size() > 3) {
            for (int i = 0; i < 3; i++) {
                int cardX = Solitaire.WIDTH / 8 + (i * 40);
                int cardY = 40;
                g.drawImage(Card.getBack(), cardX, cardY, null);
            }
        } else if (!Deck.pile.isEmpty()){
            for (int i = 0; i < Deck.pile.size(); i++) {
                int cardX = Solitaire.WIDTH / 8 + (i * 40);
                int cardY = 40;
                g.drawImage(Card.getBack(), cardX, cardY, null);
            }
        }
        hasChanges = false;

        drawOutline(g);
    }

    private static void drawOutline(Graphics2D g) {
        if (ControllerOld.selectedCard != null) {
            g.setColor(Color.BLACK);
            Rectangle rect = ControllerOld.selectedCard.getBounds();
            g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(rect);
        }
    }

}
