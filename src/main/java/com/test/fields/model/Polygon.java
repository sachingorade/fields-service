package com.test.fields.model;

import java.util.List;

public class Polygon extends Geometry {
    public static final String TYPE = "Polygon";
    private List<List<List<Double>>> coordinates;

    public List<List<List<Double>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<List<Double>>> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "coordinates=" + coordinates +
                '}';
    }
}
