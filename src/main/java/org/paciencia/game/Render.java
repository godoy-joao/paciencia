package org.paciencia.game;

import org.paciencia.card.Card;
import org.paciencia.card.Deck;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Render {

    public static boolean hasChanges;
    public static List<Point> columnPoints;
    public static List<Point> foundationPoints;
    public static List<Point> pilePoints;
    public static List<Point> wastePoints;

    public static void render(Graphics2D g) {
        columnPoints = new ArrayList<>();
        for (int i = 0; i < Deck.columns.size(); i++) {
            columnPoints.add(new Point(273 + (i * 120), 220));
        }
        foundationPoints = new ArrayList<>();
        for (int i = 0; i < Deck.foundations.size(); i++) {
            foundationPoints.add(new Point(683 + (i * 120), 40));
        }
        pilePoints = new ArrayList<>();
        for (int i = 0; i < Deck.pile.size(); i++) {
            foundationPoints.add(new Point(40 + (i * 80), 40));
        }
        wastePoints = new ArrayList<>();
        for (int i = 0; i < Deck.waste.size(); i++) {
            wastePoints.add(new Point(40 + (i * 80), 40));
        }
    }

}
