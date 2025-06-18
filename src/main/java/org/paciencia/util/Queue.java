package org.paciencia.util;

import org.paciencia.card.Card;

public class Queue {
    private LinkedList list;

    public Queue() {
        list = new LinkedList();
    }

    public void enqueue(Card card) {
        list.add(card);
    }

    public Card dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("a fila está vazia");
        }
        return list.remove(0);
    }

    public Card peek() {
        if (isEmpty()) {
            throw new IllegalStateException("a fila está vazia");
        }
        return list.get(0);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }
}
