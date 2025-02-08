package org.elisariane.exceptions;

public class UnparseableLineException extends RuntimeException {
    public UnparseableLineException(String message) {
        super(message);
    }

    public UnparseableLineException(String message, Throwable cause) {
        super(message, cause);
    }
}