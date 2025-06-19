package org.paciencia.util;

import org.paciencia.card.Card;

public class No {
    Card card;
    No previous;
    No next;

    No(Card card) {
        this.card = card;
        this.previous = null;
        this.next = null;
    }
}
