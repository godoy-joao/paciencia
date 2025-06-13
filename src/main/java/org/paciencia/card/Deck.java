package org.paciencia.card;

import org.paciencia.game.Render;
import org.paciencia.game.Solitaire;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Deck {

    public static List<Card> cards;

    public static List<List<Card>> columns;
    public static List<List<Card>> foundations;
    public static List<Card> pile;
    public static List<Card> waste;
    public static List<List<Card>> lists;

    public static void createDeck() {
        lists = new ArrayList<>();
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
        initializePoints();
        Render.hasChanges = true;


    }

    public static void distribute() {
        columns.clear();
        pile.clear();
        int index = 0;

        for (int i = 1; i <= 7; i++) {
            List<Card> column = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                column.add(cards.get(index++));
            }
            columns.add(column);
        }

        for (int i = index; i < cards.size(); i++) {
            pile.add(cards.get(i));
        }
    }

    private static void initializePoints() {
        Render.columnPoints = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            Render.columnPoints.add(new Point(Solitaire.WIDTH / 6 + (i * 120), 220));
        }
        Render.foundationPoints = new ArrayList<>();
        for (int i = 0; i < foundations.size(); i++) {
            Render.foundationPoints.add(new Point(683 + (i * 120), 40));
        }
        Render.pilePoints = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Render.pilePoints.add(new Point(40 + (i * 30), 40));
        }
        Render.wastePoints = new ArrayList<>();
        for (int i = 0; i < waste.size(); i++) {
            Render.wastePoints.add(new Point(40 + (i * 80), 40));
        }
    }

    private static void addAllToMap() {
        
    }


}
