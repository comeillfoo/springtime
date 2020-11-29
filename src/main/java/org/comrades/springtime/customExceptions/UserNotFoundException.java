package org.comrades.springtime.customExceptions;

public class UserNotFoundException extends Exception{
    private String exceptionMessage;

    public UserNotFoundException(String message) {
        exceptionMessage = message;
    }

    public String getMessage() {
        return exceptionMessage;
    }
}
