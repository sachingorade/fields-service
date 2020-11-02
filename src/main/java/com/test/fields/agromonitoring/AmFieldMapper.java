package com.test.fields.agromonitoring;

import com.test.fields.agromonitoring.model.AmField;
import com.test.fields.model.Field;
import org.springframework.stereotype.Component;

@Component
public class AmFieldMapper {

    public AmField toAmField(Field field) {
        return new AmField(field.getPolygonId(), field.getName(), field.getBoundaries().getGeoJson());
    }

}
