package com.test.fields.service;

import com.test.fields.exceptions.NotFoundException;
import com.test.fields.model.Field;
import com.test.fields.persistence.FieldRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;

    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public Field createField(Field field) {
        /*
        This is a create field method so we should always create a new field regardless of what id is set in the given data.
         */
        field.setId(null);
        return fieldRepository.save(field);
    }

    public void deleteField(Field field) {
        fieldRepository.delete(field);
    }

    public Field createOrUpdateField(Field field) {
        return fieldRepository.save(field);
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
