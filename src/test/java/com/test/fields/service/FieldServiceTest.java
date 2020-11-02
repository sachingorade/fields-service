package com.test.fields.service;

import com.test.fields.TestUtil;
import com.test.fields.exceptions.NotFoundException;
import com.test.fields.model.Field;
import com.test.fields.persistence.FieldRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import javax.lang.model.util.Types;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FieldServiceTest {

    @InjectMocks
    private FieldService fieldService;
    @Mock
    private FieldRepository fieldRepository;

    @Test
    void testCreateNewField() {
        Field field = TestUtil.getResource("sampledata/field.json", Field.class);
        fieldService.createField(field);

        assertNull(field.getId()); // Always create new field
        verify(fieldRepository).save(same(field));
    }

    @Test
    void testDeleteField() {
        Field field = TestUtil.getResource("sampledata/field.json", Field.class);
        fieldService.deleteField(field);

        verify(fieldRepository).delete(same(field));
    }

    @Test
    void testUpdateField() {
        Field field = TestUtil.getResource("sampledata/field.json", Field.class);
        fieldService.createOrUpdateField(field);

        verify(fieldRepository).save(same(field));
    }

    @Test
    void testGetFieldById() {
        Field field = TestUtil.getResource("sampledata/field.json", Field.class);
        when(fieldRepository.findById(any(UUID.class))).thenReturn(Optional.of(field));

        UUID fieldId = UUID.randomUUID();
        fieldService.getField(fieldId);

        verify(fieldRepository).findById(same(fieldId));
    }

    @Test
    void testGetFieldNotFoundException() {
        UUID fieldId = UUID.randomUUID();
        Assertions.assertThrows(NotFoundException.class, () -> fieldService.getField(fieldId));
    }

    @Test
    void testGetFieldsByPageAndSize() {
        fieldService.getFieldsByPageAndSize(0, 10);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(fieldRepository).findAllBy(pageableCaptor.capture());

        assertEquals(0, pageableCaptor.getValue().getPageNumber());
        assertEquals(10, pageableCaptor.getValue().getPageSize());
    }

}