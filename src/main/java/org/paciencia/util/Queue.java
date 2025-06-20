package org.paciencia.util;

import org.paciencia.card.Card;

public class Queue {
    private LinkedList list;

    public Queue() {
        list = new LinkedList();
    }

    public void add(Card card) {
        list.add(card);
    }

    public Card remove() {
        if (isEmpty()) {
            throw new IllegalStateException("a fila está vazia");
        }
        return list.removeLast();
    }

    public Card get() {
        if (isEmpty()) {
            throw new IllegalStateException("a fila está vazia");
        }
        return list.get(size() - 1);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }
}
