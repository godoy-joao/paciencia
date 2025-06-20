package org.paciencia.exception;

import org.paciencia.control.Controller;

public class MovementNotAllowedException extends RuntimeException {
    public MovementNotAllowedException(String message) {
        super(message);
        Controller.clearSelectedCard();
    }
}
