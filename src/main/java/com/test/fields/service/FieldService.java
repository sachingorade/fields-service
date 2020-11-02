package com.test.fields.service;

import com.test.fields.agromonitoring.AmFieldMapper;
import com.test.fields.agromonitoring.PolygonApiClient;
import com.test.fields.agromonitoring.model.AmField;
import com.test.fields.exceptions.NotFoundException;
import com.test.fields.model.Field;
import com.test.fields.persistence.FieldRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;
    private final PolygonApiClient polygonApiClient;
    private final AmFieldMapper amFieldMapper;

    public FieldService(FieldRepository fieldRepository, PolygonApiClient polygonApiClient, AmFieldMapper amFieldMapper) {
        this.fieldRepository = fieldRepository;
        this.polygonApiClient = polygonApiClient;
        this.amFieldMapper = amFieldMapper;
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

        AmField polygon = polygonApiClient.createPolygon(amFieldMapper.toAmField(field));
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

        polygonApiClient.updatePolygon(fieldToUpdate.getPolygonId(), amFieldMapper.toAmField(fieldToUpdate));
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
}
