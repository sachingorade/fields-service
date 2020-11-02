package com.test.fields.model;

import java.util.Map;
import java.util.Objects;

public class GeoFeature extends GeoJson {
    public static final String TYPE = "Feature";

    private Map<String, String> properties;
    private Geometry geometry;

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoFeature that = (GeoFeature) o;
        return Objects.equals(properties, that.properties) &&
                Objects.equals(geometry, that.geometry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties, geometry);
    }

    @Override
    public String toString() {
        return "GeoFeature{" +
                "properties=" + properties +
                ", geometry=" + geometry +
                '}';
    }
}
