package com.test.fields.persistence;

import com.test.fields.model.Field;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidValueGenerator extends AbstractMongoEventListener<Field> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Field> event) {
        super.onBeforeConvert(event);
        Field source = event.getSource();
        if (source.getId() == null) {
            // As per Wikipedia, chance of generating duplicate UUID is very very low, so lets generate random UUID.
            source.setId(UUID.randomUUID());
        }
    }
}
