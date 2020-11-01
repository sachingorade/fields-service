package com.test.fields.model;

import java.util.Map;

/*
This class can be made abstract and we can add subtypes for the sake of simplicity we make it concrete for now.
 */
public class GeoJson {
    private final String type = "Feature";
    private Map<String, String> properties;
    private Geometry geometry;
}
