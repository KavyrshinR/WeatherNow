package ru.kavyrshin.weathernow.model.exception;


public class CustomException extends RuntimeException {

    public static final int NETWORK_EXCEPTION = 1;
    public static final int SERVER_EXCEPTION = 1;

    private int id = 0;

    public CustomException(int id, String message) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}