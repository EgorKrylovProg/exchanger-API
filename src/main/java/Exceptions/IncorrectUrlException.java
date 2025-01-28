package Exceptions;

public class IncorrectUrlException extends Exception {

    public IncorrectUrlException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return String.format("{\n \"message\": \"%s\"\n}", getMessage());
    }
}
