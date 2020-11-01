package com.test.fields.model;

import java.util.List;

public class Polygon extends Geometry {
    public static final String TYPE = "Polygon";
    private List<List<List<Double>>> coordinates;

}
