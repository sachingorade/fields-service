package com.test.fields.service;

import com.test.fields.TestUtil;
import com.test.fields.agromonitoring.AmFieldMapper;
import com.test.fields.agromonitoring.PolygonApiClient;
import com.test.fields.agromonitoring.model.AmField;
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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FieldServiceTest {

    @InjectMocks
    private FieldService fieldService;
    @Mock
    private AmFieldMapper amFieldMapper;
    @Mock
    private FieldRepository fieldRepository;
    @Mock
    private PolygonApiClient polygonApiClient;

    @Test
    void testCreateNewField() {
        when(amFieldMapper.toAmField(any(Field.class))).thenCallRealMethod();
        when(polygonApiClient.createPolygon(any(AmField.class))).thenReturn(new AmField("abc"));

        Field field = TestUtil.getResource("sampledata/field.json", Field.class);
        fieldService.createField(field);

        assertNull(field.getId()); // Always create new field

        // Create a polygon
        ArgumentCaptor<AmField> amFieldCaptor = ArgumentCaptor.forClass(AmField.class);
        verify(polygonApiClient).createPolygon(amFieldCaptor.capture());
        assertEquals(field.getBoundaries().getGeoJson(), amFieldCaptor.getValue().getGeoJson());

        verify(fieldRepository).save(same(field));
        // We must store the polygon id as we have to use it later
        assertEquals("abc", field.getPolygonId());
    }

    @Test
    void testDeleteField() {
        Field field = TestUtil.getResource("sampledata/field.json", Field.class);
        Field returnField = TestUtil.getResource("sampledata/field.json", Field.class);
        returnField.setPolygonId("abc");

        when(fieldRepository.findById(eq(field.getId()))).thenReturn(Optional.of(returnField));

        fieldService.deleteField(field);

        verify(polygonApiClient).deletePolygon(eq(returnField.getPolygonId()));
        verify(fieldRepository).delete(same(returnField));
    }

    @Test
    void testUpdateField() {
        when(amFieldMapper.toAmField(any(Field.class))).thenCallRealMethod();

        Field field = TestUtil.getResource("sampledata/field.json", Field.class);
        Field returnField = TestUtil.getResource("sampledata/field.json", Field.class);
        returnField.setPolygonId("abc");
        when(fieldRepository.findById(field.getId())).thenReturn(Optional.of(returnField));

        fieldService.createOrUpdateField(field);

        ArgumentCaptor<AmField> amFieldCaptor = ArgumentCaptor.forClass(AmField.class);
        verify(polygonApiClient).updatePolygon(eq(returnField.getPolygonId()), amFieldCaptor.capture());
        verify(fieldRepository).save(same(returnField));
    }

    @Test
    void testCreateOrUpdateField() {
        when(amFieldMapper.toAmField(any(Field.class))).thenCallRealMethod();
        when(polygonApiClient.createPolygon(any(AmField.class))).thenReturn(new AmField("abc"));

        Field field = TestUtil.getResource("sampledata/field.json", Field.class);
        when(fieldRepository.findById(field.getId())).thenReturn(Optional.empty());

        fieldService.createOrUpdateField(field);

        ArgumentCaptor<AmField> amFieldCaptor = ArgumentCaptor.forClass(AmField.class);
        verify(polygonApiClient).createPolygon(amFieldCaptor.capture());
        assertEquals(field.getBoundaries().getGeoJson(), amFieldCaptor.getValue().getGeoJson());

        verify(fieldRepository).save(same(field));
        assertEquals("abc", field.getPolygonId());
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