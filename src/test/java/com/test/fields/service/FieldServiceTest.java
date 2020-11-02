package com.test.fields.service;

import com.test.fields.TestUtil;
import com.test.fields.agromonitoring.AmModelMapper;
import com.test.fields.agromonitoring.PolygonApiClient;
import com.test.fields.agromonitoring.WeatherApiClient;
import com.test.fields.agromonitoring.model.AmField;
import com.test.fields.agromonitoring.model.AmFieldWeather;
import com.test.fields.agromonitoring.model.AmWeather;
import com.test.fields.api.model.FieldWeather;
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

import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FieldServiceTest {

    @InjectMocks
    private FieldService fieldService;
    @Mock
    private AmModelMapper amModelMapper;
    @Mock
    private FieldRepository fieldRepository;
    @Mock
    private PolygonApiClient polygonApiClient;
    @Mock
    private WeatherApiClient weatherApiClient;

    @Test
    void testCreateNewField() {
        when(amModelMapper.toAmField(any(Field.class))).thenCallRealMethod();
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
        when(amModelMapper.toAmField(any(Field.class))).thenCallRealMethod();

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
        when(amModelMapper.toAmField(any(Field.class))).thenCallRealMethod();
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

    @Test
    void testGetFieldWeather() {
        when(amModelMapper.toFieldWeather(any(AmFieldWeather.class))).thenCallRealMethod();

        UUID fieldId = UUID.fromString("a0f63e74-d7ef-4924-acb3-0e770ae9ec98");
        Field field = TestUtil.getResource("sampledata/field.json", Field.class);
        field.setPolygonId("abc");
        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));

        AmWeather amWeather = TestUtil.getResource("agromonitoring/weather/weather.json", AmWeather.class);
        AmFieldWeather weather = new AmFieldWeather();
        weather.add(amWeather);
        when(weatherApiClient.getPolygonWeather(eq("abc"), anyLong(), anyLong()))
                .thenReturn(weather);

        FieldWeather fieldWeather = fieldService.getFieldWeather(fieldId);

        // TODO we can verify for last 7 days by using an interface to provide time but I am running out of time now!!
        verify(weatherApiClient).getPolygonWeather(eq("abc"), anyLong(), anyLong());
        assertEquals(1485702000, fieldWeather.getWeather().get(0).getTimestamp());
    }

}