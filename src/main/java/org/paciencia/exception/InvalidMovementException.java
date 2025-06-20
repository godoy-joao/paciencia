package org.paciencia.exception;

import org.paciencia.control.Controller;

public class InvalidMovementException extends RuntimeException {
  public InvalidMovementException(String message) {
    super(message);
    Controller.clearSelectedCard();
  }
}
