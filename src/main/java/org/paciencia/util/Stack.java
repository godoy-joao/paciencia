package org.paciencia.util;

import org.paciencia.card.Card;

public class Stack {

    private LinkedList list;

    public Stack() {
        list = new LinkedList();
    }

    public void add(Card card) {
        list.add(card);
    }

    public Card remove() {
        if (isEmpty()) {
            throw new IllegalStateException("a pilha está vazia");
        }
        return list.remove(list.size() - 1);
    }

    public Card get() {
        if (isEmpty()) {
            throw new IllegalStateException("a pilha está vazia");
        }
        return list.get(list.size() - 1);
    }

    public Card get(int index) {
        if (isEmpty()) {
            throw new IllegalStateException("A pilha está vazia.");
        }
        return list.get(index);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }
}
