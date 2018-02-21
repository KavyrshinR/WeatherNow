package ru.kavyrshin.weathernow.domain.models;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CacheCity cacheCity = (CacheCity) o;

        if (id != cacheCity.id) return false;
        if (utcOffset != cacheCity.utcOffset) return false;
        if (dstOffset != cacheCity.dstOffset) return false;
        return name.equals(cacheCity.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + utcOffset;
        result = 31 * result + dstOffset;
        return result;
    }
}