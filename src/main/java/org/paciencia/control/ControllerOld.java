package org.paciencia.control;

import org.paciencia.card.*;
import org.paciencia.exception.InvalidMovementException;
import org.paciencia.game.Render;
import org.paciencia.game.Solitaire;

import java.awt.*;

public class ControllerOld {

    public static Card selectedCard;
    private static CardSource cardSource;
    private static String targetName;
    private static Rectangle wasteRect;
    private static Rectangle pileRect;
    private static Rectangle[] foundationRects = new Rectangle[4];
    private static Rectangle[] columnRects = new Rectangle[7];

    public ControllerOld() {
        wasteRect = new Rectangle(380, 40, 200, 140);
        pileRect = new Rectangle(160, 40, 200, 140);
        for (int i = 0; i < 4; i++) {
            foundationRects[i] = new Rectangle(680 + (i * 120), 40, 100, 140);
        }
        for (int i = 0; i < 7; i++) {
            columnRects[i] = new Rectangle(180 + (i * 140), 220, 100, Solitaire.HEIGHT - 221);
        }
    }

    public static Card getSelectedCard() {
        return selectedCard;
    }

    public static void perform(Point point) {
        if (selectedCard == null) {
            if (targetName.equals("waste")) {

            }
            for (int i = 0; i < foundationRects.length; i++) {
                if (targetName.equals("foundation_" + i)) {

                }
            }
            for (int i = 0; i < columnRects.length; i++) {
                if (targetName.equals("column_" + i)) {
                    selectCard(point);
                }
            }
        } else {
            move(point);
        }
    }




    public static void onClick(Point point) {
        targetName = getTarget(point);
        if (targetName.isEmpty()) {
            selectedCard = null;
            return;
        }
        if (targetName.equals("pile")) {
            pileNextCard();
            return;
        }

        Render.hasChanges = true;
    }

    private static String getTarget(Point point) {
        if (pileRect.contains(point)) {
            return "pile";
        }
        if (wasteRect.contains(point)) {

        }
        for (int i = 0; i < foundationRects.length; i++) {
            if (foundationRects[i].contains(point)) {
                return "foundation_" + i;
            }
        }
        for (int i = 0; i < columnRects.length; i++) {
            if (columnRects[i].contains(point)) {
                return "column_" + i;
            }
        }
        return "";
    }

    private static void pileNextCard() {
        if (!Deck.pile.isEmpty()) {
            Deck.waste.add(Deck.pile.remove());

        } else {
            if (!Deck.waste.isEmpty()) {
                while (!Deck.waste.isEmpty()) {
                    Deck.pile.add(Deck.waste.remove());
                }
            }
        }
    }

    private static Card getWasteCard() {
        if (Deck.waste.isEmpty()) {
            throw new InvalidMovementException("Descarte está vazio.");
        } else {
            return Deck.waste.get();
        }
    }

    public static Card selectCard(Point point) {
        Card card = null;
        if (selectedCard == null) {
            for (int i = 0; i < Deck.cards.size(); i++) {
                card = Deck.cards.get(i);
                if (card.contains(point) && card.isFaceUp()) {
                    selectedCard = card;
                    Render.hasChanges = true;
                }
            }
        }
        return card;
    }

    public static String getTargetName(Point point) {
        if (wasteRect.contains(point)) {
            return "waste";
        }
        for (int i = 0; i < foundationRects.length; i++) {
            if (foundationRects[i].contains(point)) {
                return "foundation_" + i;
            }
        }
        for (int i = 0; i < columnRects.length; i++) {
            if (columnRects[i].contains(point)) {
                return "column_" + i;
            }
        }
        return "";
    }

    public static void move(Point point) {
        String target = getTargetName(point);


    }

}
