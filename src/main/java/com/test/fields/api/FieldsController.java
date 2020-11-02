package com.test.fields.api;

import com.test.fields.api.model.FieldWeather;
import com.test.fields.model.Field;
import com.test.fields.service.FieldService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class FieldsController {

    private final FieldService fieldService;

    public FieldsController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @PostMapping(ApiEndpoint.FIELDS)
    public Field createField(@RequestBody Field field) {
        return fieldService.createField(field);
    }

    @GetMapping(ApiEndpoint.FIELDS_WITH_FIELD_ID)
    public Field getField(@PathVariable(ApiEndpoint.FIELD_ID)UUID fieldId) {
        return fieldService.getField(fieldId);
    }

    @DeleteMapping(ApiEndpoint.FIELDS_WITH_FIELD_ID)
    public void deleteField(@PathVariable(ApiEndpoint.FIELD_ID)UUID fieldId) {
        fieldService.deleteField(new Field(fieldId));
    }

    @PutMapping(ApiEndpoint.FIELDS_WITH_FIELD_ID)
    public Field updateField(@RequestBody Field field) {
        return fieldService.createOrUpdateField(field);
    }

    @GetMapping(ApiEndpoint.FIELDS)
    public List<Field> getFieldsByPageAndSize(@RequestParam(value = "page", defaultValue = "0")Integer page,
                                              @RequestParam(value = "size", defaultValue = "20")Integer size) {
        return fieldService.getFieldsByPageAndSize(page, size);
    }

    @GetMapping(ApiEndpoint.FIELD_WEATHER)
    public FieldWeather getFieldWeather(@PathVariable(ApiEndpoint.FIELD_ID)UUID fieldId) {
        return fieldService.getFieldWeather(fieldId);
    }

}
