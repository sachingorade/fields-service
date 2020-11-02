package com.test.fields.api.model;

import java.util.List;

public class FieldWeather {

    private List<Weather> weather;

    public FieldWeather() {
    }

    public FieldWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    @Override
    public String toString() {
        return "FieldWeather{" +
                "weather=" + weather +
                '}';
    }
}
