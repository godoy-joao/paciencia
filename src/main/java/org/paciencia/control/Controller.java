package org.paciencia.control;

import org.paciencia.card.Card;
import org.paciencia.card.Deck;
import org.paciencia.card.Suit;
import org.paciencia.exception.InvalidMovementException;
import org.paciencia.exception.MovementNotAllowedException;
import org.paciencia.game.Render;
import org.paciencia.game.Solitaire;
import org.paciencia.util.LinkedList;

import java.awt.*;

public class Controller {

    private static Card selectedCard;
    private static int selectedCards;
    private static CardSource cardSource;
    private static int horizontalIndex;
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

    public static int selectedAmount() {
        return selectedCards;
    }

    public static void onClick(Point point) {
        if (selectedCard == null) {
            setSelectedCard(point);
        } else {
            if (!move(point)) {
                setSelectedCard(point);
            }
        }
        updateCardPositions();
        Render.hasChanges = true;
    }

    private static boolean move(Point point) {
        CardSource targetSource = getTargetSource(point);
        if (targetSource == CardSource.WASTE || targetSource == null) {
            throw new InvalidMovementException("Alvo inválido para movimento da carta.");
        }
        switch (targetSource) {
            case COLUMN -> {
                int columnIndex = getColumnIndex(point);
                if (columnIndex != -1) {
                    Card targetCard = Deck.columns[columnIndex].getLast();
                    switch (cardSource) {
                        case WASTE -> {
                            if (validateCardToCardMovement(selectedCard, targetCard)) {
                                Deck.columns[columnIndex].add(Deck.waste.remove());
                                clearSelectedCard();
                                Render.hasChanges = true;
                                return true;
                            } else {
                                clearSelectedCard();
                                return false;
                            }
                        }
                        case COLUMN -> {
                            if (validateCardToCardMovement(selectedCard, targetCard)) {
                                int selectedIndex = Deck.columns[horizontalIndex].getIndex(selectedCard);
                                LinkedList cardsToMove = new LinkedList();
                                int columnSize = Deck.columns[horizontalIndex].size();
                                for (int i = selectedIndex; i < columnSize; i++) {
                                    Card card = Deck.columns[horizontalIndex].get(i);
                                    cardsToMove.add(card);
                                    System.out.println("Selected: "+card.getSuit() + " "+card.getRank());
                                }
                                System.out.println("Cartas a mover: "+cardsToMove.size());
                                for (int i = 0; i < cardsToMove.size(); i++) {
                                    Deck.columns[horizontalIndex].removeLast();
                                    System.out.println("Removido");
                                }
                                if (selectedIndex > 0 && !Deck.columns[horizontalIndex].isEmpty()) {
                                    Deck.columns[horizontalIndex].get(selectedIndex - 1).flipUp();
                                }
                                System.out.println("Cartas a adicionar: "+cardsToMove.size());
                                int cardsToAdd = cardsToMove.size();
                                for (int i = 0; i < cardsToAdd; i++) {
                                    Card card = cardsToMove.removeFirst();
                                    System.out.println(i+". Adicionando carta: "+card.getSuit() + " "+card.getRank());
                                    Deck.columns[columnIndex].add(card);
                                }

                                clearSelectedCard();
                                Render.hasChanges = true;
                                return true;
                            } else {
                                clearSelectedCard();
                                Render.hasChanges = true;
                                return false;
                            }
                        }
                        case FOUNDATION -> {
                            Deck.columns[columnIndex].add(Deck.foundations[horizontalIndex].remove());
                            Render.hasChanges = true;
                        }
                    }
                    clearSelectedCard();
                    Render.hasChanges = true;
                    return true;
                } else {
                    throw new InvalidMovementException("Alvo inválido para movimento da carta.");
                }
            }
            case FOUNDATION -> {
                int foundationIndex = getFoundationIndex(point);
                if (foundationIndex != -1) {
                    if (Deck.foundations[foundationIndex].isEmpty()) {
                        if (selectedCard.getRank() == 1) {
                            switch (cardSource) {
                                case COLUMN -> {
                                    int selectedIndex = Deck.columns[horizontalIndex].getIndex(selectedCard);
                                    if (Deck.columns[horizontalIndex].getLast() != selectedCard) {
                                        throw new InvalidMovementException("Não pode retirar a carta do meio da coluna.");
                                    } else {
                                        Deck.foundations[foundationIndex].add(Deck.columns[horizontalIndex].remove(selectedIndex));
                                    }
                                    if (!Deck.columns[horizontalIndex].isEmpty()) {
                                        Deck.columns[horizontalIndex].get(selectedIndex - 1).flipUp();
                                    }
                                    clearSelectedCard();
                                    Render.hasChanges = true;
                                    return true;
                                }
                                case WASTE -> {
                                    Deck.foundations[foundationIndex].add(Deck.waste.remove());
                                    clearSelectedCard();
                                    Render.hasChanges = true;
                                    return true;
                                }
                            }
                        } else {
                            throw new InvalidMovementException("A primeira carta na base deve ser um Às.");
                        }
                    } else {
                        if (validateCardToFoundationMovement(selectedCard, Deck.foundations[foundationIndex].get())) {
                            switch (cardSource) {
                                case COLUMN -> {
                                    int selectedIndex = Deck.columns[horizontalIndex].getIndex(selectedCard);
                                    Card card = Deck.columns[horizontalIndex].remove(selectedIndex);
                                    if (!Deck.columns[horizontalIndex].isEmpty()) {
                                        Deck.columns[horizontalIndex].get(selectedIndex - 1).flipUp();
                                    }
                                    Deck.foundations[foundationIndex].add(card);
                                    clearSelectedCard();
                                    Render.hasChanges = true;
                                    return true;
                                }
                                case WASTE -> {
                                    Deck.foundations[foundationIndex].add(Deck.waste.remove());
                                    clearSelectedCard();
                                    Render.hasChanges = true;
                                    return true;
                                }
                            }
                            clearSelectedCard();
                            Render.hasChanges = true;
                            return false;
                        } else {
                            throw new InvalidMovementException("Movimento inválido.");
                        }
                    }
                    clearSelectedCard();
                    Render.hasChanges = true;
                    return false;
                } else {
                    throw new InvalidMovementException("Alvo inválido para movimento da carta.");
                }
            }
        }
        clearSelectedCard();
        return false;
    }

    private static void updateCardPositions() {
        for (int i = 0; i < Deck.columns.length; i++) {
            for (int j = 0; j < Deck.columns[i].size(); j++) {
                int cardX = 180 + (i * 140);
                int cardY = 220 + (j * 30);
                Deck.columns[i].get(j).setLocation(cardX, cardY);
            }
        }
        for (int i = 0; i < Deck.foundations.length; i++) {
            if (!Deck.foundations[i].isEmpty()) {
                for (int j = 0; j < Deck.foundations[i].size(); j++) {
                    int cardX = 680 + (i * 120);
                    Deck.foundations[i].get(j).setLocation(cardX, 40);
                }
            }
        }
        for (int i = Deck.waste.size(); i > 0; i--) {
            int cards = Math.min(3, Deck.waste.size());
            for (int j = cards; j > 0; j--) {
                int cardIndex = Deck.waste.size() - i;
                Deck.waste.get(cardIndex).setLocation(530 - (j * 50), 40);
            }
        }
    }

    private static boolean validateCardToFoundationMovement(Card selected, Card target) {
        if (target == null) {
            return true;
        }
        if (selected.getRank() != target.getRank() +1) {
            return false;
        }
        if (!(selected.getRank() <= target.getRank())) {
            switch (selected.getSuit()) {
                case CLUBS -> {
                    if (target.getSuit() == Suit.CLUBS) {
                        return true;
                    }
                }
                case HEARTS -> {
                    if (target.getSuit() == Suit.HEARTS) {
                        return true;
                    }
                }
                case SPADES -> {
                    if (target.getSuit() == Suit.SPADES) {
                        return true;
                    }
                }
                case DIAMONDS -> {
                    if (target.getSuit() == Suit.DIAMONDS) {
                        return true;
                    }
                }
            }
        } else {
            throw new MovementNotAllowedException("A carta selecionada deve ter um valor maior que a carta na base.");
        }
        return false;
    }

    private static boolean validateCardToCardMovement(Card selected, Card target) {
        if (selected.getRank() == 13 && target == null) {
            return true;
        }
        if (!target.isFaceUp()) {
            throw new MovementNotAllowedException("Uma carta não pode ser movida para outra não revelada.");
        }
        if ((selected.isBlack() && target.isRed()) || (selected.isRed() && target.isBlack())) {
            if (selected.getRank() == target.getRank() - 1) {
                return true;
            } else {
                if (selected.getRank() >= target.getRank()) {
                    throw new MovementNotAllowedException("Uma carta não pode ser movida para outra de menor ou igual valor.");
                } else {
                    throw new MovementNotAllowedException("Uma carta só pode ser movida para outra de valor imediatamente maior.");
                }
            }
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
        }
        if (wasteRect.contains(point)) {
            selectedCard = Deck.waste.get();
            cardSource = CardSource.WASTE;
            return;
        }
        for (int i = 0; i < columnRects.length; i++) {

            if (columnRects[i].contains(point)) {
                Card card = null;
                int selected = 0;
                int selectedIndex = 0;
                for (int j = 0; j < Deck.columns[i].size(); j++) {
                    if (Deck.columns[i].get(j).contains(point) && Deck.columns[i].get(j).isFaceUp()) {
                        card = Deck.columns[i].get(j);
                        cardSource = CardSource.COLUMN;
                        horizontalIndex = i;
                        selectedIndex = j;
                    }
                }

                LinkedList cardsToMove = new LinkedList();
                int columnSize = Deck.columns[horizontalIndex].size();
                for (int h = selectedIndex; h < columnSize; h++) {
                    selected++;
                }

                selectedCards = selected;
                selectedCard = card;
                return;
            }
        }
        for (int i = 0; i < foundationRects.length; i++) {
            if (foundationRects[i].contains(point)) {
                selectedCard = Deck.foundations[i].get();
                cardSource = CardSource.FOUNDATION;
                horizontalIndex = i;
                return;
            }
        }
    }

    private static void flipPileCard() {
        if (!Deck.pile.isEmpty()) {
            Card card = Deck.pile.remove();
            card.flipUp();
            Deck.waste.add(card);
        } else {
            if (!Deck.waste.isEmpty()) {
                while (!Deck.waste.isEmpty()) {
                    Card card = Deck.waste.remove();
                    card.flipDown();
                    Deck.pile.add(card);
                }
            }
        }
    }

    public static void clearSelectedCard() {
        selectedCard = null;
        cardSource = null;
        horizontalIndex = 0;
        Render.hasChanges = true;
        selectedCards = 0;
    }

    public static Card getSelectedCard() {
        return selectedCard;
    }
}
