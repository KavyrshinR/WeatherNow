package ru.kavyrshin.weathernow.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CacheCity extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;

    private int utcOffset;
    private int dstOffset;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(int utcOffset) {
        this.utcOffset = utcOffset;
    }

    public int getDstOffset() {
        return dstOffset;
    }

    public void setDstOffset(int dstOffset) {
        this.dstOffset = dstOffset;
    }
}