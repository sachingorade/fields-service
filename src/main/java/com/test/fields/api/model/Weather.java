package com.test.fields.api.model;

public class Weather {

    private long timestamp;
    private float temperature;
    private int humidity;
    private float temperatureMax;
    private float temperatureMin;

    public Weather() {
    }

    public Weather(long timestamp, float temperature, int humidity, float temperatureMax, float temperatureMin) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.humidity = humidity;
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public float getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getTemperatureMax() {
        return temperatureMax;
    }

    public float getTemperatureMin() {
        return temperatureMin;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "timestamp=" + timestamp +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", temperatureMax=" + temperatureMax +
                ", temperatureMin=" + temperatureMin +
                '}';
    }
}
