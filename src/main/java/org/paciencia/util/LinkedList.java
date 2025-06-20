package org.paciencia.util;

import org.paciencia.card.Card;

public class LinkedList {
    private No head;
    private No tail;
    private int size;

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void add(Card card) {
        No novoNo = new No(card);
        if (head == null) {
            head = novoNo;
            tail = novoNo;
        } else {
            tail.next = novoNo;
            novoNo.previous = tail;
            tail = novoNo;
        }
        size++;
    }

    public Card remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Tamanho: " + size);
        }

        No current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }

        if (current.previous != null) {
            current.previous.next = current.next;
        } else {
            head = current.next;
        }

        if (current.next != null) {
            current.next.previous = current.previous;
        } else {
            tail = current.previous;
        }

        size--;
        return current.card;
    }

    public Card get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Tamanho: " + size);
        }

        No current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current.card;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(Card card) {
        No novoNo = new No(card);
        if (head == null) {
            head = novoNo;
            tail = novoNo;
        } else {
            novoNo.next = head;
            head.previous = novoNo;
            head = novoNo;
        }
        size++;
    }

    public Card getFirst() {
        if (isEmpty()) {
            return null;
        }
        return head.card;
    }

    public Card getLast() {
        if (isEmpty()) {
            return null;
        }
        return tail.card;
    }

    public Card removeLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Lista está vazia");
        }

        Card removedData = tail.card;

        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.previous;
            tail.next = null;
        }

        size--;
        return removedData;
    }

    public Card removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Lista está vazia");
        }

        Card removedData = head.card;

        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.previous = null;
        }
        size--;
        return removedData;
    }

    public boolean contains(Card card) {
        No current = head;
        while (current != null) {
            if (current.card.equals(card)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

}