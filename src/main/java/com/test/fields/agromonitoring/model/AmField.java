package com.test.fields.agromonitoring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.fields.model.GeoJson;

import java.util.Objects;

/*
For the sake of simplicity I reuse the inner data objects :)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AmField {

    private String id;
    private String name;
    @JsonProperty("geo_json")
    private GeoJson geoJson;

    public AmField() {
    }

    public AmField(String id) {
        this.id = id;
    }

    public AmField(String id, String name, GeoJson geoJson) {
        this.id = id;
        this.name = name;
        this.geoJson = geoJson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoJson getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(GeoJson geoJson) {
        this.geoJson = geoJson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmField amField = (AmField) o;
        return Objects.equals(id, amField.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AmField{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", geoJson=" + geoJson +
                '}';
    }
}
