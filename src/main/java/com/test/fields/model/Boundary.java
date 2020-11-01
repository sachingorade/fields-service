package com.test.fields.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class Boundary {

    private String id;
    private String polygonId;
    private LocalDateTime created;
    private LocalDateTime updated;
    private GeoJson geoJson;

    public Boundary() {
    }

    public Boundary(String id, String polygonId, LocalDateTime created, LocalDateTime updated, GeoJson geoJson) {
        this.id = id;
        this.polygonId = polygonId;
        this.created = created;
        this.updated = updated;
        this.geoJson = geoJson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getPolygonId() {
        return polygonId;
    }

    public void setPolygonId(String polygonId) {
        this.polygonId = polygonId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public GeoJson getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(GeoJson geoJson) {
        this.geoJson = geoJson;
    }
}
