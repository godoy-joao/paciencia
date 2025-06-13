package org.paciencia.game;

import org.paciencia.card.Card;
import org.paciencia.card.Deck;
import org.paciencia.control.Controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.List;
import java.util.ArrayList;

public class Render {

    public static boolean hasChanges;
    public static List<Point> columnPoints;
    public static List<Point> foundationPoints;
    public static List<Point> pilePoints;
    public static List<Point> wastePoints;

    public static void render(Graphics2D g) {
        for (int i = 0; i < Deck.columns.size(); i++) {
            List<Card> column = Deck.columns.get(i);
            Point point = columnPoints.get(i);
            for (int j = 0; j < column.size(); j++) {
                Card card = column.get(j);
                card.setBounds(point.x, point.y, 100, 140);
                BufferedImage cardImage = (card.isFaceUp()) ? card.getFace() : Card.getBack();
                g.drawImage(cardImage, point.x, point.y + (j * (int) (140 / 3)), null);
            }
        }
        for (int i = 0; i < pilePoints.size(); i++) {
            Card card = Deck.pile.get(i);
            Point point = pilePoints.get(i);
            card.setBounds(point.x, point.y, 100, 140);
            BufferedImage cardImage = (card.isFaceUp()) ? card.getFace() : Card.getBack();
            g.drawImage(cardImage, point.x, point.y , null);
        }
        hasChanges = false;
    }

    private static void drawOutline() {
        if (Controller.selectedCard != null) {

        }
    }

}
