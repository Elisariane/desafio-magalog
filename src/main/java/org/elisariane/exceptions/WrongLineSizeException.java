package org.elisariane.exceptions;

public class WrongLineSizeException extends RuntimeException {
    public WrongLineSizeException(String message) {
        super(message);
    }

    public WrongLineSizeException(String message, int expectedSize, int actualSize) {
        super(String.format("%s (Esperado: %d, Obtido: %d)", message, expectedSize, actualSize));
    }
}