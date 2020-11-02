package com.test.fields.agromonitoring;

import com.test.fields.agromonitoring.model.AmFieldWeather;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "weather-api-client", url = "${agromonitoring.api.weather.url}")
public interface WeatherApiClient {

    @GetMapping
    AmFieldWeather getPolygonWeather(@RequestParam("polyid")String id, @RequestParam("start")long start,
                                     @RequestParam("end")long end);

}
