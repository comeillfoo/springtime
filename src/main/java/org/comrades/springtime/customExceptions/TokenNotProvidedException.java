package org.comrades.springtime.customExceptions;

public class TokenNotProvidedException extends Exception {
    private String exceptionMessage;

    public TokenNotProvidedException(String message) {
        exceptionMessage = message;
    }

    public String getMessage() {
        return exceptionMessage;
    }
}
