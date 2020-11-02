package com.test.fields.service;

import com.test.fields.agromonitoring.AmModelMapper;
import com.test.fields.agromonitoring.PolygonApiClient;
import com.test.fields.agromonitoring.WeatherApiClient;
import com.test.fields.agromonitoring.model.AmField;
import com.test.fields.agromonitoring.model.AmFieldWeather;
import com.test.fields.api.model.FieldWeather;
import com.test.fields.exceptions.NotFoundException;
import com.test.fields.model.Field;
import com.test.fields.persistence.FieldRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;
    private final PolygonApiClient polygonApiClient;
    private final WeatherApiClient weatherApiClient;
    private final AmModelMapper amModelMapper;

    public FieldService(FieldRepository fieldRepository, PolygonApiClient polygonApiClient, WeatherApiClient weatherApiClient,
                        AmModelMapper amModelMapper) {
        this.fieldRepository = fieldRepository;
        this.polygonApiClient = polygonApiClient;
        this.weatherApiClient = weatherApiClient;
        this.amModelMapper = amModelMapper;
    }

    @Transactional
    public Field createField(Field field) {
        /*
        This is a create field method so we should always create a new field regardless of what id is set in the given data.
         */
        field.setId(null);
        LocalDateTime created = LocalDateTime.now();
        field.setCreated(created);
        field.getBoundaries().setCreated(created);

        AmField polygon = polygonApiClient.createPolygon(amModelMapper.toAmField(field));
        field.setPolygonId(polygon.getId());

        return fieldRepository.save(field);
    }

    @Transactional
    public void deleteField(Field field) {
        Optional<Field> optionalField = fieldRepository.findById(field.getId());
        optionalField.ifPresent(this::deleteExistingField);
    }

    private void deleteExistingField(Field field) {
        polygonApiClient.deletePolygon(field.getPolygonId());
        fieldRepository.delete(field);
    }

    @Transactional
    public Field createOrUpdateField(Field field) {
        Optional<Field> optionalField = fieldRepository.findById(field.getId());
        if (optionalField.isEmpty()) {
            return createField(field);
        }
        return updateField(optionalField.get());
    }

    private Field updateField(Field fieldToUpdate) {
        LocalDateTime updated = LocalDateTime.now();
        fieldToUpdate.setUpdated(updated);
        fieldToUpdate.getBoundaries().setUpdated(updated);

        polygonApiClient.updatePolygon(fieldToUpdate.getPolygonId(), amModelMapper.toAmField(fieldToUpdate));
        return fieldRepository.save(fieldToUpdate);
    }

    public Field getField(UUID fieldId) {
        Optional<Field> optionalField = fieldRepository.findById(fieldId);
        return optionalField.orElseThrow(() -> new NotFoundException("Field with id:[" + fieldId + "] not found."));
    }

    public List<Field> getFieldsByPageAndSize(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return fieldRepository.findAllBy(pageable);
    }

    /*
    This method assumes that weather is requested for last 7 days
     */
    public FieldWeather getFieldWeather(UUID fieldId) {
        Instant now = Instant.now();
        Instant last7thDay = now.minus(Duration.ofDays(7));
        long start = last7thDay.getEpochSecond();
        long end = now.getEpochSecond();

        Optional<Field> optionalField = fieldRepository.findById(fieldId);
        Field field = optionalField.orElseThrow(() -> new NotFoundException("Field with id:[" + fieldId + "] not found."));

        AmFieldWeather polygonWeather = weatherApiClient.getPolygonWeather(field.getPolygonId(), start, end);

        return amModelMapper.toFieldWeather(polygonWeather);
    }
}
