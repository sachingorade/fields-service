package com.test.fields.agromonitoring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AmWeather {

    private long dt;
    private AmMainWeather main;

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public AmMainWeather getMain() {
        return main;
    }

    public void setMain(AmMainWeather main) {
        this.main = main;
    }
}
