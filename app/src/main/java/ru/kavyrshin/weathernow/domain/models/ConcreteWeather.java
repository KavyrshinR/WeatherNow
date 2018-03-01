package ru.kavyrshin.weathernow.domain.models;



public class ConcreteWeather {

    private String cityName;
    private WeatherListElement weatherListElement;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public WeatherListElement getWeatherListElement() {
        return weatherListElement;
    }

    public void setWeatherListElement(WeatherListElement weatherListElement) {
        this.weatherListElement = weatherListElement;
    }
}
