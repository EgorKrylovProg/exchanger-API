package Exceptions;

public class NoDataFoundException extends Exception {

    public NoDataFoundException(String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        return String.format("{\n \"message\": \"%s\"\n}", getMessage());
    }
}
