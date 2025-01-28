package Exceptions;

public class DatabaseAccessException extends Exception {

    public DatabaseAccessException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return String.format("{\n \"message\": \"%s\"\n}", getMessage());
    }
}
