package com.test.fields.agromonitoring;

import com.test.fields.agromonitoring.model.AmField;
import com.test.fields.agromonitoring.model.AmFieldWeather;
import com.test.fields.agromonitoring.model.AmWeather;
import com.test.fields.api.model.FieldWeather;
import com.test.fields.api.model.Weather;
import com.test.fields.model.Field;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AmModelMapper {

    public AmField toAmField(Field field) {
        return new AmField(field.getPolygonId(), field.getName(), field.getBoundaries().getGeoJson());
    }

    public FieldWeather toFieldWeather(AmFieldWeather list) {
        return new FieldWeather(list.stream().map(this::toWeather).collect(Collectors.toList()));
    }

    private Weather toWeather(AmWeather amWeather) {
        return new Weather(amWeather.getDt(), amWeather.getMain().getTemp(), amWeather.getMain().getHumidity(),
                amWeather.getMain().getTempMin(), amWeather.getMain().getTempMax());
    }

}
