package com.test.fields.agromonitoring;

import com.test.fields.agromonitoring.model.AmField;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "polygon-api-client", url = "${agromonitoring.api.polygon.url}")
public interface PolygonApiClient {

    @PostMapping
    AmField createPolygon(@RequestBody AmField field);

    @DeleteMapping("/{polygonId}")
    void deletePolygon(@PathVariable("polygonId") String polygonId);

    @PutMapping("/{polygonId}")
    AmField updatePolygon(@PathVariable("polygonId") String polygonId, @RequestBody AmField field);
}
