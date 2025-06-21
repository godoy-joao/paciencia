package org.paciencia.card;

import org.paciencia.control.Controller;
import org.paciencia.game.Render;
import org.paciencia.util.*;

import java.util.Random;

public class Deck {

    public static LinkedList cards = new LinkedList();
    public static LinkedList[] columns;
    public static Stack[] foundations;
    public static Queue pile = new Queue();
    public static Queue waste = new Queue();

    public static void createDeck() {
        new Controller();
        columns = new LinkedList[]{
                new LinkedList(), new LinkedList(), new LinkedList(), new LinkedList(), new LinkedList(),
                new LinkedList(), new LinkedList()
        };

        foundations = new Stack[]{
                new Stack(), new Stack(), new Stack(), new Stack()
        };

        for (Suit suit : Suit.values()) {
            for (int i = 1; i < 14; i++) {
                cards.add(new Card(suit, i));
            }
        }
        LinkedList randomizedCards = new LinkedList();
        int size = cards.size();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            randomizedCards.add(cards.remove(random.nextInt(cards.size())));
        }
        cards = randomizedCards;
        distribute();
        Render.hasChanges = true;
    }

    public static void distribute() {
        int index = 0;
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < i; j++) {
                Card card = cards.get(index++);
                if (j == i - 1) card.flipUp();

                System.out.println("Para cima: "+card.isFaceUp()+", Valor: "+card.getSuit().name()+" "+card.getRank());
                card.setLocation(180 + (i * 140), 220 + (j*30));
                columns[i-1].add(card);
            }
        }
        for (int i = index; i < cards.size(); i++) {
            pile.add(cards.get(i));
        }
    }
}
