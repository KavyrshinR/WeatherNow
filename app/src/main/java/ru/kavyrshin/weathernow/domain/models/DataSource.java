package ru.kavyrshin.weathernow.domain.models;


public class DataSource {

    public static final int INTERNET_DATA_SOURCE = 123;
    public static final int DISK_DATA_SOURCE = 1234;

    private int source;

    public DataSource(int source) {
        this.source = source;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}