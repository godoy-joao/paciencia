package org.paciencia.control;

import org.paciencia.card.Card;
import org.paciencia.card.Deck;
import org.paciencia.exception.InvalidMovementException;
import org.paciencia.exception.MovementNotAllowedException;
import org.paciencia.game.Render;
import org.paciencia.game.Solitaire;

import java.awt.*;

public class Controller {

    private static Card selectedCard;
    private static CardSource cardSource;
    private static Rectangle wasteRect;
    private static Rectangle pileRect;
    private static Rectangle[] foundationRects = new Rectangle[4];
    private static Rectangle[] columnRects = new Rectangle[7];

    public Controller() {
        wasteRect = new Rectangle(380, 40, 200, 140);
        pileRect = new Rectangle(160, 40, 200, 140);
        for (int i = 0; i < 4; i++) {
            foundationRects[i] = new Rectangle(680 + (i * 120), 40, 100, 140);
        }
        for (int i = 0; i < 7; i++) {
            columnRects[i] = new Rectangle(180 + (i * 140), 220, 100, Solitaire.HEIGHT - 221);
        }
    }

    public static void onClick(Point point) {
        if (selectedCard == null) {
            setSelectedCard(point);
            Render.hasChanges = true;
        } else {
            move(point);
        }
    }

    private static void move(Point point) {
        CardSource targetSource = getTargetSource(point);
        if (targetSource == CardSource.WASTE || targetSource == null) {
            throw new InvalidMovementException("Alvo inválido para movimento da carta.");
        }
        switch (targetSource) {
            case COLUMN -> {
                int columnIndex = getColumnIndex(point);
                if (columnIndex != -1) {
                    Card targetCard = Deck.columns[columnIndex].getLast();
                    if (validateCardToCardMovement(selectedCard, targetCard)) {
                        
                    }
                } else {
                    throw new InvalidMovementException("Alvo inválido para movimento da carta.");
                }
            }
            case FOUNDATION -> {
                int foundationIndex = getFoundationIndex(point);
                if (foundationIndex != -1) {

                } else {
                    throw new InvalidMovementException("Alvo inválido para movimento da carta.");
                }
            }
        }
    }

    private static boolean validateCardToCardMovement(Card selected, Card target) {
        if (!target.isFaceUp()) {
            throw new MovementNotAllowedException("Uma carta não pode ser movida para outra não revelada.");
        }
        if ((selected.isBlack() && target.isRed()) || (selected.isRed() && target.isBlack())) {
            if (selected.getRank() < target.getRank()) {
            return true;
            }
            throw new MovementNotAllowedException("Uma carta não pode ser movida para outra de menor ou igual valor.");
        } else {
            throw new MovementNotAllowedException("Uma carta não pode ser movida para outra de mesmo naipe.");
        }
    }

    private static int getColumnIndex(Point point) {
        for (int i = 0; i < columnRects.length; i++) {
            if (columnRects[i].contains(point)) {
                return i;
            }
        }
        return -1;
    }

    private static int getFoundationIndex(Point point) {
        for (int i = 0; i < foundationRects.length; i++) {
            if (foundationRects[i].contains(point)) {
                return i;
            }
        }
        return -1;
    }

    public static CardSource getTargetSource(Point point) {
        if (wasteRect.contains(point)) {
            return CardSource.WASTE;
        }
        for (int i = 0; i < columnRects.length; i++) {
            if (columnRects[i].contains(point)) {
                return CardSource.COLUMN;
            }
        }
        for (int i = 0; i < foundationRects.length; i++) {
            if (foundationRects[i].contains(point)) {
                return CardSource.FOUNDATION;
            }
        }
        return null;
    }


    public static void setSelectedCard(Point point) {
        if (pileRect.contains(point)) {
            flipPileCard();
            System.out.println("PILHA");
        }
        if (wasteRect.contains(point)) {
            selectedCard = Deck.waste.get();
            cardSource = CardSource.WASTE;
            System.out.println("DESCARTE");
            return;
        }
        for (int i = 0; i < columnRects.length; i++) {

            if (columnRects[i].contains(point)) {
                System.out.println("COLUNA "+i);
                Card card = null;
                for (int j = 0; j < Deck.columns[i].size(); j++) {
                    if (Deck.columns[i].get(j).contains(point) && Deck.columns[i].get(j).isFaceUp()) {
                        card = Deck.columns[i].get(j);
                        cardSource = CardSource.COLUMN;
                    }
                }
                selectedCard = card;
                return;
            }
        }
        for (int i = 0; i < foundationRects.length; i++) {
            if (foundationRects[i].contains(point)) {
                selectedCard = Deck.foundations[i].get();
                cardSource = CardSource.FOUNDATION;
                return;
            }
        }
    }

    private static void flipPileCard() {
        if (!Deck.pile.isEmpty()) {
            Card card = Deck.pile.remove();
            card.flip();
            Deck.waste.add(card);
        } else {
            if (!Deck.waste.isEmpty()) {
                while (!Deck.waste.isEmpty()) {
                    Card card = Deck.waste.remove();
                    card.flip();
                    Deck.pile.add(card);
                }
            }
        }
    }

    public static void clearSelectedCard() {
        selectedCard = null;
        cardSource = null;
    }

}
